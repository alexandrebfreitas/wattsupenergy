import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import PostoMedicaoService from './posto-medicao.service';
import { type IPostoMedicao } from '@/shared/model/posto-medicao.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PostoMedicaoDetails',
  setup() {
    const postoMedicaoService = inject('postoMedicaoService', () => new PostoMedicaoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const postoMedicao: Ref<IPostoMedicao> = ref({});

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

    return {
      alertService,
      postoMedicao,

      previousState,
      t$: useI18n().t,
    };
  },
});
