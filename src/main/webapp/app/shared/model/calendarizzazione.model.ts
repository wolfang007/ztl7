export interface ICalendarizzazione {
  id?: number;
  lunedi?: boolean;
  martedi?: boolean;
  mercoledi?: boolean;
  giovedi?: boolean;
  venerdi?: boolean;
  sabato?: boolean;
  domenica?: boolean;
  siRipete?: boolean;
  festivi?: boolean;
}

export class Calendarizzazione implements ICalendarizzazione {
  constructor(
    public id?: number,
    public lunedi?: boolean,
    public martedi?: boolean,
    public mercoledi?: boolean,
    public giovedi?: boolean,
    public venerdi?: boolean,
    public sabato?: boolean,
    public domenica?: boolean,
    public siRipete?: boolean,
    public festivi?: boolean
  ) {
    this.lunedi = this.lunedi || false;
    this.martedi = this.martedi || false;
    this.mercoledi = this.mercoledi || false;
    this.giovedi = this.giovedi || false;
    this.venerdi = this.venerdi || false;
    this.sabato = this.sabato || false;
    this.domenica = this.domenica || false;
    this.siRipete = this.siRipete || false;
    this.festivi = this.festivi || false;
  }
}
