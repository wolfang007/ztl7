import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Myztl7TestModule } from '../../../test.module';
import { CalendarizzazioneDetailComponent } from 'app/entities/calendarizzazione/calendarizzazione-detail.component';
import { Calendarizzazione } from 'app/shared/model/calendarizzazione.model';

describe('Component Tests', () => {
  describe('Calendarizzazione Management Detail Component', () => {
    let comp: CalendarizzazioneDetailComponent;
    let fixture: ComponentFixture<CalendarizzazioneDetailComponent>;
    const route = ({ data: of({ calendarizzazione: new Calendarizzazione(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Myztl7TestModule],
        declarations: [CalendarizzazioneDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CalendarizzazioneDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CalendarizzazioneDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load calendarizzazione on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.calendarizzazione).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
