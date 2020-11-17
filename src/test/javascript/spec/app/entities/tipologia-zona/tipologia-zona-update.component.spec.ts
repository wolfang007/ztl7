import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Myztl7TestModule } from '../../../test.module';
import { TipologiaZonaUpdateComponent } from 'app/entities/tipologia-zona/tipologia-zona-update.component';
import { TipologiaZonaService } from 'app/entities/tipologia-zona/tipologia-zona.service';
import { TipologiaZona } from 'app/shared/model/tipologia-zona.model';

describe('Component Tests', () => {
  describe('TipologiaZona Management Update Component', () => {
    let comp: TipologiaZonaUpdateComponent;
    let fixture: ComponentFixture<TipologiaZonaUpdateComponent>;
    let service: TipologiaZonaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Myztl7TestModule],
        declarations: [TipologiaZonaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TipologiaZonaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipologiaZonaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipologiaZonaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipologiaZona(123);
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
        const entity = new TipologiaZona();
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
