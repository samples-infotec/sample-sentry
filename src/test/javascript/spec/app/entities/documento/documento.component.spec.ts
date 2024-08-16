/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import DocumentoComponent from '@/entities/documento/documento.vue';
import DocumentoClass from '@/entities/documento/documento.component';
import DocumentoService from '@/entities/documento/documento.service';
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
  describe('Documento Management Component', () => {
    let wrapper: Wrapper<DocumentoClass>;
    let comp: DocumentoClass;
    let documentoServiceStub: SinonStubbedInstance<DocumentoService>;

    beforeEach(() => {
      documentoServiceStub = sinon.createStubInstance<DocumentoService>(DocumentoService);
      documentoServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<DocumentoClass>(DocumentoComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          documentoService: () => documentoServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      documentoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

      // WHEN
      comp.retrieveAllDocumentos();
      await comp.$nextTick();

      // THEN
      expect(documentoServiceStub.retrieve.called).toBeTruthy();
      expect(comp.documentos[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      documentoServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 'ABC' });
      expect(documentoServiceStub.retrieve.callCount).toEqual(1);

      comp.removeDocumento();
      await comp.$nextTick();

      // THEN
      expect(documentoServiceStub.delete.called).toBeTruthy();
      expect(documentoServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
