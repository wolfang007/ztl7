export interface ITipologiaPermesso {
  id?: number;
  nome?: string;
  codice?: string;
}

export class TipologiaPermesso implements ITipologiaPermesso {
  constructor(public id?: number, public nome?: string, public codice?: string) {}
}
