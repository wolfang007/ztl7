import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Myztl7TestModule } from '../../../test.module';
import { GruppoVarchiUpdateComponent } from 'app/entities/gruppo-varchi/gruppo-varchi-update.component';
import { GruppoVarchiService } from 'app/entities/gruppo-varchi/gruppo-varchi.service';
import { GruppoVarchi } from 'app/shared/model/gruppo-varchi.model';

describe('Component Tests', () => {
  describe('GruppoVarchi Management Update Component', () => {
    let comp: GruppoVarchiUpdateComponent;
    let fixture: ComponentFixture<GruppoVarchiUpdateComponent>;
    let service: GruppoVarchiService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Myztl7TestModule],
        declarations: [GruppoVarchiUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GruppoVarchiUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GruppoVarchiUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GruppoVarchiService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GruppoVarchi(123);
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
        const entity = new GruppoVarchi();
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
