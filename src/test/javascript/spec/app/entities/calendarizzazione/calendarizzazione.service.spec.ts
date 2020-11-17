import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CalendarizzazioneService } from 'app/entities/calendarizzazione/calendarizzazione.service';
import { ICalendarizzazione, Calendarizzazione } from 'app/shared/model/calendarizzazione.model';

describe('Service Tests', () => {
  describe('Calendarizzazione Service', () => {
    let injector: TestBed;
    let service: CalendarizzazioneService;
    let httpMock: HttpTestingController;
    let elemDefault: ICalendarizzazione;
    let expectedResult: ICalendarizzazione | ICalendarizzazione[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CalendarizzazioneService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Calendarizzazione(0, false, false, false, false, false, false, false, false, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Calendarizzazione', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Calendarizzazione()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Calendarizzazione', () => {
        const returnedFromService = Object.assign(
          {
            lunedi: true,
            martedi: true,
            mercoledi: true,
            giovedi: true,
            venerdi: true,
            sabato: true,
            domenica: true,
            siRipete: true,
            festivi: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Calendarizzazione', () => {
        const returnedFromService = Object.assign(
          {
            lunedi: true,
            martedi: true,
            mercoledi: true,
            giovedi: true,
            venerdi: true,
            sabato: true,
            domenica: true,
            siRipete: true,
            festivi: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Calendarizzazione', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
