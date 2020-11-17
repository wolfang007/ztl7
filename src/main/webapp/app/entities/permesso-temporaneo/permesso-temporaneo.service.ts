import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPermessoTemporaneo } from 'app/shared/model/permesso-temporaneo.model';

type EntityResponseType = HttpResponse<IPermessoTemporaneo>;
type EntityArrayResponseType = HttpResponse<IPermessoTemporaneo[]>;

@Injectable({ providedIn: 'root' })
export class PermessoTemporaneoService {
  public resourceUrl = SERVER_API_URL + 'api/permesso-temporaneos';

  constructor(protected http: HttpClient) {}

  create(permessoTemporaneo: IPermessoTemporaneo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(permessoTemporaneo);
    return this.http
      .post<IPermessoTemporaneo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(permessoTemporaneo: IPermessoTemporaneo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(permessoTemporaneo);
    return this.http
      .put<IPermessoTemporaneo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPermessoTemporaneo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPermessoTemporaneo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(permessoTemporaneo: IPermessoTemporaneo): IPermessoTemporaneo {
    const copy: IPermessoTemporaneo = Object.assign({}, permessoTemporaneo, {
      dataInizio:
        permessoTemporaneo.dataInizio && permessoTemporaneo.dataInizio.isValid()
          ? permessoTemporaneo.dataInizio.format(DATE_FORMAT)
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataInizio = res.body.dataInizio ? moment(res.body.dataInizio) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((permessoTemporaneo: IPermessoTemporaneo) => {
        permessoTemporaneo.dataInizio = permessoTemporaneo.dataInizio ? moment(permessoTemporaneo.dataInizio) : undefined;
      });
    }
    return res;
  }
}
