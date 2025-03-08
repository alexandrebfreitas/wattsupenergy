import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import AcomphUpdate from './acomph-update.vue';
import AcomphService from './acomph.service';
import AlertService from '@/shared/alert/alert.service';

import PostoMedicaoService from '@/entities/posto-medicao/posto-medicao.service';

type AcomphUpdateComponentType = InstanceType<typeof AcomphUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const acomphSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<AcomphUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Acomph Management Update Component', () => {
    let comp: AcomphUpdateComponentType;
    let acomphServiceStub: SinonStubbedInstance<AcomphService>;

    beforeEach(() => {
      route = {};
      acomphServiceStub = sinon.createStubInstance<AcomphService>(AcomphService);
      acomphServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          acomphService: () => acomphServiceStub,
          postoMedicaoService: () =>
            sinon.createStubInstance<PostoMedicaoService>(PostoMedicaoService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(AcomphUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.acomph = acomphSample;
        acomphServiceStub.update.resolves(acomphSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(acomphServiceStub.update.calledWith(acomphSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        acomphServiceStub.create.resolves(entity);
        const wrapper = shallowMount(AcomphUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.acomph = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(acomphServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        acomphServiceStub.find.resolves(acomphSample);
        acomphServiceStub.retrieve.resolves([acomphSample]);

        // WHEN
        route = {
          params: {
            acomphId: `${acomphSample.id}`,
          },
        };
        const wrapper = shallowMount(AcomphUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.acomph).toMatchObject(acomphSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        acomphServiceStub.find.resolves(acomphSample);
        const wrapper = shallowMount(AcomphUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
