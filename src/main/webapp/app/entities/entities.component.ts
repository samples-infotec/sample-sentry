import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import ConvocatoriaService from './convocatoria/convocatoria.service';
import ProyectoService from './proyecto/proyecto.service';
import DocumentoService from './documento/documento.service';
import FirmaService from './firma/firma.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('convocatoriaService') private convocatoriaService = () => new ConvocatoriaService();
  @Provide('proyectoService') private proyectoService = () => new ProyectoService();
  @Provide('documentoService') private documentoService = () => new DocumentoService();
  @Provide('firmaService') private firmaService = () => new FirmaService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
