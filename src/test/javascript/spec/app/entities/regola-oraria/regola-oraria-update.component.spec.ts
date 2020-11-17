import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Myztl7TestModule } from '../../../test.module';
import { RegolaOrariaUpdateComponent } from 'app/entities/regola-oraria/regola-oraria-update.component';
import { RegolaOrariaService } from 'app/entities/regola-oraria/regola-oraria.service';
import { RegolaOraria } from 'app/shared/model/regola-oraria.model';

describe('Component Tests', () => {
  describe('RegolaOraria Management Update Component', () => {
    let comp: RegolaOrariaUpdateComponent;
    let fixture: ComponentFixture<RegolaOrariaUpdateComponent>;
    let service: RegolaOrariaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Myztl7TestModule],
        declarations: [RegolaOrariaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RegolaOrariaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RegolaOrariaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RegolaOrariaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RegolaOraria(123);
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
        const entity = new RegolaOraria();
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
