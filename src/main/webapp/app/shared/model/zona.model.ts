import { IProfiloOrario } from 'app/shared/model/profilo-orario.model';
import { ITipologiaZona } from 'app/shared/model/tipologia-zona.model';
import { IGruppoVarchi } from 'app/shared/model/gruppo-varchi.model';
import { EntityStatus } from 'app/shared/model/enumerations/entity-status.model';

export interface IZona {
  id?: number;
  nome?: string;
  stato?: EntityStatus;
  profiloOrario?: IProfiloOrario;
  tipologiaZona?: ITipologiaZona;
  gruppoVarchis?: IGruppoVarchi[];
}

export class Zona implements IZona {
  constructor(
    public id?: number,
    public nome?: string,
    public stato?: EntityStatus,
    public profiloOrario?: IProfiloOrario,
    public tipologiaZona?: ITipologiaZona,
    public gruppoVarchis?: IGruppoVarchi[]
  ) {}
}
