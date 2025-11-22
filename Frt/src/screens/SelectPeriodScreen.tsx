import React from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity } from 'react-native';
import { useNavigation, useRoute } from '@react-navigation/native';
import { Button } from '../components/Button';
import { theme } from '../theme';

export const SelectPeriodScreen: React.FC = () => {
  const navigation = useNavigation();
  const route = useRoute();
  const courseData = (route.params as any)?.courseData;

  const periods = [1, 2, 3, 4, 5, 6];
  const [selectedPeriod, setSelectedPeriod] = React.useState<number | null>(null);

  const handleFinalize = () => {
    if (selectedPeriod) {
      console.log('Selected period:', selectedPeriod);
      // TODO: Criar curso e período no backend
      // Navegar para detalhe do período
      (navigation as any).navigate('PeriodDetail', { 
        periodNumber: selectedPeriod,
        courseData 
      });
    }
  };

  return (
    <ScrollView style={styles.container} contentContainerStyle={styles.content}>
      <View style={styles.header}>
        <TouchableOpacity onPress={() => navigation.goBack()}>
          <Text style={styles.backArrow}>←</Text>
        </TouchableOpacity>
        <Text style={styles.headerTitle}>Selecione o Período</Text>
      </View>

      <View style={styles.periodsContainer}>
        {periods.map((period) => (
          <TouchableOpacity
            key={period}
            style={[
              styles.periodButton,
              selectedPeriod === period && styles.periodButtonSelected,
            ]}
            onPress={() => setSelectedPeriod(period)}
          >
            <View
              style={[
                styles.periodNumberCircle,
                selectedPeriod === period && styles.periodNumberCircleSelected,
              ]}
            >
              <Text
                style={[
                  styles.periodNumber,
                  selectedPeriod === period && styles.periodNumberSelected,
                ]}
              >
                {period}
              </Text>
            </View>
            <Text
              style={[
                styles.periodLabel,
                selectedPeriod === period && styles.periodLabelSelected,
              ]}
            >
              Período {period}
            </Text>
          </TouchableOpacity>
        ))}
      </View>

      <Button
        title="Finalizar"
        onPress={handleFinalize}
        variant="primary"
        style={styles.finalizeButton}
        disabled={!selectedPeriod}
      />
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: theme.colors.white,
  },
  content: {
    padding: theme.spacing.lg,
    paddingBottom: theme.spacing.xl,
  },
  header: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: theme.spacing.xl,
  },
  backArrow: {
    fontSize: 24,
    color: theme.colors.black,
    marginRight: theme.spacing.md,
  },
  headerTitle: {
    fontSize: theme.typography.fontSize.xl,
    fontWeight: '600',
    color: theme.colors.black,
  },
  periodsContainer: {
    marginBottom: theme.spacing.xl,
  },
  periodButton: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: theme.colors.white,
    borderWidth: 1,
    borderColor: theme.colors.grayLight,
    borderRadius: theme.borderRadius.lg,
    padding: theme.spacing.md,
    marginBottom: theme.spacing.md,
  },
  periodButtonSelected: {
    borderColor: theme.colors.blueLight,
    borderWidth: 2,
  },
  periodNumberCircle: {
    width: 40,
    height: 40,
    borderRadius: 20,
    backgroundColor: theme.colors.backgroundLight,
    justifyContent: 'center',
    alignItems: 'center',
    marginRight: theme.spacing.md,
  },
  periodNumberCircleSelected: {
    backgroundColor: theme.colors.blueLight,
  },
  periodNumber: {
    fontSize: theme.typography.fontSize.lg,
    fontWeight: 'bold',
    color: theme.colors.grayDark,
  },
  periodNumberSelected: {
    color: theme.colors.white,
  },
  periodLabel: {
    fontSize: theme.typography.fontSize.md,
    color: theme.colors.black,
  },
  periodLabelSelected: {
    fontWeight: '600',
    color: theme.colors.blueLight,
  },
  finalizeButton: {
    marginTop: theme.spacing.lg,
  },
});

