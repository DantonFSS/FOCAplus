import React, { useState } from 'react';
import { StatusBar } from 'expo-status-bar';
import { View, Text, TouchableOpacity } from 'react-native';
import { NavigationContainer, useNavigation } from '@react-navigation/native';
import { createDrawerNavigator } from '@react-navigation/drawer';
import { LoginScreen } from './src/screens/LoginScreen';
import { RegisterScreen } from './src/screens/RegisterScreen';
import { HomeScreen } from './src/screens/HomeScreen';
import { ProScreen } from './src/screens/ProScreen';
import { CreateGroupScreen } from './src/screens/CreateGroupScreen';
import { NewCourseScreen } from './src/screens/NewCourseScreen';
import { SelectPeriodScreen } from './src/screens/SelectPeriodScreen';
import { PeriodDetailScreen } from './src/screens/PeriodDetailScreen';
import { DrawerContent } from './src/components/DrawerContent';
import { AuthProvider, useAuth } from './src/contexts/AuthContext';
import { theme } from './src/theme';

const Drawer = createDrawerNavigator();

function AuthNavigator() {
  const [currentScreen, setCurrentScreen] = useState<'login' | 'register'>('login');

  return (
    <>
      {currentScreen === 'login' ? (
        <LoginScreen 
          onNavigateToRegister={() => setCurrentScreen('register')}
          onLoginSuccess={() => {
            // O redirecionamento será feito automaticamente pelo AppNavigator
            // quando isAuthenticated mudar
          }}
        />
      ) : (
        <RegisterScreen onNavigateToLogin={() => setCurrentScreen('login')} />
      )}
      <StatusBar style={currentScreen === 'login' ? 'light' : 'dark'} />
    </>
  );
}

function AppNavigator() {
  const { isAuthenticated, isLoading } = useAuth();

  if (isLoading) {
    return null; // Ou um componente de loading
  }

  if (!isAuthenticated) {
    return <AuthNavigator />;
  }

  return (
    <NavigationContainer>
      <Drawer.Navigator
        drawerContent={(props) => {
          const state = props.state;
          const currentRoute = state.routes[state.index];
          return <DrawerContent {...props} currentScreen={currentRoute.name} />;
        }}
        screenOptions={({ navigation }) => ({
          drawerStyle: {
            backgroundColor: theme.colors.white,
            width: 280,
          },
          headerShown: true,
          headerStyle: {
            backgroundColor: theme.colors.white,
          },
          headerTintColor: theme.colors.black,
          headerTitle: '',
          headerLeft: () => (
            <TouchableOpacity
              onPress={() => navigation.openDrawer()}
              style={{ marginLeft: 16, padding: 8 }}
            >
              <Text style={{ fontSize: 24, color: theme.colors.black }}>☰</Text>
            </TouchableOpacity>
          ),
        })}
      >
        <Drawer.Screen name="Home" component={HomeScreen} />
        <Drawer.Screen name="Pro" component={ProScreen} />
        <Drawer.Screen name="CreateGroup" component={CreateGroupScreen} />
        <Drawer.Screen 
          name="NewCourse" 
          component={NewCourseScreen}
          options={{ headerShown: false }}
        />
        <Drawer.Screen 
          name="SelectPeriod" 
          component={SelectPeriodScreen}
          options={{ headerShown: false }}
        />
        <Drawer.Screen 
          name="PeriodDetail" 
          component={PeriodDetailScreen}
          options={{ headerShown: false }}
        />
      </Drawer.Navigator>
      <StatusBar style="dark" />
    </NavigationContainer>
  );
}

export default function App() {
  return (
    <AuthProvider>
      <AppNavigator />
    </AuthProvider>
  );
}
