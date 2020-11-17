import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRegolaOraria } from 'app/shared/model/regola-oraria.model';
import { RegolaOrariaService } from './regola-oraria.service';

@Component({
  templateUrl: './regola-oraria-delete-dialog.component.html',
})
export class RegolaOrariaDeleteDialogComponent {
  regolaOraria?: IRegolaOraria;

  constructor(
    protected regolaOrariaService: RegolaOrariaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.regolaOrariaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('regolaOrariaListModification');
      this.activeModal.close();
    });
  }
}
