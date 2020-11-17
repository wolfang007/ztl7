import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Myztl7TestModule } from '../../../test.module';
import { CalendarizzazioneUpdateComponent } from 'app/entities/calendarizzazione/calendarizzazione-update.component';
import { CalendarizzazioneService } from 'app/entities/calendarizzazione/calendarizzazione.service';
import { Calendarizzazione } from 'app/shared/model/calendarizzazione.model';

describe('Component Tests', () => {
  describe('Calendarizzazione Management Update Component', () => {
    let comp: CalendarizzazioneUpdateComponent;
    let fixture: ComponentFixture<CalendarizzazioneUpdateComponent>;
    let service: CalendarizzazioneService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Myztl7TestModule],
        declarations: [CalendarizzazioneUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CalendarizzazioneUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CalendarizzazioneUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CalendarizzazioneService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Calendarizzazione(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Calendarizzazione();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
