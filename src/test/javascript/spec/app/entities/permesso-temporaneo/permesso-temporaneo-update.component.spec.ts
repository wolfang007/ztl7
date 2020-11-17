import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Myztl7TestModule } from '../../../test.module';
import { PermessoTemporaneoUpdateComponent } from 'app/entities/permesso-temporaneo/permesso-temporaneo-update.component';
import { PermessoTemporaneoService } from 'app/entities/permesso-temporaneo/permesso-temporaneo.service';
import { PermessoTemporaneo } from 'app/shared/model/permesso-temporaneo.model';

describe('Component Tests', () => {
  describe('PermessoTemporaneo Management Update Component', () => {
    let comp: PermessoTemporaneoUpdateComponent;
    let fixture: ComponentFixture<PermessoTemporaneoUpdateComponent>;
    let service: PermessoTemporaneoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Myztl7TestModule],
        declarations: [PermessoTemporaneoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PermessoTemporaneoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PermessoTemporaneoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PermessoTemporaneoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PermessoTemporaneo(123);
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
        const entity = new PermessoTemporaneo();
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
