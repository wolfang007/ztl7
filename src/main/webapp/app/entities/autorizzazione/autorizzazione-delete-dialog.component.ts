import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAutorizzazione } from 'app/shared/model/autorizzazione.model';
import { AutorizzazioneService } from './autorizzazione.service';

@Component({
  templateUrl: './autorizzazione-delete-dialog.component.html',
})
export class AutorizzazioneDeleteDialogComponent {
  autorizzazione?: IAutorizzazione;

  constructor(
    protected autorizzazioneService: AutorizzazioneService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.autorizzazioneService.delete(id).subscribe(() => {
      this.eventManager.broadcast('autorizzazioneListModification');
      this.activeModal.close();
    });
  }
}
