import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Myztl7TestModule } from '../../../test.module';
import { VarcoUpdateComponent } from 'app/entities/varco/varco-update.component';
import { VarcoService } from 'app/entities/varco/varco.service';
import { Varco } from 'app/shared/model/varco.model';

describe('Component Tests', () => {
  describe('Varco Management Update Component', () => {
    let comp: VarcoUpdateComponent;
    let fixture: ComponentFixture<VarcoUpdateComponent>;
    let service: VarcoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Myztl7TestModule],
        declarations: [VarcoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(VarcoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VarcoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VarcoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Varco(123);
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
        const entity = new Varco();
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
