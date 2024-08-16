/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import FirmaComponent from '@/entities/firma/firma.vue';
import FirmaClass from '@/entities/firma/firma.component';
import FirmaService from '@/entities/firma/firma.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Firma Management Component', () => {
    let wrapper: Wrapper<FirmaClass>;
    let comp: FirmaClass;
    let firmaServiceStub: SinonStubbedInstance<FirmaService>;

    beforeEach(() => {
      firmaServiceStub = sinon.createStubInstance<FirmaService>(FirmaService);
      firmaServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<FirmaClass>(FirmaComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          firmaService: () => firmaServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      firmaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

      // WHEN
      comp.retrieveAllFirmas();
      await comp.$nextTick();

      // THEN
      expect(firmaServiceStub.retrieve.called).toBeTruthy();
      expect(comp.firmas[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      firmaServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 'ABC' });
      expect(firmaServiceStub.retrieve.callCount).toEqual(1);

      comp.removeFirma();
      await comp.$nextTick();

      // THEN
      expect(firmaServiceStub.delete.called).toBeTruthy();
      expect(firmaServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
