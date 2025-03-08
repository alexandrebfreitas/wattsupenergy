import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';
import { useI18n } from 'vue-i18n';

import PostoMedicaoService from './posto-medicao.service';
import { type IPostoMedicao } from '@/shared/model/posto-medicao.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PostoMedicao',
  setup() {
    const { t: t$ } = useI18n();
    const postoMedicaoService = inject('postoMedicaoService', () => new PostoMedicaoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const postoMedicaos: Ref<IPostoMedicao[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrievePostoMedicaos = async () => {
      isFetching.value = true;
      try {
        const res = await postoMedicaoService().retrieve();
        postoMedicaos.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrievePostoMedicaos();
    };

    onMounted(async () => {
      await retrievePostoMedicaos();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IPostoMedicao) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removePostoMedicao = async () => {
      try {
        await postoMedicaoService().delete(removeId.value);
        const message = t$('wattsUpEnergyApp.postoMedicao.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrievePostoMedicaos();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      postoMedicaos,
      handleSyncList,
      isFetching,
      retrievePostoMedicaos,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removePostoMedicao,
      t$,
    };
  },
});
