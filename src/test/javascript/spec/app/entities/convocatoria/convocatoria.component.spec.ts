/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ConvocatoriaComponent from '@/entities/convocatoria/convocatoria.vue';
import ConvocatoriaClass from '@/entities/convocatoria/convocatoria.component';
import ConvocatoriaService from '@/entities/convocatoria/convocatoria.service';
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
  describe('Convocatoria Management Component', () => {
    let wrapper: Wrapper<ConvocatoriaClass>;
    let comp: ConvocatoriaClass;
    let convocatoriaServiceStub: SinonStubbedInstance<ConvocatoriaService>;

    beforeEach(() => {
      convocatoriaServiceStub = sinon.createStubInstance<ConvocatoriaService>(ConvocatoriaService);
      convocatoriaServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ConvocatoriaClass>(ConvocatoriaComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          convocatoriaService: () => convocatoriaServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      convocatoriaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

      // WHEN
      comp.retrieveAllConvocatorias();
      await comp.$nextTick();

      // THEN
      expect(convocatoriaServiceStub.retrieve.called).toBeTruthy();
      expect(comp.convocatorias[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      convocatoriaServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 'ABC' });
      expect(convocatoriaServiceStub.retrieve.callCount).toEqual(1);

      comp.removeConvocatoria();
      await comp.$nextTick();

      // THEN
      expect(convocatoriaServiceStub.delete.called).toBeTruthy();
      expect(convocatoriaServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
