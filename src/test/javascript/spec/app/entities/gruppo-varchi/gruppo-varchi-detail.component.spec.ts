import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Myztl7TestModule } from '../../../test.module';
import { GruppoVarchiDetailComponent } from 'app/entities/gruppo-varchi/gruppo-varchi-detail.component';
import { GruppoVarchi } from 'app/shared/model/gruppo-varchi.model';

describe('Component Tests', () => {
  describe('GruppoVarchi Management Detail Component', () => {
    let comp: GruppoVarchiDetailComponent;
    let fixture: ComponentFixture<GruppoVarchiDetailComponent>;
    const route = ({ data: of({ gruppoVarchi: new GruppoVarchi(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Myztl7TestModule],
        declarations: [GruppoVarchiDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GruppoVarchiDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GruppoVarchiDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load gruppoVarchi on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.gruppoVarchi).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
