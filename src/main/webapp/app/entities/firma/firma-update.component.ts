import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import DocumentoService from '@/entities/documento/documento.service';
import { IDocumento } from '@/shared/model/documento.model';

import { IFirma, Firma } from '@/shared/model/firma.model';
import FirmaService from './firma.service';

const validations: any = {
  firma: {
    rol: {
      required,
    },
    documentoUri: {},
    certificado: {},
    rfc: {
      required,
    },
    curp: {},
    signature: {},
    active: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class FirmaUpdate extends Vue {
  @Inject('firmaService') private firmaService: () => FirmaService;
  @Inject('alertService') private alertService: () => AlertService;

  public firma: IFirma = new Firma();

  @Inject('documentoService') private documentoService: () => DocumentoService;

  public documentos: IDocumento[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.firmaId) {
        vm.retrieveFirma(to.params.firmaId);
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
    if (this.firma.id) {
      this.firmaService()
        .update(this.firma)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('sampleSentryApp.firma.updated', { param: param.id });
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
      this.firmaService()
        .create(this.firma)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('sampleSentryApp.firma.created', { param: param.id });
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

  public retrieveFirma(firmaId): void {
    this.firmaService()
      .find(firmaId)
      .then(res => {
        this.firma = res;
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
  }
}
