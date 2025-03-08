import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import PostoMedicao from './posto-medicao.vue';
import PostoMedicaoService from './posto-medicao.service';
import AlertService from '@/shared/alert/alert.service';

type PostoMedicaoComponentType = InstanceType<typeof PostoMedicao>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('PostoMedicao Management Component', () => {
    let postoMedicaoServiceStub: SinonStubbedInstance<PostoMedicaoService>;
    let mountOptions: MountingOptions<PostoMedicaoComponentType>['global'];

    beforeEach(() => {
      postoMedicaoServiceStub = sinon.createStubInstance<PostoMedicaoService>(PostoMedicaoService);
      postoMedicaoServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          postoMedicaoService: () => postoMedicaoServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        postoMedicaoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(PostoMedicao, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(postoMedicaoServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.postoMedicaos[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: PostoMedicaoComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(PostoMedicao, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        postoMedicaoServiceStub.retrieve.reset();
        postoMedicaoServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        postoMedicaoServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removePostoMedicao();
        await comp.$nextTick(); // clear components

        // THEN
        expect(postoMedicaoServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(postoMedicaoServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
