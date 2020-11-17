import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITipologiaZona } from 'app/shared/model/tipologia-zona.model';

type EntityResponseType = HttpResponse<ITipologiaZona>;
type EntityArrayResponseType = HttpResponse<ITipologiaZona[]>;

@Injectable({ providedIn: 'root' })
export class TipologiaZonaService {
  public resourceUrl = SERVER_API_URL + 'api/tipologia-zonas';

  constructor(protected http: HttpClient) {}

  create(tipologiaZona: ITipologiaZona): Observable<EntityResponseType> {
    return this.http.post<ITipologiaZona>(this.resourceUrl, tipologiaZona, { observe: 'response' });
  }

  update(tipologiaZona: ITipologiaZona): Observable<EntityResponseType> {
    return this.http.put<ITipologiaZona>(this.resourceUrl, tipologiaZona, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipologiaZona>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipologiaZona[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
