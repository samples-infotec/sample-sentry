import { Component, Vue, Inject } from 'vue-property-decorator';

import { IConvocatoria } from '@/shared/model/convocatoria.model';
import ConvocatoriaService from './convocatoria.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ConvocatoriaDetails extends Vue {
  @Inject('convocatoriaService') private convocatoriaService: () => ConvocatoriaService;
  @Inject('alertService') private alertService: () => AlertService;

  public convocatoria: IConvocatoria = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.convocatoriaId) {
        vm.retrieveConvocatoria(to.params.convocatoriaId);
      }
    });
  }

  public retrieveConvocatoria(convocatoriaId) {
    this.convocatoriaService()
      .find(convocatoriaId)
      .then(res => {
        this.convocatoria = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
