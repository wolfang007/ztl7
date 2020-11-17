import { ITipologiaVeicolo } from 'app/shared/model/tipologia-veicolo.model';
import { IProfiloOrario } from 'app/shared/model/profilo-orario.model';
import { OreEnum } from 'app/shared/model/enumerations/ore-enum.model';
import { MinutiEnum } from 'app/shared/model/enumerations/minuti-enum.model';
import { EntityStatus } from 'app/shared/model/enumerations/entity-status.model';

export interface IRegolaOraria {
  id?: number;
  nome?: string;
  oraInizio?: OreEnum;
  oraFine?: OreEnum;
  minutiInizio?: MinutiEnum;
  minutiFine?: MinutiEnum;
  lunedi?: boolean;
  martedi?: boolean;
  mercoledi?: boolean;
  giovedi?: boolean;
  venerdi?: boolean;
  sabato?: boolean;
  domenica?: boolean;
  festivi?: boolean;
  stato?: EntityStatus;
  tipologiaVeicolos?: ITipologiaVeicolo[];
  profiloOrarios?: IProfiloOrario[];
}

export class RegolaOraria implements IRegolaOraria {
  constructor(
    public id?: number,
    public nome?: string,
    public oraInizio?: OreEnum,
    public oraFine?: OreEnum,
    public minutiInizio?: MinutiEnum,
    public minutiFine?: MinutiEnum,
    public lunedi?: boolean,
    public martedi?: boolean,
    public mercoledi?: boolean,
    public giovedi?: boolean,
    public venerdi?: boolean,
    public sabato?: boolean,
    public domenica?: boolean,
    public festivi?: boolean,
    public stato?: EntityStatus,
    public tipologiaVeicolos?: ITipologiaVeicolo[],
    public profiloOrarios?: IProfiloOrario[]
  ) {
    this.lunedi = this.lunedi || false;
    this.martedi = this.martedi || false;
    this.mercoledi = this.mercoledi || false;
    this.giovedi = this.giovedi || false;
    this.venerdi = this.venerdi || false;
    this.sabato = this.sabato || false;
    this.domenica = this.domenica || false;
    this.festivi = this.festivi || false;
  }
}
