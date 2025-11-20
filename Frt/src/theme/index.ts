export const theme = {
  colors: {
    // Primárias
    backgroundLight: '#E5E5E5',
    blueLight: '#008BF2',
    
    // Secundárias
    greenGood: '#58CC02',
    redBad: '#EE5555',
    
    // Cores auxiliares
    white: '#FFFFFF',
    black: '#000000',
    gray: '#808080',
    grayLight: '#CCCCCC',
    grayDark: '#333333',
  },
  spacing: {
    xs: 4,
    sm: 8,
    md: 16,
    lg: 24,
    xl: 32,
    xxl: 48,
  },
  typography: {
    fontFamily: {
      regular: 'System',
      bold: 'System',
    },
    fontSize: {
      xs: 12,
      sm: 14,
      md: 16,
      lg: 18,
      xl: 20,
      xxl: 24,
      xxxl: 32,
    },
  },
  borderRadius: {
    sm: 4,
    md: 8,
    lg: 12,
    xl: 16,
    round: 9999,
  },
};

export type Theme = typeof theme;

