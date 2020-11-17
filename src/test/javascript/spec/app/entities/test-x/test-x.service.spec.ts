import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TestXService } from 'app/entities/test-x/test-x.service';
import { ITestX, TestX } from 'app/shared/model/test-x.model';

describe('Service Tests', () => {
  describe('TestX Service', () => {
    let injector: TestBed;
    let service: TestXService;
    let httpMock: HttpTestingController;
    let elemDefault: ITestX;
    let expectedResult: ITestX | ITestX[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TestXService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new TestX(
        0,
        'AAAAAAA',
        0,
        0,
        0,
        0,
        0,
        false,
        currentDate,
        currentDate,
        currentDate,
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            xLocalDate: currentDate.format(DATE_FORMAT),
            xZonedDateTime: currentDate.format(DATE_TIME_FORMAT),
            xInstant: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TestX', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            xLocalDate: currentDate.format(DATE_FORMAT),
            xZonedDateTime: currentDate.format(DATE_TIME_FORMAT),
            xInstant: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            xLocalDate: currentDate,
            xZonedDateTime: currentDate,
            xInstant: currentDate,
          },
          returnedFromService
        );

        service.create(new TestX()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TestX', () => {
        const returnedFromService = Object.assign(
          {
            xString: 'BBBBBB',
            xInteger: 1,
            xLong: 1,
            xBigDecimal: 1,
            xFloat: 1,
            xDouble: 1,
            xBoolean: true,
            xLocalDate: currentDate.format(DATE_FORMAT),
            xZonedDateTime: currentDate.format(DATE_TIME_FORMAT),
            xInstant: currentDate.format(DATE_TIME_FORMAT),
            xUUID: 'BBBBBB',
            xBlob: 'BBBBBB',
            xAnyBlob: 'BBBBBB',
            xImageBlob: 'BBBBBB',
            xTextBlob: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            xLocalDate: currentDate,
            xZonedDateTime: currentDate,
            xInstant: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TestX', () => {
        const returnedFromService = Object.assign(
          {
            xString: 'BBBBBB',
            xInteger: 1,
            xLong: 1,
            xBigDecimal: 1,
            xFloat: 1,
            xDouble: 1,
            xBoolean: true,
            xLocalDate: currentDate.format(DATE_FORMAT),
            xZonedDateTime: currentDate.format(DATE_TIME_FORMAT),
            xInstant: currentDate.format(DATE_TIME_FORMAT),
            xUUID: 'BBBBBB',
            xBlob: 'BBBBBB',
            xAnyBlob: 'BBBBBB',
            xImageBlob: 'BBBBBB',
            xTextBlob: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            xLocalDate: currentDate,
            xZonedDateTime: currentDate,
            xInstant: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TestX', () => {
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
