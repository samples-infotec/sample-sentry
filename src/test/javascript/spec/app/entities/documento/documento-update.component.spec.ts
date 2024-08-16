/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import DocumentoUpdateComponent from '@/entities/documento/documento-update.vue';
import DocumentoClass from '@/entities/documento/documento-update.component';
import DocumentoService from '@/entities/documento/documento.service';

import FirmaService from '@/entities/firma/firma.service';

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
  describe('Documento Management Update Component', () => {
    let wrapper: Wrapper<DocumentoClass>;
    let comp: DocumentoClass;
    let documentoServiceStub: SinonStubbedInstance<DocumentoService>;

    beforeEach(() => {
      documentoServiceStub = sinon.createStubInstance<DocumentoService>(DocumentoService);

      wrapper = shallowMount<DocumentoClass>(DocumentoUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          documentoService: () => documentoServiceStub,
          alertService: () => new AlertService(),

          firmaService: () =>
            sinon.createStubInstance<FirmaService>(FirmaService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

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
        comp.documento = entity;
        documentoServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(documentoServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.documento = entity;
        documentoServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(documentoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDocumento = { id: 'ABC' };
        documentoServiceStub.find.resolves(foundDocumento);
        documentoServiceStub.retrieve.resolves([foundDocumento]);

        // WHEN
        comp.beforeRouteEnter({ params: { documentoId: 'ABC' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.documento).toBe(foundDocumento);
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
