/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ProyectoComponent from '@/entities/proyecto/proyecto.vue';
import ProyectoClass from '@/entities/proyecto/proyecto.component';
import ProyectoService from '@/entities/proyecto/proyecto.service';
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
  describe('Proyecto Management Component', () => {
    let wrapper: Wrapper<ProyectoClass>;
    let comp: ProyectoClass;
    let proyectoServiceStub: SinonStubbedInstance<ProyectoService>;

    beforeEach(() => {
      proyectoServiceStub = sinon.createStubInstance<ProyectoService>(ProyectoService);
      proyectoServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ProyectoClass>(ProyectoComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          proyectoService: () => proyectoServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      proyectoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

      // WHEN
      comp.retrieveAllProyectos();
      await comp.$nextTick();

      // THEN
      expect(proyectoServiceStub.retrieve.called).toBeTruthy();
      expect(comp.proyectos[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      proyectoServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 'ABC' });
      expect(proyectoServiceStub.retrieve.callCount).toEqual(1);

      comp.removeProyecto();
      await comp.$nextTick();

      // THEN
      expect(proyectoServiceStub.delete.called).toBeTruthy();
      expect(proyectoServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
