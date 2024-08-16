import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDocumento } from '@/shared/model/documento.model';
import DocumentoService from './documento.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class DocumentoDetails extends Vue {
  @Inject('documentoService') private documentoService: () => DocumentoService;
  @Inject('alertService') private alertService: () => AlertService;

  public documento: IDocumento = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.documentoId) {
        vm.retrieveDocumento(to.params.documentoId);
      }
    });
  }

  public retrieveDocumento(documentoId) {
    this.documentoService()
      .find(documentoId)
      .then(res => {
        this.documento = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
