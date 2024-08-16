import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IConvocatoria } from '@/shared/model/convocatoria.model';

import ConvocatoriaService from './convocatoria.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Convocatoria extends Vue {
  @Inject('convocatoriaService') private convocatoriaService: () => ConvocatoriaService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: string = null;

  public convocatorias: IConvocatoria[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllConvocatorias();
  }

  public clear(): void {
    this.retrieveAllConvocatorias();
  }

  public retrieveAllConvocatorias(): void {
    this.isFetching = true;
    this.convocatoriaService()
      .retrieve()
      .then(
        res => {
          this.convocatorias = res.data;
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

  public prepareRemove(instance: IConvocatoria): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeConvocatoria(): void {
    this.convocatoriaService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('sampleSentryApp.convocatoria.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllConvocatorias();
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
