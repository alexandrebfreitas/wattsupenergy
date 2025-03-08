import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import AcomphDetails from './acomph-details.vue';
import AcomphService from './acomph.service';
import AlertService from '@/shared/alert/alert.service';

type AcomphDetailsComponentType = InstanceType<typeof AcomphDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const acomphSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Acomph Management Detail Component', () => {
    let acomphServiceStub: SinonStubbedInstance<AcomphService>;
    let mountOptions: MountingOptions<AcomphDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      acomphServiceStub = sinon.createStubInstance<AcomphService>(AcomphService);

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
          acomphService: () => acomphServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        acomphServiceStub.find.resolves(acomphSample);
        route = {
          params: {
            acomphId: `${123}`,
          },
        };
        const wrapper = shallowMount(AcomphDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.acomph).toMatchObject(acomphSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        acomphServiceStub.find.resolves(acomphSample);
        const wrapper = shallowMount(AcomphDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
