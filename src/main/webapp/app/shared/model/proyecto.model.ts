import { IDocumento } from '@/shared/model/documento.model';
import { IConvocatoria } from '@/shared/model/convocatoria.model';

export interface IProyecto {
  id?: string;
  detalle?: string | null;
  modalidad?: string;
  titulo?: string;
  solicitudClave?: string | null;
  solicitudFecha?: Date | null;
  completo?: boolean;
  documentos?: IDocumento[] | null;
  convocatoria?: IConvocatoria | null;
}

export class Proyecto implements IProyecto {
  constructor(
    public id?: string,
    public detalle?: string | null,
    public modalidad?: string,
    public titulo?: string,
    public solicitudClave?: string | null,
    public solicitudFecha?: Date | null,
    public completo?: boolean,
    public documentos?: IDocumento[] | null,
    public convocatoria?: IConvocatoria | null
  ) {
    this.completo = this.completo ?? false;
  }
}
