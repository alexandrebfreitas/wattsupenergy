import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Acomph from './acomph.vue';
import AcomphService from './acomph.service';
import AlertService from '@/shared/alert/alert.service';

type AcomphComponentType = InstanceType<typeof Acomph>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Acomph Management Component', () => {
    let acomphServiceStub: SinonStubbedInstance<AcomphService>;
    let mountOptions: MountingOptions<AcomphComponentType>['global'];

    beforeEach(() => {
      acomphServiceStub = sinon.createStubInstance<AcomphService>(AcomphService);
      acomphServiceStub.retrieve.resolves({ headers: {} });

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
          acomphService: () => acomphServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        acomphServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Acomph, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(acomphServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.acomphs[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: AcomphComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Acomph, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        acomphServiceStub.retrieve.reset();
        acomphServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        acomphServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeAcomph();
        await comp.$nextTick(); // clear components

        // THEN
        expect(acomphServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(acomphServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
