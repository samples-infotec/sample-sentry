import { Authority } from '@/shared/security/authority';

const CoreUserManagementComponent = () => import('@/admin/user-management/user-management.vue');
const CoreUserManagementViewComponent = () => import('@/admin/user-management/user-management-view.vue');
const CoreUserManagementEditComponent = () => import('@/admin/user-management/user-management-edit.vue');
const CoreDocsComponent = () => import('@/admin/docs/docs.vue');
const CoreConfigurationComponent = () => import('@/admin/configuration/configuration.vue');
const CoreHealthComponent = () => import('@/admin/health/health.vue');
const CoreLogsComponent = () => import('@/admin/logs/logs.vue');
const CoreMetricsComponent = () => import('@/admin/metrics/metrics.vue');

export default [
  {
    path: '/admin/user-management',
    name: 'CoreUser',
    component: CoreUserManagementComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/user-management/new',
    name: 'CoreUserCreate',
    component: CoreUserManagementEditComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/user-management/:userId/edit',
    name: 'CoreUserEdit',
    component: CoreUserManagementEditComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/user-management/:userId/view',
    name: 'CoreUserView',
    component: CoreUserManagementViewComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/docs',
    name: 'CoreDocsComponent',
    component: CoreDocsComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/health',
    name: 'CoreHealthComponent',
    component: CoreHealthComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/logs',
    name: 'CoreLogsComponent',
    component: CoreLogsComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/metrics',
    name: 'CoreMetricsComponent',
    component: CoreMetricsComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/configuration',
    name: 'CoreConfigurationComponent',
    component: CoreConfigurationComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
];
