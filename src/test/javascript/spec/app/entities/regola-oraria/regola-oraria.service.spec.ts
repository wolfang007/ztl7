import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RegolaOrariaService } from 'app/entities/regola-oraria/regola-oraria.service';
import { IRegolaOraria, RegolaOraria } from 'app/shared/model/regola-oraria.model';
import { OreEnum } from 'app/shared/model/enumerations/ore-enum.model';
import { MinutiEnum } from 'app/shared/model/enumerations/minuti-enum.model';
import { EntityStatus } from 'app/shared/model/enumerations/entity-status.model';

describe('Service Tests', () => {
  describe('RegolaOraria Service', () => {
    let injector: TestBed;
    let service: RegolaOrariaService;
    let httpMock: HttpTestingController;
    let elemDefault: IRegolaOraria;
    let expectedResult: IRegolaOraria | IRegolaOraria[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(RegolaOrariaService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new RegolaOraria(
        0,
        'AAAAAAA',
        OreEnum.ZERO,
        OreEnum.ZERO,
        MinutiEnum.QUINDICI,
        MinutiEnum.QUINDICI,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        EntityStatus.ATTIVO
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a RegolaOraria', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new RegolaOraria()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a RegolaOraria', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            oraInizio: 'BBBBBB',
            oraFine: 'BBBBBB',
            minutiInizio: 'BBBBBB',
            minutiFine: 'BBBBBB',
            lunedi: true,
            martedi: true,
            mercoledi: true,
            giovedi: true,
            venerdi: true,
            sabato: true,
            domenica: true,
            festivi: true,
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

      it('should return a list of RegolaOraria', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            oraInizio: 'BBBBBB',
            oraFine: 'BBBBBB',
            minutiInizio: 'BBBBBB',
            minutiFine: 'BBBBBB',
            lunedi: true,
            martedi: true,
            mercoledi: true,
            giovedi: true,
            venerdi: true,
            sabato: true,
            domenica: true,
            festivi: true,
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

      it('should delete a RegolaOraria', () => {
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
