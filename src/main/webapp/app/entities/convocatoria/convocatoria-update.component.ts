import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, numeric, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import ProyectoService from '@/entities/proyecto/proyecto.service';
import { IProyecto } from '@/shared/model/proyecto.model';

import { IConvocatoria, Convocatoria } from '@/shared/model/convocatoria.model';
import ConvocatoriaService from './convocatoria.service';

const validations: any = {
  convocatoria: {
    nombre: {
      required,
    },
    anio: {
      numeric,
      min: minValue(1960),
      max: maxValue(2099),
    },
    fechaCreacion: {},
  },
};

@Component({
  validations,
})
export default class ConvocatoriaUpdate extends Vue {
  @Inject('convocatoriaService') private convocatoriaService: () => ConvocatoriaService;
  @Inject('alertService') private alertService: () => AlertService;

  public convocatoria: IConvocatoria = new Convocatoria();

  @Inject('proyectoService') private proyectoService: () => ProyectoService;

  public proyectos: IProyecto[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.convocatoriaId) {
        vm.retrieveConvocatoria(to.params.convocatoriaId);
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
    if (this.convocatoria.id) {
      this.convocatoriaService()
        .update(this.convocatoria)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('sampleSentryApp.convocatoria.updated', { param: param.id });
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
      this.convocatoriaService()
        .create(this.convocatoria)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('sampleSentryApp.convocatoria.created', { param: param.id });
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

  public retrieveConvocatoria(convocatoriaId): void {
    this.convocatoriaService()
      .find(convocatoriaId)
      .then(res => {
        this.convocatoria = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.proyectoService()
      .retrieve()
      .then(res => {
        this.proyectos = res.data;
      });
  }
}
