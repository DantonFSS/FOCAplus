package com.focados.foca.modules.users.domain.services;

import com.focados.foca.modules.users.database.entity.RefreshTokenModel;
import com.focados.foca.modules.users.database.entity.UserModel;
import com.focados.foca.modules.users.database.repository.RefreshTokenRepository;
import com.focados.foca.modules.users.database.repository.UserRepository;
import com.focados.foca.modules.users.domain.dtos.request.CreateUserDto;
import com.focados.foca.modules.users.domain.dtos.request.LoginDto;
import com.focados.foca.modules.users.domain.dtos.request.RefreshTokenDto;
import com.focados.foca.modules.users.domain.dtos.response.AuthResponseDto;
import com.focados.foca.modules.users.domain.dtos.mappers.UserMapper;
import com.focados.foca.shared.common.utils.exceptions.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret:defaultSecretKeyForDevelopmentOnlyChangeInProduction}")
    private String jwtSecret;

    @Value("${jwt.expiration:3600000}")
    private Long jwtExpiration;

    @Value("${jwt.refresh.expiration:86400000}")
    private Long refreshTokenExpiration;

    public AuthResponseDto register(CreateUserDto createUserDto) {
        // Verifica se email já existe
        if (userRepository.findByEmail(createUserDto.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException(createUserDto.getEmail());
        }

        // Verifica se username já existe
        if (userRepository.findByUsername(createUserDto.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException(createUserDto.getUsername());
        }

        // Verifica se CPF já existe
        if (createUserDto.getCpf() != null && !createUserDto.getCpf().isBlank()) {
            if (userRepository.findByCpf(createUserDto.getCpf()).isPresent()) {
                throw new CpfAlreadyExistsException();

            }
        }

        // Mapeia DTO para entidade
        UserModel user = UserMapper.mappingToUserEntity(createUserDto);

        // Criptografa senha com BCrypt
        user.setPasswordHash(passwordEncoder.encode(createUserDto.getPassword()));

        // Salva usuário
        UserModel savedUser = userRepository.save(user);

        // Gera JWT
        String accessToken = generateAccessToken(savedUser);

        // Gera Refresh Token
        String refreshToken = generateRefreshToken(savedUser);

        // Salva Refresh Token no banco
        saveRefreshToken(savedUser, refreshToken);

        return new AuthResponseDto(accessToken, refreshToken);
    }

    public AuthResponseDto login(LoginDto loginDto) {
        // Busca usuário por email
        UserModel user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        // Valida senha com BCrypt
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        // Gera JWT
        String accessToken = generateAccessToken(user);

        // Gera Refresh Token
        String refreshToken = generateRefreshToken(user);

        // Salva Refresh Token no banco
        saveRefreshToken(user, refreshToken);

        return new AuthResponseDto(accessToken, refreshToken);
    }

    private String generateAccessToken(UserModel user) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        Date expiration = new Date(System.currentTimeMillis() + jwtExpiration);

        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .issuedAt(new Date())
                .expiration(expiration)
                .signWith(key)
                .compact();
    }

    private String generateRefreshToken(UserModel user) {
        return UUID.randomUUID().toString();
    }

    @Transactional
    private void saveRefreshToken(UserModel user, String refreshToken) {

        // Remove TODOS os tokens do usuário antes de criar novo
        refreshTokenRepository.deleteByUserId(user.getId());

        // Hash do refresh token antes de salvar
        String tokenHash = passwordEncoder.encode(refreshToken);

        // Revoga tokens antigos do usuário
        refreshTokenRepository.findByUserId(user.getId())
                .ifPresent(oldToken -> {
                    oldToken.setRevoked(true);
                    refreshTokenRepository.save(oldToken);
                });

        // Cria novo refresh token
        RefreshTokenModel refreshTokenModel = new RefreshTokenModel();
        refreshTokenModel.setUser(user);
        refreshTokenModel.setTokenHash(tokenHash);
        refreshTokenModel.setExpiresAt(LocalDateTime.now().plusSeconds(refreshTokenExpiration / 1000));
        refreshTokenModel.setRevoked(false);

        refreshTokenRepository.save(refreshTokenModel);
    }

    public AuthResponseDto refreshToken(RefreshTokenDto refreshTokenDto) {
        // Busca todos os refresh tokens ativos e verifica se algum corresponde
        RefreshTokenModel refreshTokenModel = refreshTokenRepository.findAll()
                .stream()
                .filter(token -> !token.getRevoked() && 
                        token.getExpiresAt().isAfter(LocalDateTime.now()))
                .filter(token -> passwordEncoder.matches(
                        refreshTokenDto.getRefreshToken(), 
                        token.getTokenHash()))
                .findFirst()
                .orElseThrow(InvalidRefreshTokenException::new);

        UserModel user = refreshTokenModel.getUser();

        // Revoga o token antigo
        refreshTokenModel.setRevoked(true);
        refreshTokenRepository.save(refreshTokenModel);

        // Gera novo access token
        String newAccessToken = generateAccessToken(user);

        // Gera novo refresh token
        String newRefreshToken = generateRefreshToken(user);

        // Salva novo refresh token
        saveRefreshToken(user, newRefreshToken);

        return new AuthResponseDto(newAccessToken, newRefreshToken);
    }
}

