import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import AcomphService from './acomph.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import PostoMedicaoService from '@/entities/posto-medicao/posto-medicao.service';
import { type IPostoMedicao } from '@/shared/model/posto-medicao.model';
import { Acomph, type IAcomph } from '@/shared/model/acomph.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'AcomphUpdate',
  setup() {
    const acomphService = inject('acomphService', () => new AcomphService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const acomph: Ref<IAcomph> = ref(new Acomph());

    const postoMedicaoService = inject('postoMedicaoService', () => new PostoMedicaoService());

    const postoMedicaos: Ref<IPostoMedicao[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'pt-br'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveAcomph = async acomphId => {
      try {
        const res = await acomphService().find(acomphId);
        acomph.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.acomphId) {
      retrieveAcomph(route.params.acomphId);
    }

    const initRelationships = () => {
      postoMedicaoService()
        .retrieve()
        .then(res => {
          postoMedicaos.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      date: {},
      vazaoDefluenteLido: {},
      vazaoDefluenteConsolidado: {},
      vazaoAfluenteLido: {},
      vazaoAfluenteConsolidado: {},
      vazaoIncrementalConsolidado: {},
      vazaoNaturalConsolidado: {},
      nivelReservatorioLido: {},
      nivelReservatorioConsolidado: {},
      dataPublicacao: {},
      numPostoMedicao: {},
    };
    const v$ = useVuelidate(validationRules, acomph as any);
    v$.value.$validate();

    return {
      acomphService,
      alertService,
      acomph,
      previousState,
      isSaving,
      currentLanguage,
      postoMedicaos,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.acomph.id) {
        this.acomphService()
          .update(this.acomph)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('wattsUpEnergyApp.acomph.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.acomphService()
          .create(this.acomph)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('wattsUpEnergyApp.acomph.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
