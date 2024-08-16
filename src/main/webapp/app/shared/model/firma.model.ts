import { IDocumento } from '@/shared/model/documento.model';

export interface IFirma {
  id?: string;
  rol?: string;
  documentoUri?: string | null;
  certificado?: string | null;
  rfc?: string;
  curp?: string | null;
  signature?: string | null;
  active?: boolean;
  documento?: IDocumento | null;
}

export class Firma implements IFirma {
  constructor(
    public id?: string,
    public rol?: string,
    public documentoUri?: string | null,
    public certificado?: string | null,
    public rfc?: string,
    public curp?: string | null,
    public signature?: string | null,
    public active?: boolean,
    public documento?: IDocumento | null
  ) {
    this.active = this.active ?? false;
  }
}
