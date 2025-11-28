import React, { useState } from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity } from 'react-native';
import { useNavigation, useRoute } from '@react-navigation/native';
import { useForm, Controller } from 'react-hook-form';
import { DatePicker } from '../components/DatePicker';
import { Button } from '../components/Button';
import { theme } from '../theme';

interface PeriodInfoFormData {
  startDate: string;
  endDate: string;
}

export const PeriodInfoScreen: React.FC = () => {
  const navigation = useNavigation();
  const route = useRoute();
  const periodNumber = (route.params as any)?.periodNumber || 1;
  const createdCourse = (route.params as any)?.createdCourse;
  const initialStartDate = (route.params as any)?.startDate || '';
  const initialEndDate = (route.params as any)?.endDate || '';
  const [isEditing, setIsEditing] = useState(false);

  const {
    control,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm<PeriodInfoFormData>({
    defaultValues: {
      startDate: initialStartDate,
      endDate: initialEndDate,
    },
  });

  const onSubmit = async (data: PeriodInfoFormData) => {
    try {
      console.log('📝 Salvando informações do período:', data);
      // TODO: Integrar com API para atualizar período
      setIsEditing(false);
      // Mostrar mensagem de sucesso
    } catch (error) {
      console.error('❌ Erro ao salvar:', error);
    }
  };

  const handleCancel = () => {
    reset();
    setIsEditing(false);
  };

  const handleAddDisciplines = () => {
    (navigation as any).navigate('PeriodDetail', {
      periodNumber,
      createdCourse,
      disciplines: disciplines,
    });
  };

  const disciplines = (route.params as any)?.disciplines || [];

  return (
    <ScrollView style={styles.container} contentContainerStyle={styles.content}>
      <View style={styles.header}>
        <TouchableOpacity onPress={() => navigation.goBack()}>
          <Text style={styles.backArrow}>← Voltar</Text>
        </TouchableOpacity>
        <Text style={styles.headerTitle}>
          {isEditing ? 'Período Info Editando' : 'Período Info'}
        </Text>
      </View>

      {/* Period Overview */}
      <View style={styles.periodOverview}>
        <View style={styles.periodNumberCircle}>
          <Text style={styles.periodNumber}>{periodNumber}</Text>
        </View>
        <Text style={styles.periodTitle}>Período {periodNumber}</Text>
      </View>

      {/* Information Card */}
      <View style={styles.infoCard}>
        {isEditing ? (
          <>
            <Text style={styles.editTitle}>Editar informações do período</Text>
            
            <Controller
              control={control}
              name="startDate"
              rules={{ required: 'Data de início é obrigatória' }}
              render={({ field: { onChange, value } }) => (
                <DatePicker
                  label="Previsão de início"
                  placeholder="dd/mm/aaaa"
                  value={value}
                  onChange={onChange}
                  error={errors.startDate?.message}
                />
              )}
            />

            <Controller
              control={control}
              name="endDate"
              rules={{ required: 'Data de término é obrigatória' }}
              render={({ field: { onChange, value } }) => (
                <DatePicker
                  label="Previsão de término"
                  placeholder="dd/mm/aaaa"
                  value={value}
                  onChange={onChange}
                  error={errors.endDate?.message}
                />
              )}
            />

            <View style={styles.editButtons}>
              <Button
                title="Cancelar"
                onPress={handleCancel}
                variant="secondary"
                style={styles.cancelButton}
              />
              <Button
                title="Salvar"
                onPress={handleSubmit(onSubmit)}
                variant="primary"
                style={styles.saveButton}
              />
            </View>
          </>
        ) : (
          <>
            <View style={styles.infoRow}>
              <Text style={styles.infoLabel}>Previsão de início</Text>
              <Text style={styles.infoValue}>
                {initialStartDate || 'dd/mm/aaaa'}
              </Text>
            </View>

            <View style={styles.infoRow}>
              <Text style={styles.infoLabel}>Previsão de término</Text>
              <Text style={styles.infoValue}>
                {initialEndDate || 'dd/mm/aaaa'}
              </Text>
            </View>

            <Button
              title="Editar informações"
              onPress={() => setIsEditing(true)}
              variant="primary"
              style={styles.editButton}
            />
          </>
        )}
      </View>

      {/* Add Disciplines Button */}
      <Button
        title="+ Adicionar disciplinas"
        onPress={handleAddDisciplines}
        variant="primary"
        style={styles.addDisciplinesButton}
      />

      {/* Disciplines List */}
      {disciplines.length > 0 && (
        <View style={styles.disciplinesContainer}>
          {disciplines.map((discipline: { id: number; name: string }) => (
            <TouchableOpacity key={discipline.id} style={styles.disciplineCard}>
              <Text style={styles.disciplineName}>{discipline.name}</Text>
            </TouchableOpacity>
          ))}
        </View>
      )}
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
    fontSize: theme.typography.fontSize.md,
    color: theme.colors.black,
    marginRight: theme.spacing.md,
  },
  headerTitle: {
    fontSize: theme.typography.fontSize.xl,
    fontWeight: '600',
    color: theme.colors.black,
  },
  periodOverview: {
    alignItems: 'center',
    marginBottom: theme.spacing.xl,
  },
  periodNumberCircle: {
    width: 50,
    height: 50,
    borderRadius: 25,
    backgroundColor: theme.colors.blueLight,
    justifyContent: 'center',
    alignItems: 'center',
    marginBottom: theme.spacing.md,
  },
  periodNumber: {
    fontSize: theme.typography.fontSize.xl,
    fontWeight: 'bold',
    color: theme.colors.white,
  },
  periodTitle: {
    fontSize: theme.typography.fontSize.xxl,
    fontWeight: 'bold',
    color: theme.colors.black,
  },
  infoCard: {
    backgroundColor: theme.colors.white,
    borderRadius: theme.borderRadius.lg,
    padding: theme.spacing.lg,
    marginBottom: theme.spacing.xl,
    borderWidth: 1,
    borderColor: theme.colors.grayLight,
  },
  editTitle: {
    fontSize: theme.typography.fontSize.lg,
    fontWeight: '600',
    color: theme.colors.black,
    marginBottom: theme.spacing.lg,
  },
  infoRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: theme.spacing.md,
    paddingBottom: theme.spacing.md,
    borderBottomWidth: 1,
    borderBottomColor: theme.colors.grayLight,
  },
  infoLabel: {
    fontSize: theme.typography.fontSize.md,
    color: theme.colors.grayDark,
    fontWeight: '600',
  },
  infoValue: {
    fontSize: theme.typography.fontSize.md,
    color: theme.colors.gray,
    flex: 1,
    textAlign: 'right',
    marginLeft: theme.spacing.md,
  },
  editButtons: {
    flexDirection: 'row',
    gap: theme.spacing.md,
    marginTop: theme.spacing.md,
  },
  cancelButton: {
    flex: 1,
  },
  saveButton: {
    flex: 1,
  },
  editButton: {
    marginTop: theme.spacing.md,
  },
  addDisciplinesButton: {
    marginBottom: theme.spacing.xl,
  },
  disciplinesContainer: {
    marginTop: theme.spacing.md,
  },
  disciplineCard: {
    backgroundColor: theme.colors.white,
    borderWidth: 1,
    borderColor: theme.colors.grayLight,
    borderRadius: theme.borderRadius.lg,
    padding: theme.spacing.md,
    marginBottom: theme.spacing.md,
  },
  disciplineName: {
    fontSize: theme.typography.fontSize.md,
    color: theme.colors.black,
  },
});

