import { EntityStatus } from 'app/shared/model/enumerations/entity-status.model';

export interface ITipologiaZona {
  id?: number;
  nome?: string;
  regoleCircolazione?: any;
  stato?: EntityStatus;
}

export class TipologiaZona implements ITipologiaZona {
  constructor(public id?: number, public nome?: string, public regoleCircolazione?: any, public stato?: EntityStatus) {}
}
