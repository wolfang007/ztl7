import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Myztl7TestModule } from '../../../test.module';
import { DurataCostoUpdateComponent } from 'app/entities/durata-costo/durata-costo-update.component';
import { DurataCostoService } from 'app/entities/durata-costo/durata-costo.service';
import { DurataCosto } from 'app/shared/model/durata-costo.model';

describe('Component Tests', () => {
  describe('DurataCosto Management Update Component', () => {
    let comp: DurataCostoUpdateComponent;
    let fixture: ComponentFixture<DurataCostoUpdateComponent>;
    let service: DurataCostoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Myztl7TestModule],
        declarations: [DurataCostoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DurataCostoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DurataCostoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DurataCostoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DurataCosto(123);
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
        const entity = new DurataCosto();
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
