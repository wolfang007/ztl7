import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITestX } from 'app/shared/model/test-x.model';
import { TestXService } from './test-x.service';

@Component({
  templateUrl: './test-x-delete-dialog.component.html',
})
export class TestXDeleteDialogComponent {
  testX?: ITestX;

  constructor(protected testXService: TestXService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.testXService.delete(id).subscribe(() => {
      this.eventManager.broadcast('testXListModification');
      this.activeModal.close();
    });
  }
}
