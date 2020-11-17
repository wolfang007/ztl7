import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Myztl7TestModule } from '../../../test.module';
import { FestivitaUpdateComponent } from 'app/entities/festivita/festivita-update.component';
import { FestivitaService } from 'app/entities/festivita/festivita.service';
import { Festivita } from 'app/shared/model/festivita.model';

describe('Component Tests', () => {
  describe('Festivita Management Update Component', () => {
    let comp: FestivitaUpdateComponent;
    let fixture: ComponentFixture<FestivitaUpdateComponent>;
    let service: FestivitaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Myztl7TestModule],
        declarations: [FestivitaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FestivitaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FestivitaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FestivitaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Festivita(123);
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
        const entity = new Festivita();
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
