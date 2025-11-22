import React from 'react';
import { View, Text, StyleSheet, ScrollView } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { useForm, Controller } from 'react-hook-form';
import { InputText } from '../components/InputText';
import { SelectDropdown } from '../components/SelectDropdown';
import { DatePicker } from '../components/DatePicker';
import { Button } from '../components/Button';
import { theme } from '../theme';

interface NewCourseFormData {
  courseName: string;
  level: string;
  divisionType: string;
  divisionQuantity: string;
  institutionName: string;
  startDate: string;
  endDate: string;
}

const LEVEL_OPTIONS = [
  { label: 'Graduação', value: 'graduacao' },
  { label: 'Pós-graduação', value: 'pos-graduacao' },
  { label: 'Mestrado', value: 'mestrado' },
  { label: 'Doutorado', value: 'doutorado' },
  { label: 'Técnico', value: 'tecnico' },
];

const DIVISION_TYPE_OPTIONS = [
  { label: 'Semestres', value: 'semestres' },
  { label: 'Anos', value: 'anos' },
  { label: 'Períodos', value: 'periodos' },
];

const DIVISION_QUANTITY_OPTIONS = Array.from({ length: 15 }, (_, i) => ({
  label: (i + 1).toString(),
  value: (i + 1).toString(),
}));

export const NewCourseScreen: React.FC = () => {
  const navigation = useNavigation();

  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm<NewCourseFormData>({
    defaultValues: {
      courseName: '',
      level: '',
      divisionType: '',
      divisionQuantity: '',
      institutionName: '',
      startDate: '',
      endDate: '',
    },
  });

  const onSubmit = (data: NewCourseFormData) => {
    console.log('Course data:', data);
    // TODO: Navegar para tela de seleção de período
    (navigation as any).navigate('SelectPeriod', { courseData: data });
  };

  return (
    <ScrollView style={styles.container} contentContainerStyle={styles.content}>
      <View style={styles.header}>
        <Text style={styles.headerTitle}>Novo Curso</Text>
      </View>

      <View style={styles.titleContainer}>
        <Text style={styles.titleIcon}>📖</Text>
        <Text style={styles.title}>Novo Curso</Text>
      </View>

      <View style={styles.formContainer}>
        <Controller
          control={control}
          name="courseName"
          rules={{ required: 'Nome do curso é obrigatório' }}
          render={({ field: { onChange, onBlur, value } }) => (
            <InputText
              label="Nome do Curso"
              placeholder="Ex: Engenharia de Software"
              containerStyle={styles.inputContainer}
              value={value}
              onChangeText={onChange}
              onBlur={onBlur}
              error={errors.courseName?.message}
              variant="light"
            />
          )}
        />

        <Controller
          control={control}
          name="level"
          rules={{ required: 'Nível é obrigatório' }}
          render={({ field: { onChange, value } }) => (
            <SelectDropdown
              label="Nível"
              placeholder="Selecione o nível"
              options={LEVEL_OPTIONS}
              value={value}
              onChange={onChange}
              error={errors.level?.message}
            />
          )}
        />

        <Controller
          control={control}
          name="divisionType"
          rules={{ required: 'Tipo de divisão é obrigatório' }}
          render={({ field: { onChange, value } }) => (
            <SelectDropdown
              label="Tipo de Divisão"
              placeholder="Selecione o tipo"
              options={DIVISION_TYPE_OPTIONS}
              value={value}
              onChange={onChange}
              error={errors.divisionType?.message}
            />
          )}
        />

        <Controller
          control={control}
          name="divisionQuantity"
          rules={{ required: 'Quantidade de divisões é obrigatória' }}
          render={({ field: { onChange, value } }) => (
            <SelectDropdown
              label="Quantidade de Divisões"
              placeholder="Selecione a quantidade"
              options={DIVISION_QUANTITY_OPTIONS}
              value={value}
              onChange={onChange}
              error={errors.divisionQuantity?.message}
            />
          )}
        />

        <Controller
          control={control}
          name="institutionName"
          rules={{ required: 'Nome da instituição é obrigatório' }}
          render={({ field: { onChange, onBlur, value } }) => (
            <InputText
              label="Nome da Instituição"
              placeholder="Ex: Universidade XPTO"
              containerStyle={styles.inputContainer}
              value={value}
              onChangeText={onChange}
              onBlur={onBlur}
              error={errors.institutionName?.message}
              variant="light"
            />
          )}
        />

        <Controller
          control={control}
          name="startDate"
          rules={{ required: 'Data de início é obrigatória' }}
          render={({ field: { onChange, value } }) => (
            <DatePicker
              label="Data de Início"
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
          rules={{ required: 'Previsão de fim é obrigatória' }}
          render={({ field: { onChange, value } }) => (
            <DatePicker
              label="Previsão de Fim"
              placeholder="dd/mm/aaaa"
              value={value}
              onChange={onChange}
              error={errors.endDate?.message}
            />
          )}
        />

        <Button
          title="Avançar"
          onPress={handleSubmit(onSubmit)}
          variant="primary"
          style={styles.advanceButton}
        />

        <Button
          title="Cancelar"
          onPress={() => navigation.goBack()}
          variant="secondary"
          style={styles.cancelButton}
        />
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
    paddingBottom: theme.spacing.xl,
  },
  header: {
    backgroundColor: theme.colors.grayDark,
    paddingVertical: theme.spacing.md,
    paddingHorizontal: theme.spacing.lg,
  },
  headerTitle: {
    fontSize: theme.typography.fontSize.lg,
    fontWeight: '600',
    color: theme.colors.white,
  },
  titleContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    padding: theme.spacing.lg,
    paddingBottom: theme.spacing.md,
  },
  titleIcon: {
    fontSize: 24,
    marginRight: theme.spacing.sm,
  },
  title: {
    fontSize: theme.typography.fontSize.xxl,
    fontWeight: 'bold',
    color: theme.colors.black,
  },
  formContainer: {
    paddingHorizontal: theme.spacing.lg,
  },
  inputContainer: {
    marginBottom: theme.spacing.md,
  },
  advanceButton: {
    marginTop: theme.spacing.lg,
    marginBottom: theme.spacing.md,
  },
  cancelButton: {
    marginBottom: theme.spacing.lg,
  },
});

