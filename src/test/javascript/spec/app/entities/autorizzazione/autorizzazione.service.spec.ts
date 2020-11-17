import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AutorizzazioneService } from 'app/entities/autorizzazione/autorizzazione.service';
import { IAutorizzazione, Autorizzazione } from 'app/shared/model/autorizzazione.model';
import { EntityStatus } from 'app/shared/model/enumerations/entity-status.model';

describe('Service Tests', () => {
  describe('Autorizzazione Service', () => {
    let injector: TestBed;
    let service: AutorizzazioneService;
    let httpMock: HttpTestingController;
    let elemDefault: IAutorizzazione;
    let expectedResult: IAutorizzazione | IAutorizzazione[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AutorizzazioneService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Autorizzazione(0, 'AAAAAAA', 'AAAAAAA', EntityStatus.ATTIVO);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Autorizzazione', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Autorizzazione()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Autorizzazione', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            descrizione: 'BBBBBB',
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

      it('should return a list of Autorizzazione', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            descrizione: 'BBBBBB',
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

      it('should delete a Autorizzazione', () => {
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
