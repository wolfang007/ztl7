import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Myztl7TestModule } from '../../../test.module';
import { AutorizzazioneUpdateComponent } from 'app/entities/autorizzazione/autorizzazione-update.component';
import { AutorizzazioneService } from 'app/entities/autorizzazione/autorizzazione.service';
import { Autorizzazione } from 'app/shared/model/autorizzazione.model';

describe('Component Tests', () => {
  describe('Autorizzazione Management Update Component', () => {
    let comp: AutorizzazioneUpdateComponent;
    let fixture: ComponentFixture<AutorizzazioneUpdateComponent>;
    let service: AutorizzazioneService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Myztl7TestModule],
        declarations: [AutorizzazioneUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AutorizzazioneUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AutorizzazioneUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AutorizzazioneService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Autorizzazione(123);
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
        const entity = new Autorizzazione();
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
