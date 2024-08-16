// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.common with an alias.
import Vue from 'vue';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { setupAxiosInterceptors } from '@/shared/config/axios-interceptor';

import App from './app.vue';
import Vue2Filters from 'vue2-filters';
import { ToastPlugin } from 'bootstrap-vue';
import router from './router';
import axios from 'axios';
import * as Sentry from '@sentry/vue';
import { Integrations } from '@sentry/tracing';
import * as config from './shared/config/config';
import * as bootstrapVueConfig from './shared/config/config-bootstrap-vue';
import JhiItemCountComponent from './shared/jhi-item-count.vue';
import JhiSortIndicatorComponent from './shared/sort/jhi-sort-indicator.vue';
import InfiniteLoading from 'vue-infinite-loading';
import HealthService from './admin/health/health.service';
import MetricsService from './admin/metrics/metrics.service';
import LogsService from './admin/logs/logs.service';
import ConfigurationService from '@/admin/configuration/configuration.service';
import ActivateService from './account/activate/activate.service';
import RegisterService from './account/register/register.service';
import UserManagementService from './admin/user-management/user-management.service';
import LoginService from './account/login.service';
import AccountService from './account/account.service';
import AlertService from './shared/alert/alert.service';

import '../content/scss/global.scss';
import '../content/scss/vendor.scss';
import TranslationService from '@/locale/translation.service';
/* tslint:disable */

// jhipster-needle-add-entity-service-to-main-import - JHipster will import entities services here

/* tslint:enable */
Vue.config.productionTip = false;
config.initVueApp(Vue);
config.initFortAwesome(Vue);
bootstrapVueConfig.initBootstrapVue(Vue);
Vue.use(Vue2Filters);
Vue.use(ToastPlugin);
Vue.component('font-awesome-icon', FontAwesomeIcon);
Vue.component('jhi-item-count', JhiItemCountComponent);
Vue.component('jhi-sort-indicator', JhiSortIndicatorComponent);
Vue.component('infinite-loading', InfiniteLoading);
const i18n = config.initI18N(Vue);
const store = config.initVueXStore(Vue);

const translationService = new TranslationService(store, i18n);
const loginService = new LoginService();
const accountService = new AccountService(store, translationService, router);

/* tslint:disable */
axios
  .get('sentry/config')
  .then(res => {
    if (res?.data?.dsn) {
      let environment = 'local';
      let tracesSampleRate: 0.25;

      if (res.data.environment) {
        environment = res.data['environment'];
      }

      if (res.data.tracesSampleRate) {
        tracesSampleRate = res.data['tracesSampleRate'];
      }

      Sentry.init({
        Vue,
        dsn: res.data['dsn'],
        beforeSend(event, hint) {
          // Check if it is an exception, and if so, show the report dialog
          if (event.exception) {
            let value = null;

            if (event?.exception?.values?.length) {
              value = event.exception.values.find(e => e.value.indexOf('navigation guard') != -1);
            } else {
              console.log(JSON.stringify(event.exception));
            }

            // Check isn't navigation guard exception
            if (!value) {
              let feedBackConfig;

              if (store.getters.account?.firstName) {
                const username = store.getters.account.lastName
                  ? `${store.getters.account.firstName} ${store.getters.account.lastName}`
                  : store.getters.account.firstName;

                feedBackConfig = { eventId: event.event_id, user: { name: username, email: store.getters.account.email } };
              } else {
                feedBackConfig = { eventId: event.event_id };
              }

              Sentry.showReportDialog(feedBackConfig);
            }
          }
          return event;
        },
        replaysSessionSampleRate: 0.25,
        // If the entire session is not sampled, use the below sample rate to sample
        // sessions when an error occurs.
        replaysOnErrorSampleRate: 1.0,
        integrations: [
          new Integrations.BrowserTracing({
            routingInstrumentation: Sentry.vueRouterInstrumentation(router),
            tracingOrigins: ['localhost', 'demo-sentry-7f000101.nip.io', /^\//],
          }),
          new Sentry.Replay(),
        ],
        // This sets the sample rate to be 10%. You may want this to be 100% while
        // in development and sample at a lower rate in production
        tracesSampleRate: tracesSampleRate,
        environment: environment,
        release: VERSION,
        dist: APP_DISTRIBUTION,
      });
    } else {
      console.log('Sentry sin activar');
    }
  })
  .catch(err => {
    console.error('Error al realizar la configuración de sentry');
    console.error(err);
  });
/* tslint:enable */

router.beforeEach(async (to, from, next) => {
  if (!to.matched.length) {
    next('/not-found');
  } else if (to.meta && to.meta.authorities && to.meta.authorities.length > 0) {
    accountService.hasAnyAuthorityAndCheckAuth(to.meta.authorities).then(value => {
      if (!value) {
        sessionStorage.setItem('requested-url', to.fullPath);
        next('/forbidden');
      } else {
        next();
      }
    });
  } else {
    // no authorities, so just proceed
    next();
  }
});

/* tslint:disable */
const vue = new Vue({
  el: '#app',
  components: { App },
  template: '<App/>',
  router,
  provide: {
    loginService: () => loginService,
    activateService: () => new ActivateService(),
    registerService: () => new RegisterService(),
    userManagementService: () => new UserManagementService(),
    healthService: () => new HealthService(),
    configurationService: () => new ConfigurationService(),
    logsService: () => new LogsService(),
    metricsService: () => new MetricsService(),

    translationService: () => translationService,
    // jhipster-needle-add-entity-service-to-main - JHipster will import entities services here
    accountService: () => accountService,
    alertService: () => new AlertService(),
  },
  i18n,
  store,
});

setupAxiosInterceptors(
  error => {
    const url = error.response?.config?.url;
    const status = error.status || error.response.status;
    if (status === 401) {
      // Store logged out state.
      store.commit('logout');
      if (!url.endsWith('api/account') && !url.endsWith('api/authenticate')) {
        // Ask for a new authentication
        loginService.openLogin(vue);
        return;
      }
    }
    console.log('Unauthorized!');
    return Promise.reject(error);
  },
  error => {
    console.log('Server error!');
    return Promise.reject(error);
  }
);
