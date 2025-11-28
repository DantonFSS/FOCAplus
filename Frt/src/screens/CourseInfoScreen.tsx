import React, { useState } from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity, Switch } from 'react-native';
import { useNavigation, useRoute } from '@react-navigation/native';
import { useForm, Controller } from 'react-hook-form';
import { InputText } from '../components/InputText';
import { DatePicker } from '../components/DatePicker';
import { Button } from '../components/Button';
import { theme } from '../theme';
import { CourseResponse } from '../api/courses';

interface CourseInfoFormData {
  address: string;
  contact: string;
  online: boolean;
  startDate: string;
  endDate: string;
}

// Converter data ISO para formato brasileiro
const convertISOToDate = (isoDate: string): string => {
  if (!isoDate) return '';
  const date = new Date(isoDate);
  const day = date.getDate().toString().padStart(2, '0');
  const month = (date.getMonth() + 1).toString().padStart(2, '0');
  const year = date.getFullYear();
  return `${day}/${month}/${year}`;
};

export const CourseInfoScreen: React.FC = () => {
  const navigation = useNavigation();
  const route = useRoute();
  const createdCourse = (route.params as any)?.createdCourse as CourseResponse | undefined;
  const [isEditing, setIsEditing] = useState(false);

  const {
    control,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm<CourseInfoFormData>({
    defaultValues: {
      address: createdCourse?.address || '',
      contact: '',
      online: createdCourse?.online || false,
      startDate: createdCourse?.startDate ? convertISOToDate(createdCourse.startDate) : '',
      endDate: createdCourse?.endDate ? convertISOToDate(createdCourse.endDate) : '',
    },
  });

  const getLevelLabel = (level?: string): string => {
    const mapping: { [key: string]: string } = {
      'UNDERGRADUATE': 'Superior',
      'HIGH_SCHOOL': 'Ensino Médio',
      'MASTER': 'Mestrado',
      'DOCTORATE': 'Doutorado',
      'FREE_COURSE': 'Curso Livre',
      'PROFESSIONAL': 'Profissional',
      'TECHNICAL': 'Técnico',
    };
    return mapping[level || ''] || 'Superior';
  };

  const onSubmit = async (data: CourseInfoFormData) => {
    try {
      console.log('📝 Salvando informações do curso:', data);
      // TODO: Integrar com API para atualizar curso
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

  const periodsCount = createdCourse?.divisionsCount || 0;
  const periods = Array.from({ length: periodsCount }, (_, i) => i + 1);

  return (
    <ScrollView style={styles.container} contentContainerStyle={styles.content}>
      <View style={styles.header}>
        <TouchableOpacity onPress={() => navigation.goBack()}>
          <Text style={styles.backArrow}>←</Text>
        </TouchableOpacity>
        <Text style={styles.headerTitle}>
          {isEditing ? 'Curso Info Editando' : 'Curso Info'}
        </Text>
      </View>

      {/* Course Overview */}
      <View style={styles.courseOverview}>
        <View style={styles.courseIcon}>
          <Text style={styles.courseIconText}>{'</>'}</Text>
        </View>
        <Text style={styles.courseName}>
          {createdCourse?.name || 'Nome do Curso'}
        </Text>
        <Text style={styles.institutionName}>
          {createdCourse?.institutionName || 'Instituição'}
        </Text>
        <Text style={styles.courseLevel}>
          {getLevelLabel(createdCourse?.level)}
        </Text>
      </View>

      {/* Information Card */}
      <View style={styles.infoCard}>
        {isEditing ? (
          <>
            <Text style={styles.editTitle}>Editar informações do curso</Text>
            
            <Controller
              control={control}
              name="address"
              render={({ field: { onChange, onBlur, value } }) => (
                <InputText
                  label="Endereço"
                  placeholder="Digite o endereço..."
                  containerStyle={styles.inputContainer}
                  value={value}
                  onChangeText={onChange}
                  onBlur={onBlur}
                  variant="light"
                />
              )}
            />

            <Controller
              control={control}
              name="contact"
              render={({ field: { onChange, onBlur, value } }) => (
                <InputText
                  label="Contato"
                  placeholder="Digite telefone ou e-mail..."
                  containerStyle={styles.inputContainer}
                  value={value}
                  onChangeText={onChange}
                  onBlur={onBlur}
                  variant="light"
                />
              )}
            />

            <View style={styles.switchContainer}>
              <Text style={styles.switchLabel}>EAD/Online</Text>
              <Controller
                control={control}
                name="online"
                render={({ field: { onChange, value } }) => (
                  <Switch
                    value={value}
                    onValueChange={onChange}
                    trackColor={{ false: theme.colors.grayLight, true: theme.colors.blueLight }}
                    thumbColor={theme.colors.white}
                  />
                )}
              />
            </View>

            <Controller
              control={control}
              name="startDate"
              render={({ field: { onChange, value } }) => (
                <DatePicker
                  label="Previsão de início"
                  placeholder="dd/mm/aaaa"
                  value={value}
                  onChange={onChange}
                />
              )}
            />

            <Controller
              control={control}
              name="endDate"
              render={({ field: { onChange, value } }) => (
                <DatePicker
                  label="Previsão de término"
                  placeholder="dd/mm/aaaa"
                  value={value}
                  onChange={onChange}
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
              <Text style={styles.infoLabel}>Endereço</Text>
              <Text style={styles.infoValue}>
                {createdCourse?.address || 'Adicione um endereço...'}
              </Text>
            </View>

            <View style={styles.infoRow}>
              <Text style={styles.infoLabel}>Contato</Text>
              <Text style={styles.infoValue}>
                {'Adicione um contato...'}
              </Text>
            </View>

            <View style={styles.infoRow}>
              <Text style={styles.infoLabel}>EAD/Online</Text>
              <Switch
                value={createdCourse?.online || false}
                disabled
                trackColor={{ false: theme.colors.grayLight, true: theme.colors.blueLight }}
                thumbColor={theme.colors.white}
              />
            </View>

            <View style={styles.infoRow}>
              <Text style={styles.infoLabel}>Previsão de início</Text>
              <Text style={styles.infoValue}>
                {createdCourse?.startDate ? convertISOToDate(createdCourse.startDate) : 'dd/mm/aaaa'}
              </Text>
            </View>

            <View style={styles.infoRow}>
              <Text style={styles.infoLabel}>Previsão de término</Text>
              <Text style={styles.infoValue}>
                {createdCourse?.endDate ? convertISOToDate(createdCourse.endDate) : 'dd/mm/aaaa'}
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

      {/* Periods Section */}
      {periodsCount > 0 && (
        <View style={styles.periodsSection}>
          <Text style={styles.periodsTitle}>Períodos</Text>
          <View style={styles.periodsGrid}>
            {periods.map((period) => (
              <TouchableOpacity
                key={period}
                style={styles.periodButton}
                onPress={() => {
                  // Navegar para informações do período
                  (navigation as any).navigate('PeriodInfo', {
                    periodNumber: period,
                    createdCourse,
                    startDate: '',
                    endDate: '',
                    disciplines: [],
                  });
                }}
              >
                <Text style={styles.periodButtonText}>Período {period}</Text>
              </TouchableOpacity>
            ))}
          </View>
        </View>
      )}

      {/* Finalize Button */}
      <Button
        title="Finalizar"
        onPress={() => {
          // Navegar para Home após finalizar
          (navigation as any).navigate('Home');
        }}
        variant="primary"
        style={styles.finalizeButton}
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
  courseOverview: {
    alignItems: 'center',
    marginBottom: theme.spacing.xl,
  },
  courseIcon: {
    width: 60,
    height: 60,
    borderRadius: 30,
    backgroundColor: theme.colors.blueLight,
    justifyContent: 'center',
    alignItems: 'center',
    marginBottom: theme.spacing.md,
  },
  courseIconText: {
    fontSize: theme.typography.fontSize.xxl,
    color: theme.colors.white,
    fontWeight: 'bold',
  },
  courseName: {
    fontSize: theme.typography.fontSize.xxl,
    fontWeight: 'bold',
    color: theme.colors.black,
    textAlign: 'center',
    marginBottom: theme.spacing.xs,
  },
  institutionName: {
    fontSize: theme.typography.fontSize.md,
    color: theme.colors.gray,
    marginBottom: theme.spacing.xs,
  },
  courseLevel: {
    fontSize: theme.typography.fontSize.md,
    color: theme.colors.gray,
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
  switchContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: theme.spacing.md,
    paddingBottom: theme.spacing.md,
    borderBottomWidth: 1,
    borderBottomColor: theme.colors.grayLight,
  },
  switchLabel: {
    fontSize: theme.typography.fontSize.md,
    color: theme.colors.grayDark,
    fontWeight: '600',
  },
  inputContainer: {
    marginBottom: theme.spacing.md,
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
  periodsSection: {
    marginBottom: theme.spacing.xl,
  },
  periodsTitle: {
    fontSize: theme.typography.fontSize.lg,
    fontWeight: '600',
    color: theme.colors.black,
    marginBottom: theme.spacing.md,
  },
  periodsGrid: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    gap: theme.spacing.md,
  },
  periodButton: {
    backgroundColor: theme.colors.blueLight,
    borderRadius: theme.borderRadius.md,
    paddingVertical: theme.spacing.md,
    paddingHorizontal: theme.spacing.lg,
    minWidth: '30%',
    alignItems: 'center',
  },
  periodButtonText: {
    fontSize: theme.typography.fontSize.md,
    color: theme.colors.white,
    fontWeight: '600',
  },
  finalizeButton: {
    marginTop: theme.spacing.lg,
  },
});

