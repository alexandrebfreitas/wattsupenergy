import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import PostoMedicaoService from './posto-medicao.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IPostoMedicao, PostoMedicao } from '@/shared/model/posto-medicao.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PostoMedicaoUpdate',
  setup() {
    const postoMedicaoService = inject('postoMedicaoService', () => new PostoMedicaoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const postoMedicao: Ref<IPostoMedicao> = ref(new PostoMedicao());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'pt-br'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrievePostoMedicao = async postoMedicaoId => {
      try {
        const res = await postoMedicaoService().find(postoMedicaoId);
        postoMedicao.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.postoMedicaoId) {
      retrievePostoMedicao(route.params.postoMedicaoId);
    }

    const initRelationships = () => {};

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      nome: {},
      numUsinaHidreletrica: {},
      bacia: {},
      subbacia: {},
      submercado: {},
      numPostoMedicaos: {},
    };
    const v$ = useVuelidate(validationRules, postoMedicao as any);
    v$.value.$validate();

    return {
      postoMedicaoService,
      alertService,
      postoMedicao,
      previousState,
      isSaving,
      currentLanguage,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.postoMedicao.id) {
        this.postoMedicaoService()
          .update(this.postoMedicao)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('wattsUpEnergyApp.postoMedicao.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.postoMedicaoService()
          .create(this.postoMedicao)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('wattsUpEnergyApp.postoMedicao.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
