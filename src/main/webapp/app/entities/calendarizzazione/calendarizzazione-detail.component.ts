import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICalendarizzazione } from 'app/shared/model/calendarizzazione.model';

@Component({
  selector: 'jhi-calendarizzazione-detail',
  templateUrl: './calendarizzazione-detail.component.html',
})
export class CalendarizzazioneDetailComponent implements OnInit {
  calendarizzazione: ICalendarizzazione | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ calendarizzazione }) => (this.calendarizzazione = calendarizzazione));
  }

  previousState(): void {
    window.history.back();
  }
}
