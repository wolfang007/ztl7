import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFestivita } from 'app/shared/model/festivita.model';
import { FestivitaService } from './festivita.service';

@Component({
  templateUrl: './festivita-delete-dialog.component.html',
})
export class FestivitaDeleteDialogComponent {
  festivita?: IFestivita;

  constructor(protected festivitaService: FestivitaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.festivitaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('festivitaListModification');
      this.activeModal.close();
    });
  }
}
