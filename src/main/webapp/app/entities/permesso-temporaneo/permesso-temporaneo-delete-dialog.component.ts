import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPermessoTemporaneo } from 'app/shared/model/permesso-temporaneo.model';
import { PermessoTemporaneoService } from './permesso-temporaneo.service';

@Component({
  templateUrl: './permesso-temporaneo-delete-dialog.component.html',
})
export class PermessoTemporaneoDeleteDialogComponent {
  permessoTemporaneo?: IPermessoTemporaneo;

  constructor(
    protected permessoTemporaneoService: PermessoTemporaneoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.permessoTemporaneoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('permessoTemporaneoListModification');
      this.activeModal.close();
    });
  }
}
