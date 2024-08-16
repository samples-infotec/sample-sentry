/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ConvocatoriaDetailComponent from '@/entities/convocatoria/convocatoria-details.vue';
import ConvocatoriaClass from '@/entities/convocatoria/convocatoria-details.component';
import ConvocatoriaService from '@/entities/convocatoria/convocatoria.service';
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
  describe('Convocatoria Management Detail Component', () => {
    let wrapper: Wrapper<ConvocatoriaClass>;
    let comp: ConvocatoriaClass;
    let convocatoriaServiceStub: SinonStubbedInstance<ConvocatoriaService>;

    beforeEach(() => {
      convocatoriaServiceStub = sinon.createStubInstance<ConvocatoriaService>(ConvocatoriaService);

      wrapper = shallowMount<ConvocatoriaClass>(ConvocatoriaDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { convocatoriaService: () => convocatoriaServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundConvocatoria = { id: 'ABC' };
        convocatoriaServiceStub.find.resolves(foundConvocatoria);

        // WHEN
        comp.retrieveConvocatoria('ABC');
        await comp.$nextTick();

        // THEN
        expect(comp.convocatoria).toBe(foundConvocatoria);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundConvocatoria = { id: 'ABC' };
        convocatoriaServiceStub.find.resolves(foundConvocatoria);

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
