import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Myztl7TestModule } from '../../../test.module';
import { ZonaUpdateComponent } from 'app/entities/zona/zona-update.component';
import { ZonaService } from 'app/entities/zona/zona.service';
import { Zona } from 'app/shared/model/zona.model';

describe('Component Tests', () => {
  describe('Zona Management Update Component', () => {
    let comp: ZonaUpdateComponent;
    let fixture: ComponentFixture<ZonaUpdateComponent>;
    let service: ZonaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Myztl7TestModule],
        declarations: [ZonaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ZonaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ZonaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ZonaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Zona(123);
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
        const entity = new Zona();
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
