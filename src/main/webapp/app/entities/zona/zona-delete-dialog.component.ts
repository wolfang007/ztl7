import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IZona } from 'app/shared/model/zona.model';
import { ZonaService } from './zona.service';

@Component({
  templateUrl: './zona-delete-dialog.component.html',
})
export class ZonaDeleteDialogComponent {
  zona?: IZona;

  constructor(protected zonaService: ZonaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.zonaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('zonaListModification');
      this.activeModal.close();
    });
  }
}
