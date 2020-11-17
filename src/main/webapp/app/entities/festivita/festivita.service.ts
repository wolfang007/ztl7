import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFestivita } from 'app/shared/model/festivita.model';

type EntityResponseType = HttpResponse<IFestivita>;
type EntityArrayResponseType = HttpResponse<IFestivita[]>;

@Injectable({ providedIn: 'root' })
export class FestivitaService {
  public resourceUrl = SERVER_API_URL + 'api/festivitas';

  constructor(protected http: HttpClient) {}

  create(festivita: IFestivita): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(festivita);
    return this.http
      .post<IFestivita>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(festivita: IFestivita): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(festivita);
    return this.http
      .put<IFestivita>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFestivita>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFestivita[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(festivita: IFestivita): IFestivita {
    const copy: IFestivita = Object.assign({}, festivita, {
      nome: festivita.nome && festivita.nome.isValid() ? festivita.nome.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.nome = res.body.nome ? moment(res.body.nome) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((festivita: IFestivita) => {
        festivita.nome = festivita.nome ? moment(festivita.nome) : undefined;
      });
    }
    return res;
  }
}
