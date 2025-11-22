import React, { useState } from 'react';
import { View, TextInput, Text, StyleSheet, ViewStyle, TextInputProps } from 'react-native';
import { theme } from '../theme';

interface InputTextProps extends TextInputProps {
  label?: string;
  error?: string;
  containerStyle?: ViewStyle;
  variant?: 'dark' | 'light';
}

export const InputText: React.FC<InputTextProps> = ({
  label,
  error,
  containerStyle,
  secureTextEntry,
  variant = 'dark',
  ...textInputProps
}) => {
  const [isFocused, setIsFocused] = useState(false);
  const [showPassword, setShowPassword] = useState(false);

  const isLight = variant === 'light';
  const borderColor = error
    ? theme.colors.redBad
    : isFocused
    ? (isLight ? theme.colors.blueLight : theme.colors.blueLight)
    : isLight
    ? theme.colors.grayLight
    : theme.colors.white;
  
  const textColor = isLight ? theme.colors.black : theme.colors.white;
  const placeholderColor = isLight ? theme.colors.gray : 'rgba(255, 255, 255, 0.8)';
  const eyeIconColor = isLight ? theme.colors.grayDark : theme.colors.white;
  const errorTextColor = isLight ? theme.colors.redBad : theme.colors.white;

  return (
    <View style={[styles.container, containerStyle]}>
      {label && <Text style={styles.label}>{label}</Text>}
      <View style={[styles.inputContainer, { borderBottomColor: borderColor }]}>
        <TextInput
          style={[styles.input, { color: textColor }]}
          placeholderTextColor={placeholderColor}
          secureTextEntry={secureTextEntry && !showPassword}
          onFocus={() => setIsFocused(true)}
          onBlur={() => setIsFocused(false)}
          {...textInputProps}
        />
        {secureTextEntry && (
          <Text
            style={[styles.eyeIcon, { color: eyeIconColor }]}
            onPress={() => setShowPassword(!showPassword)}
          >
            {showPassword ? '👁️' : '👁️‍🗨️'}
          </Text>
        )}
      </View>
      {error && <Text style={[styles.errorText, { color: errorTextColor }]}>{error}</Text>}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    marginBottom: theme.spacing.md,
  },
  label: {
    fontSize: theme.typography.fontSize.sm,
    color: theme.colors.grayDark,
    marginBottom: theme.spacing.xs,
  },
  inputContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    borderBottomWidth: 1,
    paddingBottom: theme.spacing.xs,
    borderBottomColor: theme.colors.grayLight,
  },
  input: {
    flex: 1,
    fontSize: theme.typography.fontSize.md,
    paddingVertical: theme.spacing.sm,
    backgroundColor: 'transparent',
  },
  eyeIcon: {
    fontSize: 20,
    padding: theme.spacing.xs,
  },
  errorText: {
    fontSize: theme.typography.fontSize.xs,
    marginTop: theme.spacing.xs,
    opacity: 0.9,
  },
});

