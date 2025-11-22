import { apiClient } from './index';

export interface UserResponse {
  id: string;
  name: string;
  username: string;
  email: string;
  cpf?: string;
  phone?: string;
  userRegisterDate?: string;
}

// Função para decodificar JWT e pegar userId
const getUserIdFromToken = (token: string): string | null => {
  try {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(
      atob(base64)
        .split('')
        .map((c) => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
        .join('')
    );
    const decoded = JSON.parse(jsonPayload);
    return decoded.sub || decoded.userId || null;
  } catch (error) {
    console.error('Error decoding token:', error);
    return null;
  }
};

export const usersApi = {
  getCurrentUser: async (accessToken?: string): Promise<UserResponse> => {
    // Se tiver token, tenta pegar userId do token
    if (accessToken) {
      const userId = getUserIdFromToken(accessToken);
      if (userId) {
        // Por enquanto, busca todos e filtra por ID
        // TODO: Criar endpoint /api/v1/users/{id} ou /api/v1/users/me
        const response = await apiClient.get<UserResponse[]>('/users');
        const user = response.data.find((u) => u.id === userId);
        if (user) {
          return user;
        }
      }
    }
    
    // Fallback: retorna o primeiro usuário (temporário)
    const response = await apiClient.get<UserResponse[]>('/users');
    if (response.data && response.data.length > 0) {
      return response.data[0];
    }
    throw new Error('Usuário não encontrado');
  },
};

