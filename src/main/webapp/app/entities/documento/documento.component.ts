import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IDocumento } from '@/shared/model/documento.model';

import DocumentoService from './documento.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Documento extends Vue {
  @Inject('documentoService') private documentoService: () => DocumentoService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: string = null;

  public documentos: IDocumento[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllDocumentos();
  }

  public clear(): void {
    this.retrieveAllDocumentos();
  }

  public retrieveAllDocumentos(): void {
    this.isFetching = true;
    this.documentoService()
      .retrieve()
      .then(
        res => {
          this.documentos = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IDocumento): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeDocumento(): void {
    this.documentoService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('sampleSentryApp.documento.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllDocumentos();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
