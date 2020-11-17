import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Myztl7TestModule } from '../../../test.module';
import { ProfiloOrarioDetailComponent } from 'app/entities/profilo-orario/profilo-orario-detail.component';
import { ProfiloOrario } from 'app/shared/model/profilo-orario.model';

describe('Component Tests', () => {
  describe('ProfiloOrario Management Detail Component', () => {
    let comp: ProfiloOrarioDetailComponent;
    let fixture: ComponentFixture<ProfiloOrarioDetailComponent>;
    const route = ({ data: of({ profiloOrario: new ProfiloOrario(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Myztl7TestModule],
        declarations: [ProfiloOrarioDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ProfiloOrarioDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProfiloOrarioDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load profiloOrario on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.profiloOrario).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
