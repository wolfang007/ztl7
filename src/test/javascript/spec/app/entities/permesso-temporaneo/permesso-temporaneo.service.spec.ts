import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PermessoTemporaneoService } from 'app/entities/permesso-temporaneo/permesso-temporaneo.service';
import { IPermessoTemporaneo, PermessoTemporaneo } from 'app/shared/model/permesso-temporaneo.model';
import { TipoPersona } from 'app/shared/model/enumerations/tipo-persona.model';

describe('Service Tests', () => {
  describe('PermessoTemporaneo Service', () => {
    let injector: TestBed;
    let service: PermessoTemporaneoService;
    let httpMock: HttpTestingController;
    let elemDefault: IPermessoTemporaneo;
    let expectedResult: IPermessoTemporaneo | IPermessoTemporaneo[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PermessoTemporaneoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PermessoTemporaneo(
        0,
        'AAAAAAA',
        'AAAAAAA',
        TipoPersona.FISICA,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        false,
        0,
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        false,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataInizio: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PermessoTemporaneo', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataInizio: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataInizio: currentDate,
          },
          returnedFromService
        );

        service.create(new PermessoTemporaneo()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PermessoTemporaneo', () => {
        const returnedFromService = Object.assign(
          {
            targa: 'BBBBBB',
            domicilioDigitale: 'BBBBBB',
            tipoPersona: 'BBBBBB',
            nomeRichiedente: 'BBBBBB',
            cognomeRichiedente: 'BBBBBB',
            ragioneSociale: 'BBBBBB',
            codiceFiscaleRichiedente: 'BBBBBB',
            partitaIva: 'BBBBBB',
            dataInizio: currentDate.format(DATE_FORMAT),
            impostaDiBollo: true,
            costo: 1,
            copiaFirmata: 'BBBBBB',
            documentoRiconoscimento: 'BBBBBB',
            pagato: true,
            protocollo: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataInizio: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PermessoTemporaneo', () => {
        const returnedFromService = Object.assign(
          {
            targa: 'BBBBBB',
            domicilioDigitale: 'BBBBBB',
            tipoPersona: 'BBBBBB',
            nomeRichiedente: 'BBBBBB',
            cognomeRichiedente: 'BBBBBB',
            ragioneSociale: 'BBBBBB',
            codiceFiscaleRichiedente: 'BBBBBB',
            partitaIva: 'BBBBBB',
            dataInizio: currentDate.format(DATE_FORMAT),
            impostaDiBollo: true,
            costo: 1,
            copiaFirmata: 'BBBBBB',
            documentoRiconoscimento: 'BBBBBB',
            pagato: true,
            protocollo: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataInizio: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a PermessoTemporaneo', () => {
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
