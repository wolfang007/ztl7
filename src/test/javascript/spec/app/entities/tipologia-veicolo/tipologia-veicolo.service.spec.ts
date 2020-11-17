import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TipologiaVeicoloService } from 'app/entities/tipologia-veicolo/tipologia-veicolo.service';
import { ITipologiaVeicolo, TipologiaVeicolo } from 'app/shared/model/tipologia-veicolo.model';
import { EntityStatus } from 'app/shared/model/enumerations/entity-status.model';

describe('Service Tests', () => {
  describe('TipologiaVeicolo Service', () => {
    let injector: TestBed;
    let service: TipologiaVeicoloService;
    let httpMock: HttpTestingController;
    let elemDefault: ITipologiaVeicolo;
    let expectedResult: ITipologiaVeicolo | ITipologiaVeicolo[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TipologiaVeicoloService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new TipologiaVeicolo(0, 'AAAAAAA', EntityStatus.ATTIVO);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TipologiaVeicolo', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new TipologiaVeicolo()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TipologiaVeicolo', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
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

      it('should return a list of TipologiaVeicolo', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
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

      it('should delete a TipologiaVeicolo', () => {
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
