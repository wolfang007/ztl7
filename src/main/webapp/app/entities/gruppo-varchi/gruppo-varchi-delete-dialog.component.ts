import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGruppoVarchi } from 'app/shared/model/gruppo-varchi.model';
import { GruppoVarchiService } from './gruppo-varchi.service';

@Component({
  templateUrl: './gruppo-varchi-delete-dialog.component.html',
})
export class GruppoVarchiDeleteDialogComponent {
  gruppoVarchi?: IGruppoVarchi;

  constructor(
    protected gruppoVarchiService: GruppoVarchiService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.gruppoVarchiService.delete(id).subscribe(() => {
      this.eventManager.broadcast('gruppoVarchiListModification');
      this.activeModal.close();
    });
  }
}
