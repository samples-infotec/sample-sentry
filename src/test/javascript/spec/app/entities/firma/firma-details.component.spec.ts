/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import FirmaDetailComponent from '@/entities/firma/firma-details.vue';
import FirmaClass from '@/entities/firma/firma-details.component';
import FirmaService from '@/entities/firma/firma.service';
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
  describe('Firma Management Detail Component', () => {
    let wrapper: Wrapper<FirmaClass>;
    let comp: FirmaClass;
    let firmaServiceStub: SinonStubbedInstance<FirmaService>;

    beforeEach(() => {
      firmaServiceStub = sinon.createStubInstance<FirmaService>(FirmaService);

      wrapper = shallowMount<FirmaClass>(FirmaDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { firmaService: () => firmaServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundFirma = { id: 'ABC' };
        firmaServiceStub.find.resolves(foundFirma);

        // WHEN
        comp.retrieveFirma('ABC');
        await comp.$nextTick();

        // THEN
        expect(comp.firma).toBe(foundFirma);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundFirma = { id: 'ABC' };
        firmaServiceStub.find.resolves(foundFirma);

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
