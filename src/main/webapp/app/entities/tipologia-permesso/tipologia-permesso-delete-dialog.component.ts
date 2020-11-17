import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipologiaPermesso } from 'app/shared/model/tipologia-permesso.model';
import { TipologiaPermessoService } from './tipologia-permesso.service';

@Component({
  templateUrl: './tipologia-permesso-delete-dialog.component.html',
})
export class TipologiaPermessoDeleteDialogComponent {
  tipologiaPermesso?: ITipologiaPermesso;

  constructor(
    protected tipologiaPermessoService: TipologiaPermessoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipologiaPermessoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tipologiaPermessoListModification');
      this.activeModal.close();
    });
  }
}
