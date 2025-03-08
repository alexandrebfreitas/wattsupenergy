import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import PostoMedicaoUpdate from './posto-medicao-update.vue';
import PostoMedicaoService from './posto-medicao.service';
import AlertService from '@/shared/alert/alert.service';

type PostoMedicaoUpdateComponentType = InstanceType<typeof PostoMedicaoUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const postoMedicaoSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<PostoMedicaoUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('PostoMedicao Management Update Component', () => {
    let comp: PostoMedicaoUpdateComponentType;
    let postoMedicaoServiceStub: SinonStubbedInstance<PostoMedicaoService>;

    beforeEach(() => {
      route = {};
      postoMedicaoServiceStub = sinon.createStubInstance<PostoMedicaoService>(PostoMedicaoService);
      postoMedicaoServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          postoMedicaoService: () => postoMedicaoServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(PostoMedicaoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.postoMedicao = postoMedicaoSample;
        postoMedicaoServiceStub.update.resolves(postoMedicaoSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(postoMedicaoServiceStub.update.calledWith(postoMedicaoSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        postoMedicaoServiceStub.create.resolves(entity);
        const wrapper = shallowMount(PostoMedicaoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.postoMedicao = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(postoMedicaoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        postoMedicaoServiceStub.find.resolves(postoMedicaoSample);
        postoMedicaoServiceStub.retrieve.resolves([postoMedicaoSample]);

        // WHEN
        route = {
          params: {
            postoMedicaoId: `${postoMedicaoSample.id}`,
          },
        };
        const wrapper = shallowMount(PostoMedicaoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.postoMedicao).toMatchObject(postoMedicaoSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        postoMedicaoServiceStub.find.resolves(postoMedicaoSample);
        const wrapper = shallowMount(PostoMedicaoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
