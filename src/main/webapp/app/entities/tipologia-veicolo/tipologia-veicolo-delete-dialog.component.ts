import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipologiaVeicolo } from 'app/shared/model/tipologia-veicolo.model';
import { TipologiaVeicoloService } from './tipologia-veicolo.service';

@Component({
  templateUrl: './tipologia-veicolo-delete-dialog.component.html',
})
export class TipologiaVeicoloDeleteDialogComponent {
  tipologiaVeicolo?: ITipologiaVeicolo;

  constructor(
    protected tipologiaVeicoloService: TipologiaVeicoloService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipologiaVeicoloService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tipologiaVeicoloListModification');
      this.activeModal.close();
    });
  }
}
