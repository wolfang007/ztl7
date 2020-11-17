import { Moment } from 'moment';
import { ICalendarizzazione } from 'app/shared/model/calendarizzazione.model';
import { ITipologiaPermesso } from 'app/shared/model/tipologia-permesso.model';
import { IDurataCosto } from 'app/shared/model/durata-costo.model';
import { IZona } from 'app/shared/model/zona.model';
import { IMotivazione } from 'app/shared/model/motivazione.model';
import { IAutorizzazione } from 'app/shared/model/autorizzazione.model';
import { TipoPersona } from 'app/shared/model/enumerations/tipo-persona.model';

export interface IPermessoTemporaneo {
  id?: number;
  targa?: string;
  domicilioDigitale?: string;
  tipoPersona?: TipoPersona;
  nomeRichiedente?: string;
  cognomeRichiedente?: string;
  ragioneSociale?: string;
  codiceFiscaleRichiedente?: string;
  partitaIva?: string;
  dataInizio?: Moment;
  impostaDiBollo?: boolean;
  costo?: number;
  copiaFirmataContentType?: string;
  copiaFirmata?: any;
  documentoRiconoscimentoContentType?: string;
  documentoRiconoscimento?: any;
  pagato?: boolean;
  protocollo?: string;
  calendario?: ICalendarizzazione;
  tipoPermesso?: ITipologiaPermesso;
  durata?: IDurataCosto;
  nome?: IZona;
  motivazione?: IMotivazione;
  autorizzazionis?: IAutorizzazione[];
}

export class PermessoTemporaneo implements IPermessoTemporaneo {
  constructor(
    public id?: number,
    public targa?: string,
    public domicilioDigitale?: string,
    public tipoPersona?: TipoPersona,
    public nomeRichiedente?: string,
    public cognomeRichiedente?: string,
    public ragioneSociale?: string,
    public codiceFiscaleRichiedente?: string,
    public partitaIva?: string,
    public dataInizio?: Moment,
    public impostaDiBollo?: boolean,
    public costo?: number,
    public copiaFirmataContentType?: string,
    public copiaFirmata?: any,
    public documentoRiconoscimentoContentType?: string,
    public documentoRiconoscimento?: any,
    public pagato?: boolean,
    public protocollo?: string,
    public calendario?: ICalendarizzazione,
    public tipoPermesso?: ITipologiaPermesso,
    public durata?: IDurataCosto,
    public nome?: IZona,
    public motivazione?: IMotivazione,
    public autorizzazionis?: IAutorizzazione[]
  ) {
    this.impostaDiBollo = this.impostaDiBollo || false;
    this.pagato = this.pagato || false;
  }
}
