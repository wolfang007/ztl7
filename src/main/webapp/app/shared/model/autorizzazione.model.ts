import { IPermessoTemporaneo } from 'app/shared/model/permesso-temporaneo.model';
import { EntityStatus } from 'app/shared/model/enumerations/entity-status.model';

export interface IAutorizzazione {
  id?: number;
  nome?: string;
  descrizione?: string;
  stato?: EntityStatus;
  permessoTemporaneos?: IPermessoTemporaneo[];
}

export class Autorizzazione implements IAutorizzazione {
  constructor(
    public id?: number,
    public nome?: string,
    public descrizione?: string,
    public stato?: EntityStatus,
    public permessoTemporaneos?: IPermessoTemporaneo[]
  ) {}
}
