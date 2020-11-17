import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ProfiloOrarioService } from 'app/entities/profilo-orario/profilo-orario.service';
import { IProfiloOrario, ProfiloOrario } from 'app/shared/model/profilo-orario.model';
import { EntityStatus } from 'app/shared/model/enumerations/entity-status.model';

describe('Service Tests', () => {
  describe('ProfiloOrario Service', () => {
    let injector: TestBed;
    let service: ProfiloOrarioService;
    let httpMock: HttpTestingController;
    let elemDefault: IProfiloOrario;
    let expectedResult: IProfiloOrario | IProfiloOrario[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ProfiloOrarioService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ProfiloOrario(0, 'AAAAAAA', EntityStatus.ATTIVO);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ProfiloOrario', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ProfiloOrario()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ProfiloOrario', () => {
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

      it('should return a list of ProfiloOrario', () => {
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

      it('should delete a ProfiloOrario', () => {
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
