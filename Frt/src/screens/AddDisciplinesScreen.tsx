import React from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity } from 'react-native';
import { useNavigation, useRoute } from '@react-navigation/native';
import { useForm, Controller } from 'react-hook-form';
import { InputText } from '../components/InputText';
import { Button } from '../components/Button';
import { theme } from '../theme';

interface DisciplineFormData {
  disciplineCount: string;
  disciplines: { name: string }[];
}

export const AddDisciplinesScreen: React.FC = () => {
  const navigation = useNavigation();
  const route = useRoute();
  const existingDisciplines = (route.params as any)?.existingDisciplines || [];
  const periodNumber = (route.params as any)?.periodNumber;
  const createdCourse = (route.params as any)?.createdCourse;

  const {
    control,
    handleSubmit,
    watch,
    setValue,
    formState: { errors },
  } = useForm<DisciplineFormData>({
    defaultValues: {
      disciplineCount: '2',
      disciplines: [{ name: '' }, { name: '' }],
    },
  });

  const disciplineCount = watch('disciplineCount');
  const disciplineFields = watch('disciplines');

  const updateDisciplineCount = (count: string) => {
    const num = parseInt(count) || 0;
    const newDisciplines = Array.from({ length: num }, (_, i) => ({
      name: disciplineFields[i]?.name || '',
    }));
    setValue('disciplines', newDisciplines);
  };

  const onSubmit = (data: DisciplineFormData) => {
    const newDisciplines = data.disciplines
      .filter((d) => d.name.trim())
      .map((d, index) => ({
        id: existingDisciplines.length + index + 1,
        name: d.name.trim(),
      }));
    
    const updatedDisciplines = [...existingDisciplines, ...newDisciplines];
    
    // Voltar para a tela anterior passando as disciplinas atualizadas
    (navigation as any).navigate('PeriodDetail', {
      periodNumber,
      createdCourse,
      updatedDisciplines,
    });
  };

  const handleCancel = () => {
    setValue('disciplineCount', '2');
    setValue('disciplines', [{ name: '' }, { name: '' }]);
    navigation.goBack();
  };

  return (
    <ScrollView style={styles.container} contentContainerStyle={styles.content}>
      <View style={styles.header}>
        <TouchableOpacity onPress={handleCancel}>
          <Text style={styles.backArrow}>←</Text>
        </TouchableOpacity>
        <Text style={styles.headerTitle}>Adicionar Disciplinas</Text>
      </View>

      <View style={styles.formContainer}>
        <Text style={styles.formTitle}>Quantas disciplinas deseja criar?</Text>
        
        <Controller
          control={control}
          name="disciplineCount"
          rules={{ required: 'Quantidade é obrigatória' }}
          render={({ field: { onChange, value } }) => (
            <View style={styles.countContainer}>
              <TouchableOpacity
                style={styles.countButton}
                onPress={() => {
                  const newVal = Math.max(1, parseInt(value || '1') - 1).toString();
                  onChange(newVal);
                  updateDisciplineCount(newVal);
                }}
              >
                <Text style={styles.countButtonText}>−</Text>
              </TouchableOpacity>
              <View style={styles.countDisplay}>
                <Text style={styles.countText}>{value || '2'}</Text>
              </View>
              <TouchableOpacity
                style={styles.countButton}
                onPress={() => {
                  const newVal = (parseInt(value || '1') + 1).toString();
                  onChange(newVal);
                  updateDisciplineCount(newVal);
                }}
              >
                <Text style={styles.countButtonText}>+</Text>
              </TouchableOpacity>
            </View>
          )}
        />

        {disciplineFields.map((_, index) => (
          <Controller
            key={index}
            control={control}
            name={`disciplines.${index}.name`}
            rules={{ required: `Nome da disciplina ${index + 1} é obrigatório` }}
            render={({ field: { onChange, onBlur, value } }) => (
              <InputText
                label={index === 0 ? 'Nome da disciplina' : `Nome da disciplina ${index + 1}`}
                placeholder={`Nome da disciplina ${index + 1}`}
                containerStyle={styles.inputContainer}
                value={value}
                onChangeText={onChange}
                onBlur={onBlur}
                error={errors.disciplines?.[index]?.name?.message}
                variant="light"
              />
            )}
          />
        ))}

        <View style={styles.formButtons}>
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
  formContainer: {
    backgroundColor: theme.colors.backgroundLight,
    borderRadius: theme.borderRadius.lg,
    padding: theme.spacing.lg,
    marginTop: theme.spacing.lg,
  },
  formTitle: {
    fontSize: theme.typography.fontSize.md,
    fontWeight: '600',
    color: theme.colors.black,
    marginBottom: theme.spacing.md,
  },
  countContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    marginBottom: theme.spacing.lg,
  },
  countButton: {
    width: 40,
    height: 40,
    borderRadius: theme.borderRadius.md,
    backgroundColor: theme.colors.white,
    borderWidth: 1,
    borderColor: theme.colors.blueLight,
    justifyContent: 'center',
    alignItems: 'center',
  },
  countButtonText: {
    fontSize: theme.typography.fontSize.xl,
    color: theme.colors.blueLight,
    fontWeight: 'bold',
  },
  countDisplay: {
    width: 60,
    height: 40,
    backgroundColor: theme.colors.white,
    borderWidth: 1,
    borderColor: theme.colors.blueLight,
    borderRadius: theme.borderRadius.md,
    justifyContent: 'center',
    alignItems: 'center',
    marginHorizontal: theme.spacing.sm,
  },
  countText: {
    fontSize: theme.typography.fontSize.lg,
    fontWeight: '600',
    color: theme.colors.black,
  },
  inputContainer: {
    marginBottom: theme.spacing.md,
  },
  formButtons: {
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
});

