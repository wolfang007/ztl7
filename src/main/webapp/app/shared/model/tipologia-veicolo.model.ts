import { IRegolaOraria } from 'app/shared/model/regola-oraria.model';
import { EntityStatus } from 'app/shared/model/enumerations/entity-status.model';

export interface ITipologiaVeicolo {
  id?: number;
  nome?: string;
  stato?: EntityStatus;
  regolaOrarias?: IRegolaOraria[];
}

export class TipologiaVeicolo implements ITipologiaVeicolo {
  constructor(public id?: number, public nome?: string, public stato?: EntityStatus, public regolaOrarias?: IRegolaOraria[]) {}
}
