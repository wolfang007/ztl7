import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Myztl7TestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { TipologiaVeicoloDeleteDialogComponent } from 'app/entities/tipologia-veicolo/tipologia-veicolo-delete-dialog.component';
import { TipologiaVeicoloService } from 'app/entities/tipologia-veicolo/tipologia-veicolo.service';

describe('Component Tests', () => {
  describe('TipologiaVeicolo Management Delete Component', () => {
    let comp: TipologiaVeicoloDeleteDialogComponent;
    let fixture: ComponentFixture<TipologiaVeicoloDeleteDialogComponent>;
    let service: TipologiaVeicoloService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Myztl7TestModule],
        declarations: [TipologiaVeicoloDeleteDialogComponent],
      })
        .overrideTemplate(TipologiaVeicoloDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipologiaVeicoloDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipologiaVeicoloService);
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
