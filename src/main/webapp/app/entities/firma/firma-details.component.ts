import { Component, Vue, Inject } from 'vue-property-decorator';

import { IFirma } from '@/shared/model/firma.model';
import FirmaService from './firma.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class FirmaDetails extends Vue {
  @Inject('firmaService') private firmaService: () => FirmaService;
  @Inject('alertService') private alertService: () => AlertService;

  public firma: IFirma = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.firmaId) {
        vm.retrieveFirma(to.params.firmaId);
      }
    });
  }

  public retrieveFirma(firmaId) {
    this.firmaService()
      .find(firmaId)
      .then(res => {
        this.firma = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
