import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Myztl7TestModule } from '../../../test.module';
import { AutorizzazioneDetailComponent } from 'app/entities/autorizzazione/autorizzazione-detail.component';
import { Autorizzazione } from 'app/shared/model/autorizzazione.model';

describe('Component Tests', () => {
  describe('Autorizzazione Management Detail Component', () => {
    let comp: AutorizzazioneDetailComponent;
    let fixture: ComponentFixture<AutorizzazioneDetailComponent>;
    const route = ({ data: of({ autorizzazione: new Autorizzazione(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Myztl7TestModule],
        declarations: [AutorizzazioneDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AutorizzazioneDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AutorizzazioneDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load autorizzazione on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.autorizzazione).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
