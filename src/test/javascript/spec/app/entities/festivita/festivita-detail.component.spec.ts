import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Myztl7TestModule } from '../../../test.module';
import { FestivitaDetailComponent } from 'app/entities/festivita/festivita-detail.component';
import { Festivita } from 'app/shared/model/festivita.model';

describe('Component Tests', () => {
  describe('Festivita Management Detail Component', () => {
    let comp: FestivitaDetailComponent;
    let fixture: ComponentFixture<FestivitaDetailComponent>;
    const route = ({ data: of({ festivita: new Festivita(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Myztl7TestModule],
        declarations: [FestivitaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FestivitaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FestivitaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load festivita on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.festivita).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
