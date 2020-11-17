import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Myztl7TestModule } from '../../../test.module';
import { TipologiaPermessoUpdateComponent } from 'app/entities/tipologia-permesso/tipologia-permesso-update.component';
import { TipologiaPermessoService } from 'app/entities/tipologia-permesso/tipologia-permesso.service';
import { TipologiaPermesso } from 'app/shared/model/tipologia-permesso.model';

describe('Component Tests', () => {
  describe('TipologiaPermesso Management Update Component', () => {
    let comp: TipologiaPermessoUpdateComponent;
    let fixture: ComponentFixture<TipologiaPermessoUpdateComponent>;
    let service: TipologiaPermessoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Myztl7TestModule],
        declarations: [TipologiaPermessoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TipologiaPermessoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipologiaPermessoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipologiaPermessoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipologiaPermesso(123);
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
        const entity = new TipologiaPermesso();
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
