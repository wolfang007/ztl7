import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Myztl7TestModule } from '../../../test.module';
import { DurataCostoDetailComponent } from 'app/entities/durata-costo/durata-costo-detail.component';
import { DurataCosto } from 'app/shared/model/durata-costo.model';

describe('Component Tests', () => {
  describe('DurataCosto Management Detail Component', () => {
    let comp: DurataCostoDetailComponent;
    let fixture: ComponentFixture<DurataCostoDetailComponent>;
    const route = ({ data: of({ durataCosto: new DurataCosto(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Myztl7TestModule],
        declarations: [DurataCostoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DurataCostoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DurataCostoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load durataCosto on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.durataCosto).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
