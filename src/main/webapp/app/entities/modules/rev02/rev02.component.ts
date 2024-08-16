import { Component, Vue, Inject } from 'vue-property-decorator';
import ConvocatoriaService from '@/entities/convocatoria/convocatoria.service';
import { IConvocatoria } from '@/shared/model/convocatoria.model';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class Rev02 extends Vue {
  @Inject('convocatoriaService') private convocatoriaService: () => ConvocatoriaService;
  @Inject('alertService') private alertService: () => AlertService;

  public nombre = '';

  public enviar() {
    const convocatoria: IConvocatoria = {
      nombre: this.nombre,
      anio: 2024,
      fechaCreacion: new Date(),
    };

    this.convocatoriaService()
      .create(convocatoria)
      .then(res => {
        const tmp: any = res;
        this.alertService().showSuccess(this, `Agregado con ID ${tmp.ida.id}`);
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }
}
