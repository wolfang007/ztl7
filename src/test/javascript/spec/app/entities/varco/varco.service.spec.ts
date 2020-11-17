import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { VarcoService } from 'app/entities/varco/varco.service';
import { IVarco, Varco } from 'app/shared/model/varco.model';
import { EntityStatus } from 'app/shared/model/enumerations/entity-status.model';

describe('Service Tests', () => {
  describe('Varco Service', () => {
    let injector: TestBed;
    let service: VarcoService;
    let httpMock: HttpTestingController;
    let elemDefault: IVarco;
    let expectedResult: IVarco | IVarco[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(VarcoService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Varco(0, 'AAAAAAA', 'AAAAAAA', EntityStatus.ATTIVO);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Varco', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Varco()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Varco', () => {
        const returnedFromService = Object.assign(
          {
            codice: 'BBBBBB',
            descrizionePosizione: 'BBBBBB',
            stato: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Varco', () => {
        const returnedFromService = Object.assign(
          {
            codice: 'BBBBBB',
            descrizionePosizione: 'BBBBBB',
            stato: 'BBBBBB',
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

      it('should delete a Varco', () => {
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
