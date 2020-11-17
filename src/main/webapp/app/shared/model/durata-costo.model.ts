export interface IDurataCosto {
  id?: number;
  durata?: string;
  descrizione?: string;
  costo?: number;
}

export class DurataCosto implements IDurataCosto {
  constructor(public id?: number, public durata?: string, public descrizione?: string, public costo?: number) {}
}
