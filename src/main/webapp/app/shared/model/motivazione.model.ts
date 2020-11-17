export interface IMotivazione {
  id?: number;
  descrizione?: string;
}

export class Motivazione implements IMotivazione {
  constructor(public id?: number, public descrizione?: string) {}
}
