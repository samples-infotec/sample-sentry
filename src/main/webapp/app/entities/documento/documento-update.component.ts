import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import FirmaService from '@/entities/firma/firma.service';
import { IFirma } from '@/shared/model/firma.model';

import ProyectoService from '@/entities/proyecto/proyecto.service';
import { IProyecto } from '@/shared/model/proyecto.model';

import { IDocumento, Documento } from '@/shared/model/documento.model';
import DocumentoService from './documento.service';

const validations: any = {
  documento: {
    uri: {
      required,
    },
    data: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class DocumentoUpdate extends Vue {
  @Inject('documentoService') private documentoService: () => DocumentoService;
  @Inject('alertService') private alertService: () => AlertService;

  public documento: IDocumento = new Documento();

  @Inject('firmaService') private firmaService: () => FirmaService;

  public firmas: IFirma[] = [];

  @Inject('proyectoService') private proyectoService: () => ProyectoService;

  public proyectos: IProyecto[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.documentoId) {
        vm.retrieveDocumento(to.params.documentoId);
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
    if (this.documento.id) {
      this.documentoService()
        .update(this.documento)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('sampleSentryApp.documento.updated', { param: param.id });
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
      this.documentoService()
        .create(this.documento)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('sampleSentryApp.documento.created', { param: param.id });
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

  public retrieveDocumento(documentoId): void {
    this.documentoService()
      .find(documentoId)
      .then(res => {
        this.documento = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.firmaService()
      .retrieve()
      .then(res => {
        this.firmas = res.data;
      });
    this.proyectoService()
      .retrieve()
      .then(res => {
        this.proyectos = res.data;
      });
  }
}
