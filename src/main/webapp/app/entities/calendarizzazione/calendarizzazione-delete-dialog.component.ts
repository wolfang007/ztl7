import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICalendarizzazione } from 'app/shared/model/calendarizzazione.model';
import { CalendarizzazioneService } from './calendarizzazione.service';

@Component({
  templateUrl: './calendarizzazione-delete-dialog.component.html',
})
export class CalendarizzazioneDeleteDialogComponent {
  calendarizzazione?: ICalendarizzazione;

  constructor(
    protected calendarizzazioneService: CalendarizzazioneService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.calendarizzazioneService.delete(id).subscribe(() => {
      this.eventManager.broadcast('calendarizzazioneListModification');
      this.activeModal.close();
    });
  }
}
