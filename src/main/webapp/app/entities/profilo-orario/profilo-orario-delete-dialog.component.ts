import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProfiloOrario } from 'app/shared/model/profilo-orario.model';
import { ProfiloOrarioService } from './profilo-orario.service';

@Component({
  templateUrl: './profilo-orario-delete-dialog.component.html',
})
export class ProfiloOrarioDeleteDialogComponent {
  profiloOrario?: IProfiloOrario;

  constructor(
    protected profiloOrarioService: ProfiloOrarioService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.profiloOrarioService.delete(id).subscribe(() => {
      this.eventManager.broadcast('profiloOrarioListModification');
      this.activeModal.close();
    });
  }
}
