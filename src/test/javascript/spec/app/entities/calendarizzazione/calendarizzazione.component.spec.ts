import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { Myztl7TestModule } from '../../../test.module';
import { CalendarizzazioneComponent } from 'app/entities/calendarizzazione/calendarizzazione.component';
import { CalendarizzazioneService } from 'app/entities/calendarizzazione/calendarizzazione.service';
import { Calendarizzazione } from 'app/shared/model/calendarizzazione.model';

describe('Component Tests', () => {
  describe('Calendarizzazione Management Component', () => {
    let comp: CalendarizzazioneComponent;
    let fixture: ComponentFixture<CalendarizzazioneComponent>;
    let service: CalendarizzazioneService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Myztl7TestModule],
        declarations: [CalendarizzazioneComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(CalendarizzazioneComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CalendarizzazioneComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CalendarizzazioneService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Calendarizzazione(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.calendarizzaziones && comp.calendarizzaziones[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Calendarizzazione(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.calendarizzaziones && comp.calendarizzaziones[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
