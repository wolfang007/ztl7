import { IVarco } from 'app/shared/model/varco.model';
import { IZona } from 'app/shared/model/zona.model';
import { EntityStatus } from 'app/shared/model/enumerations/entity-status.model';

export interface IGruppoVarchi {
  id?: number;
  nome?: string;
  descrizione?: string;
  descrioneIntervalli?: string;
  stato?: EntityStatus;
  posiziones?: IVarco[];
  zonas?: IZona[];
}

export class GruppoVarchi implements IGruppoVarchi {
  constructor(
    public id?: number,
    public nome?: string,
    public descrizione?: string,
    public descrioneIntervalli?: string,
    public stato?: EntityStatus,
    public posiziones?: IVarco[],
    public zonas?: IZona[]
  ) {}
}
