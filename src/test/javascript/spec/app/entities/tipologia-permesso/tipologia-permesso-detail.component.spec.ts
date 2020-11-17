import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Myztl7TestModule } from '../../../test.module';
import { TipologiaPermessoDetailComponent } from 'app/entities/tipologia-permesso/tipologia-permesso-detail.component';
import { TipologiaPermesso } from 'app/shared/model/tipologia-permesso.model';

describe('Component Tests', () => {
  describe('TipologiaPermesso Management Detail Component', () => {
    let comp: TipologiaPermessoDetailComponent;
    let fixture: ComponentFixture<TipologiaPermessoDetailComponent>;
    const route = ({ data: of({ tipologiaPermesso: new TipologiaPermesso(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Myztl7TestModule],
        declarations: [TipologiaPermessoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TipologiaPermessoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipologiaPermessoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tipologiaPermesso on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipologiaPermesso).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
