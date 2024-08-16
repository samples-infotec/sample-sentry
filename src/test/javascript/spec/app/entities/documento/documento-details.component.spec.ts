/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import DocumentoDetailComponent from '@/entities/documento/documento-details.vue';
import DocumentoClass from '@/entities/documento/documento-details.component';
import DocumentoService from '@/entities/documento/documento.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Documento Management Detail Component', () => {
    let wrapper: Wrapper<DocumentoClass>;
    let comp: DocumentoClass;
    let documentoServiceStub: SinonStubbedInstance<DocumentoService>;

    beforeEach(() => {
      documentoServiceStub = sinon.createStubInstance<DocumentoService>(DocumentoService);

      wrapper = shallowMount<DocumentoClass>(DocumentoDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { documentoService: () => documentoServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDocumento = { id: 'ABC' };
        documentoServiceStub.find.resolves(foundDocumento);

        // WHEN
        comp.retrieveDocumento('ABC');
        await comp.$nextTick();

        // THEN
        expect(comp.documento).toBe(foundDocumento);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDocumento = { id: 'ABC' };
        documentoServiceStub.find.resolves(foundDocumento);

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
