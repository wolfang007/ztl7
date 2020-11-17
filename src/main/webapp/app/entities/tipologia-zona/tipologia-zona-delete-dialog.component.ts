import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipologiaZona } from 'app/shared/model/tipologia-zona.model';
import { TipologiaZonaService } from './tipologia-zona.service';

@Component({
  templateUrl: './tipologia-zona-delete-dialog.component.html',
})
export class TipologiaZonaDeleteDialogComponent {
  tipologiaZona?: ITipologiaZona;

  constructor(
    protected tipologiaZonaService: TipologiaZonaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipologiaZonaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tipologiaZonaListModification');
      this.activeModal.close();
    });
  }
}
