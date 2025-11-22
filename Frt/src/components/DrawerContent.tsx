import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity, Image } from 'react-native';
import { theme } from '../theme';
import { useAuth } from '../contexts/AuthContext';

interface DrawerContentProps {
  navigation?: any;
  currentScreen?: string;
}

export const DrawerContent: React.FC<DrawerContentProps> = ({ navigation, currentScreen = 'Home' }) => {
  const { user, logout } = useAuth();

  const menuItems = [
    { id: 'home', label: 'Home', icon: '🏠', iconType: 'emoji' },
    { id: 'pro', label: 'Obter Pro', icon: '⭐', iconType: 'emoji', screen: 'Pro' },
    { id: 'ratpack', label: 'Rat Pack', icon: '⚙️', iconType: 'emoji' },
    { id: 'create-group', label: 'Criar grupo', icon: 'plus_ison', iconType: 'asset', screen: 'CreateGroup' },
    { id: 'join-group', label: 'Juntar-se ao grupo', icon: 'users_icon', iconType: 'asset' },
    { id: 'challenges', label: 'Desafios concluídos', icon: '🚩', iconType: 'emoji' },
    { id: 'devices', label: 'Meus dispositivos', icon: '⌚', iconType: 'emoji' },
    { id: 'settings', label: 'Configurações', icon: 'config_icon', iconType: 'asset' },
    { id: 'help', label: 'Ajuda & feedback', icon: '?', iconType: 'emoji' },
    { id: 'about', label: 'Sobre', icon: 'ℹ️', iconType: 'emoji' },
  ];

  const getIconSource = (iconName: string) => {
    const iconMap: { [key: string]: any } = {
      'plus_ison': require('../../assets/plus_ison.png'),
      'users_icon': require('../../assets/users_icon.png'),
      'config_icon': require('../../assets/config_icon.png'),
    };
    return iconMap[iconName];
  };

  return (
    <View style={styles.container}>
      <View style={styles.profileSection}>
        <View style={styles.avatar}>
          <Text style={styles.avatarText}>
            {user?.name?.charAt(0).toUpperCase() || 'U'}
          </Text>
        </View>
        <Text style={styles.userName}>{user?.name || user?.username || 'Usuário'}</Text>
      </View>

      <View style={styles.menuSection}>
        {menuItems.map((item) => {
          const isActive = 
            (item.id === 'home' && (currentScreen === 'Home' || currentScreen === 'home')) ||
            (item.screen && currentScreen === item.screen) ||
            (item.id === 'pro' && currentScreen === 'Pro') ||
            (item.id === 'create-group' && currentScreen === 'CreateGroup');
          return (
            <TouchableOpacity
              key={item.id}
              style={[styles.menuItem, isActive && styles.menuItemActive]}
              onPress={() => {
                if (navigation) {
                  if (item.screen) {
                    navigation.navigate(item.screen as never);
                  } else if (item.id === 'home') {
                    navigation.navigate('Home');
                  }
                }
              }}
            >
              {item.iconType === 'asset' ? (
                <Image 
                  source={getIconSource(item.icon)} 
                  style={[styles.menuIconImage, isActive && styles.menuIconImageActive]}
                  resizeMode="contain"
                />
              ) : (
                <Text style={[styles.menuIcon, isActive && styles.menuIconActive]}>
                  {item.icon}
                </Text>
              )}
              <Text style={[styles.menuLabel, isActive && styles.menuLabelActive]}>
                {item.label}
              </Text>
              {!isActive && <Text style={styles.menuArrow}>›</Text>}
            </TouchableOpacity>
          );
        })}
      </View>

      <TouchableOpacity style={styles.logoutButton} onPress={logout}>
        <Text style={styles.logoutText}>Sair</Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: theme.colors.white,
    paddingTop: 60,
  },
  profileSection: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: theme.spacing.lg,
    paddingBottom: theme.spacing.lg,
  },
  avatar: {
    width: 50,
    height: 50,
    borderRadius: 25,
    backgroundColor: theme.colors.blueLight,
    justifyContent: 'center',
    alignItems: 'center',
    marginRight: theme.spacing.md,
  },
  avatarText: {
    fontSize: theme.typography.fontSize.xl,
    fontWeight: 'bold',
    color: theme.colors.white,
  },
  userName: {
    fontSize: theme.typography.fontSize.lg,
    fontWeight: '600',
    color: theme.colors.black,
  },
  menuSection: {
    flex: 1,
    paddingTop: theme.spacing.md,
  },
  menuItem: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingVertical: theme.spacing.md,
    paddingHorizontal: theme.spacing.lg,
  },
  menuItemActive: {
    backgroundColor: theme.colors.blueLight,
    marginHorizontal: theme.spacing.md,
    borderRadius: theme.borderRadius.md,
  },
  menuIcon: {
    fontSize: 20,
    marginRight: theme.spacing.md,
    width: 24,
  },
  menuIconActive: {
    color: theme.colors.white,
  },
  menuIconImage: {
    width: 20,
    height: 20,
    marginRight: theme.spacing.md,
    tintColor: theme.colors.black,
  },
  menuIconImageActive: {
    tintColor: theme.colors.white,
  },
  menuLabel: {
    flex: 1,
    fontSize: theme.typography.fontSize.md,
    color: theme.colors.black,
  },
  menuLabelActive: {
    color: theme.colors.white,
    fontWeight: '600',
  },
  menuArrow: {
    fontSize: 20,
    color: theme.colors.gray,
  },
  logoutButton: {
    padding: theme.spacing.lg,
    borderTopWidth: 1,
    borderTopColor: theme.colors.grayLight,
  },
  logoutText: {
    fontSize: theme.typography.fontSize.md,
    color: theme.colors.redBad,
    fontWeight: '600',
    textAlign: 'center',
  },
});

