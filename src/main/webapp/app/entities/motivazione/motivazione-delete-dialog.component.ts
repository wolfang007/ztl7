import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMotivazione } from 'app/shared/model/motivazione.model';
import { MotivazioneService } from './motivazione.service';

@Component({
  templateUrl: './motivazione-delete-dialog.component.html',
})
export class MotivazioneDeleteDialogComponent {
  motivazione?: IMotivazione;

  constructor(
    protected motivazioneService: MotivazioneService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.motivazioneService.delete(id).subscribe(() => {
      this.eventManager.broadcast('motivazioneListModification');
      this.activeModal.close();
    });
  }
}
