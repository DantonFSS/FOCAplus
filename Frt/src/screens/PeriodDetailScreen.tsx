import React, { useState, useEffect } from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity } from 'react-native';
import { useNavigation, useRoute } from '@react-navigation/native';
import { Button } from '../components/Button';
import { theme } from '../theme';

export const PeriodDetailScreen: React.FC = () => {
  const navigation = useNavigation();
  const route = useRoute();
  const periodNumber = (route.params as any)?.periodNumber || 1;
  const createdCourse = (route.params as any)?.createdCourse;
  const [disciplines, setDisciplines] = useState<{ id: number; name: string }[]>([]);

  // Listener para receber disciplinas da tela AddDisciplinesScreen
  useEffect(() => {
    const unsubscribe = navigation.addListener('focus', () => {
      const updatedDisciplines = (route.params as any)?.updatedDisciplines;
      if (updatedDisciplines) {
        setDisciplines(updatedDisciplines);
        // Limpar parâmetros após usar
        (route.params as any).updatedDisciplines = undefined;
      }
    });

    return unsubscribe;
  }, [navigation, route]);

  const handleAddDisciplines = () => {
    (navigation as any).navigate('AddDisciplines', {
      existingDisciplines: disciplines,
      periodNumber,
      createdCourse,
    });
  };

  const handleViewPeriodInfo = () => {
    (navigation as any).navigate('PeriodInfo', {
      periodNumber,
      createdCourse,
      startDate: '',
      endDate: '',
      disciplines,
    });
  };

  return (
    <ScrollView style={styles.container} contentContainerStyle={styles.content}>
      <View style={styles.header}>
        <TouchableOpacity onPress={() => navigation.goBack()}>
          <Text style={styles.backArrow}>←</Text>
        </TouchableOpacity>
        <Text style={styles.headerTitle}>Período {periodNumber}</Text>
      </View>

      {disciplines.length === 0 ? (
        <View style={styles.emptyStateContainer}>
          <Text style={styles.emptyIcon}>📦</Text>
          <Text style={styles.emptyText}>Nenhuma disciplina cadastrada ainda</Text>
          <Button
            title="+ Adicionar disciplinas"
            onPress={handleAddDisciplines}
            variant="primary"
            style={styles.addButton}
          />
        </View>
      ) : (
        <View style={styles.disciplinesContainer}>
          {disciplines.map((discipline) => (
            <TouchableOpacity key={discipline.id} style={styles.disciplineCard}>
              <View style={styles.disciplineNumberCircle}>
                <Text style={styles.disciplineNumber}>{discipline.id}</Text>
              </View>
              <Text style={styles.disciplineName}>{discipline.name}</Text>
            </TouchableOpacity>
          ))}
        </View>
      )}

      <View style={styles.actionsContainer}>
        <Button
          title="Ver informações do período"
          onPress={handleViewPeriodInfo}
          variant="secondary"
          style={styles.infoButton}
        />
        {disciplines.length > 0 && (
          <Button
            title="+ Adicionar disciplinas"
            onPress={handleAddDisciplines}
            variant="primary"
            style={styles.addButtonBottom}
          />
        )}
        <TouchableOpacity
          style={styles.nextButtonContainer}
          onPress={() => {
            (navigation as any).navigate('CourseInfo', { 
              createdCourse,
              periodNumber,
              disciplines,
            });
          }}
          activeOpacity={0.8}
        >
          <Text style={styles.nextButtonText}>Próximo</Text>
          <Text style={styles.nextButtonArrow}>→</Text>
        </TouchableOpacity>
      </View>
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
  emptyStateContainer: {
    alignItems: 'center',
    justifyContent: 'center',
    paddingVertical: theme.spacing.xxl,
  },
  emptyIcon: {
    fontSize: 64,
    marginBottom: theme.spacing.lg,
  },
  emptyText: {
    fontSize: theme.typography.fontSize.md,
    color: theme.colors.gray,
    marginBottom: theme.spacing.xl,
  },
  addButton: {
    marginTop: theme.spacing.md,
  },
  disciplinesContainer: {
    marginBottom: theme.spacing.lg,
  },
  disciplineCard: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: theme.colors.white,
    borderWidth: 1,
    borderColor: theme.colors.grayLight,
    borderRadius: theme.borderRadius.lg,
    padding: theme.spacing.md,
    marginBottom: theme.spacing.md,
  },
  disciplineNumberCircle: {
    width: 40,
    height: 40,
    borderRadius: 20,
    backgroundColor: theme.colors.blueLight,
    justifyContent: 'center',
    alignItems: 'center',
    marginRight: theme.spacing.md,
  },
  disciplineNumber: {
    fontSize: theme.typography.fontSize.lg,
    fontWeight: 'bold',
    color: theme.colors.white,
  },
  disciplineName: {
    fontSize: theme.typography.fontSize.md,
    color: theme.colors.black,
    flex: 1,
  },
  actionsContainer: {
    marginTop: theme.spacing.lg,
  },
  infoButton: {
    marginBottom: theme.spacing.md,
  },
  addButtonBottom: {
    marginBottom: theme.spacing.md,
  },
  nextButtonContainer: {
    backgroundColor: theme.colors.greenGood,
    borderRadius: theme.borderRadius.lg,
    paddingVertical: theme.spacing.lg,
    paddingHorizontal: theme.spacing.xl,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    marginTop: theme.spacing.lg,
    shadowColor: theme.colors.greenGood,
    shadowOffset: {
      width: 0,
      height: 4,
    },
    shadowOpacity: 0.3,
    shadowRadius: 4.65,
    elevation: 8,
    borderWidth: 2,
    borderColor: theme.colors.greenGood,
  },
  nextButtonText: {
    fontSize: theme.typography.fontSize.lg,
    fontWeight: 'bold',
    color: theme.colors.white,
    marginRight: theme.spacing.sm,
  },
  nextButtonArrow: {
    fontSize: theme.typography.fontSize.xl,
    fontWeight: 'bold',
    color: theme.colors.white,
  },
});

