import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import PostoMedicaoDetails from './posto-medicao-details.vue';
import PostoMedicaoService from './posto-medicao.service';
import AlertService from '@/shared/alert/alert.service';

type PostoMedicaoDetailsComponentType = InstanceType<typeof PostoMedicaoDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const postoMedicaoSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('PostoMedicao Management Detail Component', () => {
    let postoMedicaoServiceStub: SinonStubbedInstance<PostoMedicaoService>;
    let mountOptions: MountingOptions<PostoMedicaoDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      postoMedicaoServiceStub = sinon.createStubInstance<PostoMedicaoService>(PostoMedicaoService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          postoMedicaoService: () => postoMedicaoServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        postoMedicaoServiceStub.find.resolves(postoMedicaoSample);
        route = {
          params: {
            postoMedicaoId: `${123}`,
          },
        };
        const wrapper = shallowMount(PostoMedicaoDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.postoMedicao).toMatchObject(postoMedicaoSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        postoMedicaoServiceStub.find.resolves(postoMedicaoSample);
        const wrapper = shallowMount(PostoMedicaoDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
