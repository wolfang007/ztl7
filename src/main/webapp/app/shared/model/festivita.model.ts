import { Moment } from 'moment';

export interface IFestivita {
  id?: number;
  nome?: Moment;
}

export class Festivita implements IFestivita {
  constructor(public id?: number, public nome?: Moment) {}
}
