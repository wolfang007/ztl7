import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDurataCosto } from 'app/shared/model/durata-costo.model';
import { DurataCostoService } from './durata-costo.service';

@Component({
  templateUrl: './durata-costo-delete-dialog.component.html',
})
export class DurataCostoDeleteDialogComponent {
  durataCosto?: IDurataCosto;

  constructor(
    protected durataCostoService: DurataCostoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.durataCostoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('durataCostoListModification');
      this.activeModal.close();
    });
  }
}
