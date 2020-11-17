import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVarco } from 'app/shared/model/varco.model';
import { VarcoService } from './varco.service';

@Component({
  templateUrl: './varco-delete-dialog.component.html',
})
export class VarcoDeleteDialogComponent {
  varco?: IVarco;

  constructor(protected varcoService: VarcoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.varcoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('varcoListModification');
      this.activeModal.close();
    });
  }
}
