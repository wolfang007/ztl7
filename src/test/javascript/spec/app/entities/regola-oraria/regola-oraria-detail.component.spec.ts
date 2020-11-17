import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Myztl7TestModule } from '../../../test.module';
import { RegolaOrariaDetailComponent } from 'app/entities/regola-oraria/regola-oraria-detail.component';
import { RegolaOraria } from 'app/shared/model/regola-oraria.model';

describe('Component Tests', () => {
  describe('RegolaOraria Management Detail Component', () => {
    let comp: RegolaOrariaDetailComponent;
    let fixture: ComponentFixture<RegolaOrariaDetailComponent>;
    const route = ({ data: of({ regolaOraria: new RegolaOraria(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Myztl7TestModule],
        declarations: [RegolaOrariaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RegolaOrariaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RegolaOrariaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load regolaOraria on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.regolaOraria).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
