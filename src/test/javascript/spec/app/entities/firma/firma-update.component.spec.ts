/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import FirmaUpdateComponent from '@/entities/firma/firma-update.vue';
import FirmaClass from '@/entities/firma/firma-update.component';
import FirmaService from '@/entities/firma/firma.service';

import DocumentoService from '@/entities/documento/documento.service';
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
  describe('Firma Management Update Component', () => {
    let wrapper: Wrapper<FirmaClass>;
    let comp: FirmaClass;
    let firmaServiceStub: SinonStubbedInstance<FirmaService>;

    beforeEach(() => {
      firmaServiceStub = sinon.createStubInstance<FirmaService>(FirmaService);

      wrapper = shallowMount<FirmaClass>(FirmaUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          firmaService: () => firmaServiceStub,
          alertService: () => new AlertService(),

          documentoService: () =>
            sinon.createStubInstance<DocumentoService>(DocumentoService, {
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
        comp.firma = entity;
        firmaServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(firmaServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.firma = entity;
        firmaServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(firmaServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundFirma = { id: 'ABC' };
        firmaServiceStub.find.resolves(foundFirma);
        firmaServiceStub.retrieve.resolves([foundFirma]);

        // WHEN
        comp.beforeRouteEnter({ params: { firmaId: 'ABC' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.firma).toBe(foundFirma);
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
