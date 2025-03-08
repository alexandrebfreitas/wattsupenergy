import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import AcomphService from './acomph.service';
import { type IAcomph } from '@/shared/model/acomph.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'AcomphDetails',
  setup() {
    const acomphService = inject('acomphService', () => new AcomphService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const acomph: Ref<IAcomph> = ref({});

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

    return {
      alertService,
      acomph,

      previousState,
      t$: useI18n().t,
    };
  },
});
