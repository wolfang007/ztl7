import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Myztl7TestModule } from '../../../test.module';
import { ProfiloOrarioUpdateComponent } from 'app/entities/profilo-orario/profilo-orario-update.component';
import { ProfiloOrarioService } from 'app/entities/profilo-orario/profilo-orario.service';
import { ProfiloOrario } from 'app/shared/model/profilo-orario.model';

describe('Component Tests', () => {
  describe('ProfiloOrario Management Update Component', () => {
    let comp: ProfiloOrarioUpdateComponent;
    let fixture: ComponentFixture<ProfiloOrarioUpdateComponent>;
    let service: ProfiloOrarioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Myztl7TestModule],
        declarations: [ProfiloOrarioUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ProfiloOrarioUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProfiloOrarioUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfiloOrarioService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProfiloOrario(123);
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
        const entity = new ProfiloOrario();
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
