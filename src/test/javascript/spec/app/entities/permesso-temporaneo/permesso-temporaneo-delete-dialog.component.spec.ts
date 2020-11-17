import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Myztl7TestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { PermessoTemporaneoDeleteDialogComponent } from 'app/entities/permesso-temporaneo/permesso-temporaneo-delete-dialog.component';
import { PermessoTemporaneoService } from 'app/entities/permesso-temporaneo/permesso-temporaneo.service';

describe('Component Tests', () => {
  describe('PermessoTemporaneo Management Delete Component', () => {
    let comp: PermessoTemporaneoDeleteDialogComponent;
    let fixture: ComponentFixture<PermessoTemporaneoDeleteDialogComponent>;
    let service: PermessoTemporaneoService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Myztl7TestModule],
        declarations: [PermessoTemporaneoDeleteDialogComponent],
      })
        .overrideTemplate(PermessoTemporaneoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PermessoTemporaneoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PermessoTemporaneoService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
