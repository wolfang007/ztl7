import { IGruppoVarchi } from 'app/shared/model/gruppo-varchi.model';
import { EntityStatus } from 'app/shared/model/enumerations/entity-status.model';

export interface IVarco {
  id?: number;
  codice?: string;
  descrizionePosizione?: string;
  stato?: EntityStatus;
  gruppoVarchis?: IGruppoVarchi[];
}

export class Varco implements IVarco {
  constructor(
    public id?: number,
    public codice?: string,
    public descrizionePosizione?: string,
    public stato?: EntityStatus,
    public gruppoVarchis?: IGruppoVarchi[]
  ) {}
}
