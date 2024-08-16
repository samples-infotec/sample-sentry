import { IFirma } from '@/shared/model/firma.model';
import { IProyecto } from '@/shared/model/proyecto.model';

export interface IDocumento {
  id?: string;
  uri?: string;
  data?: string;
  firmas?: IFirma[] | null;
  proyecto?: IProyecto | null;
}

export class Documento implements IDocumento {
  constructor(
    public id?: string,
    public uri?: string,
    public data?: string,
    public firmas?: IFirma[] | null,
    public proyecto?: IProyecto | null
  ) {}
}
