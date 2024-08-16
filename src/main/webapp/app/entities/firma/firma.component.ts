import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IFirma } from '@/shared/model/firma.model';

import FirmaService from './firma.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Firma extends Vue {
  @Inject('firmaService') private firmaService: () => FirmaService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: string = null;

  public firmas: IFirma[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllFirmas();
  }

  public clear(): void {
    this.retrieveAllFirmas();
  }

  public retrieveAllFirmas(): void {
    this.isFetching = true;
    this.firmaService()
      .retrieve()
      .then(
        res => {
          this.firmas = res.data;
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

  public prepareRemove(instance: IFirma): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeFirma(): void {
    this.firmaService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('sampleSentryApp.firma.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllFirmas();
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
