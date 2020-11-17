import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Myztl7TestModule } from '../../../test.module';
import { VarcoDetailComponent } from 'app/entities/varco/varco-detail.component';
import { Varco } from 'app/shared/model/varco.model';

describe('Component Tests', () => {
  describe('Varco Management Detail Component', () => {
    let comp: VarcoDetailComponent;
    let fixture: ComponentFixture<VarcoDetailComponent>;
    const route = ({ data: of({ varco: new Varco(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Myztl7TestModule],
        declarations: [VarcoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(VarcoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VarcoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load varco on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.varco).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
