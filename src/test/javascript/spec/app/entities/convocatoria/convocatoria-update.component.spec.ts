/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ConvocatoriaUpdateComponent from '@/entities/convocatoria/convocatoria-update.vue';
import ConvocatoriaClass from '@/entities/convocatoria/convocatoria-update.component';
import ConvocatoriaService from '@/entities/convocatoria/convocatoria.service';

import ProyectoService from '@/entities/proyecto/proyecto.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Convocatoria Management Update Component', () => {
    let wrapper: Wrapper<ConvocatoriaClass>;
    let comp: ConvocatoriaClass;
    let convocatoriaServiceStub: SinonStubbedInstance<ConvocatoriaService>;

    beforeEach(() => {
      convocatoriaServiceStub = sinon.createStubInstance<ConvocatoriaService>(ConvocatoriaService);

      wrapper = shallowMount<ConvocatoriaClass>(ConvocatoriaUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          convocatoriaService: () => convocatoriaServiceStub,
          alertService: () => new AlertService(),

          proyectoService: () =>
            sinon.createStubInstance<ProyectoService>(ProyectoService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 'ABC' };
        comp.convocatoria = entity;
        convocatoriaServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(convocatoriaServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.convocatoria = entity;
        convocatoriaServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(convocatoriaServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundConvocatoria = { id: 'ABC' };
        convocatoriaServiceStub.find.resolves(foundConvocatoria);
        convocatoriaServiceStub.retrieve.resolves([foundConvocatoria]);

        // WHEN
        comp.beforeRouteEnter({ params: { convocatoriaId: 'ABC' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.convocatoria).toBe(foundConvocatoria);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
