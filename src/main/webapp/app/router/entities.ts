import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

// prettier-ignore
const Convocatoria = () => import('@/entities/convocatoria/convocatoria.vue');
// prettier-ignore
const ConvocatoriaUpdate = () => import('@/entities/convocatoria/convocatoria-update.vue');
// prettier-ignore
const ConvocatoriaDetails = () => import('@/entities/convocatoria/convocatoria-details.vue');
// prettier-ignore
const Proyecto = () => import('@/entities/proyecto/proyecto.vue');
// prettier-ignore
const ProyectoUpdate = () => import('@/entities/proyecto/proyecto-update.vue');
// prettier-ignore
const ProyectoDetails = () => import('@/entities/proyecto/proyecto-details.vue');
// prettier-ignore
const Documento = () => import('@/entities/documento/documento.vue');
// prettier-ignore
const DocumentoUpdate = () => import('@/entities/documento/documento-update.vue');
// prettier-ignore
const DocumentoDetails = () => import('@/entities/documento/documento-details.vue');
// prettier-ignore
const Firma = () => import('@/entities/firma/firma.vue');
// prettier-ignore
const FirmaUpdate = () => import('@/entities/firma/firma-update.vue');
// prettier-ignore
const FirmaDetails = () => import('@/entities/firma/firma-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'convocatoria',
      name: 'Convocatoria',
      component: Convocatoria,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'convocatoria/new',
      name: 'ConvocatoriaCreate',
      component: ConvocatoriaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'convocatoria/:convocatoriaId/edit',
      name: 'ConvocatoriaEdit',
      component: ConvocatoriaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'convocatoria/:convocatoriaId/view',
      name: 'ConvocatoriaView',
      component: ConvocatoriaDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'proyecto',
      name: 'Proyecto',
      component: Proyecto,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'proyecto/new',
      name: 'ProyectoCreate',
      component: ProyectoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'proyecto/:proyectoId/edit',
      name: 'ProyectoEdit',
      component: ProyectoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'proyecto/:proyectoId/view',
      name: 'ProyectoView',
      component: ProyectoDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'documento',
      name: 'Documento',
      component: Documento,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'documento/new',
      name: 'DocumentoCreate',
      component: DocumentoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'documento/:documentoId/edit',
      name: 'DocumentoEdit',
      component: DocumentoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'documento/:documentoId/view',
      name: 'DocumentoView',
      component: DocumentoDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'firma',
      name: 'Firma',
      component: Firma,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'firma/new',
      name: 'FirmaCreate',
      component: FirmaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'firma/:firmaId/edit',
      name: 'FirmaEdit',
      component: FirmaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'firma/:firmaId/view',
      name: 'FirmaView',
      component: FirmaDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
