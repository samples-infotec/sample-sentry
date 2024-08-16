import { IProyecto } from '@/shared/model/proyecto.model';

export interface IConvocatoria {
  id?: string;
  nombre?: string;
  anio?: number | null;
  fechaCreacion?: Date | null;
  proyectos?: IProyecto[] | null;
}

export class Convocatoria implements IConvocatoria {
  constructor(
    public id?: string,
    public nombre?: string,
    public anio?: number | null,
    public fechaCreacion?: Date | null,
    public proyectos?: IProyecto[] | null
  ) {}
}
