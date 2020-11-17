import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Myztl7TestModule } from '../../../test.module';
import { TestXUpdateComponent } from 'app/entities/test-x/test-x-update.component';
import { TestXService } from 'app/entities/test-x/test-x.service';
import { TestX } from 'app/shared/model/test-x.model';

describe('Component Tests', () => {
  describe('TestX Management Update Component', () => {
    let comp: TestXUpdateComponent;
    let fixture: ComponentFixture<TestXUpdateComponent>;
    let service: TestXService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Myztl7TestModule],
        declarations: [TestXUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TestXUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TestXUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TestXService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TestX(123);
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
        const entity = new TestX();
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
