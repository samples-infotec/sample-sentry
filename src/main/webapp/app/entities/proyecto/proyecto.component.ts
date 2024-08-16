import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IProyecto } from '@/shared/model/proyecto.model';

import ProyectoService from './proyecto.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Proyecto extends Vue {
  @Inject('proyectoService') private proyectoService: () => ProyectoService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: string = null;

  public proyectos: IProyecto[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllProyectos();
  }

  public clear(): void {
    this.retrieveAllProyectos();
  }

  public retrieveAllProyectos(): void {
    this.isFetching = true;
    this.proyectoService()
      .retrieve()
      .then(
        res => {
          this.proyectos = res.data;
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

  public prepareRemove(instance: IProyecto): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeProyecto(): void {
    this.proyectoService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('sampleSentryApp.proyecto.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllProyectos();
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
