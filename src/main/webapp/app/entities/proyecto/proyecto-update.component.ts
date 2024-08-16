import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import DocumentoService from '@/entities/documento/documento.service';
import { IDocumento } from '@/shared/model/documento.model';

import ConvocatoriaService from '@/entities/convocatoria/convocatoria.service';
import { IConvocatoria } from '@/shared/model/convocatoria.model';

import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';
import ProyectoService from './proyecto.service';

const validations: any = {
  proyecto: {
    detalle: {},
    modalidad: {
      required,
    },
    titulo: {
      required,
    },
    solicitudClave: {},
    solicitudFecha: {},
    completo: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class ProyectoUpdate extends Vue {
  @Inject('proyectoService') private proyectoService: () => ProyectoService;
  @Inject('alertService') private alertService: () => AlertService;

  public proyecto: IProyecto = new Proyecto();

  @Inject('documentoService') private documentoService: () => DocumentoService;

  public documentos: IDocumento[] = [];

  @Inject('convocatoriaService') private convocatoriaService: () => ConvocatoriaService;

  public convocatorias: IConvocatoria[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.proyectoId) {
        vm.retrieveProyecto(to.params.proyectoId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.proyecto.id) {
      this.proyectoService()
        .update(this.proyecto)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('sampleSentryApp.proyecto.updated', { param: param.id });
          return (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.proyectoService()
        .create(this.proyecto)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('sampleSentryApp.proyecto.created', { param: param.id });
          (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.proyecto[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.proyecto[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.proyecto[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.proyecto[field] = null;
    }
  }

  public retrieveProyecto(proyectoId): void {
    this.proyectoService()
      .find(proyectoId)
      .then(res => {
        res.solicitudFecha = new Date(res.solicitudFecha);
        this.proyecto = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.documentoService()
      .retrieve()
      .then(res => {
        this.documentos = res.data;
      });
    this.convocatoriaService()
      .retrieve()
      .then(res => {
        this.convocatorias = res.data;
      });
  }
}
