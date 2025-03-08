import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';
import { useI18n } from 'vue-i18n';

import AcomphService from './acomph.service';
import { type IAcomph } from '@/shared/model/acomph.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Acomph',
  setup() {
    const { t: t$ } = useI18n();
    const acomphService = inject('acomphService', () => new AcomphService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const acomphs: Ref<IAcomph[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveAcomphs = async () => {
      isFetching.value = true;
      try {
        const res = await acomphService().retrieve();
        acomphs.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveAcomphs();
    };

    onMounted(async () => {
      await retrieveAcomphs();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IAcomph) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeAcomph = async () => {
      try {
        await acomphService().delete(removeId.value);
        const message = t$('wattsUpEnergyApp.acomph.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveAcomphs();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      acomphs,
      handleSyncList,
      isFetching,
      retrieveAcomphs,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeAcomph,
      t$,
    };
  },
});
