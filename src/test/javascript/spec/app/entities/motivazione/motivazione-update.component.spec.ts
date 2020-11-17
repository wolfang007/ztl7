import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Myztl7TestModule } from '../../../test.module';
import { MotivazioneUpdateComponent } from 'app/entities/motivazione/motivazione-update.component';
import { MotivazioneService } from 'app/entities/motivazione/motivazione.service';
import { Motivazione } from 'app/shared/model/motivazione.model';

describe('Component Tests', () => {
  describe('Motivazione Management Update Component', () => {
    let comp: MotivazioneUpdateComponent;
    let fixture: ComponentFixture<MotivazioneUpdateComponent>;
    let service: MotivazioneService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Myztl7TestModule],
        declarations: [MotivazioneUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MotivazioneUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MotivazioneUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MotivazioneService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Motivazione(123);
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
        const entity = new Motivazione();
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
