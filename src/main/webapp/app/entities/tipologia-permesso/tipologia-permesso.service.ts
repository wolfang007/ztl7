import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITipologiaPermesso } from 'app/shared/model/tipologia-permesso.model';

type EntityResponseType = HttpResponse<ITipologiaPermesso>;
type EntityArrayResponseType = HttpResponse<ITipologiaPermesso[]>;

@Injectable({ providedIn: 'root' })
export class TipologiaPermessoService {
  public resourceUrl = SERVER_API_URL + 'api/tipologia-permessos';

  constructor(protected http: HttpClient) {}

  create(tipologiaPermesso: ITipologiaPermesso): Observable<EntityResponseType> {
    return this.http.post<ITipologiaPermesso>(this.resourceUrl, tipologiaPermesso, { observe: 'response' });
  }

  update(tipologiaPermesso: ITipologiaPermesso): Observable<EntityResponseType> {
    return this.http.put<ITipologiaPermesso>(this.resourceUrl, tipologiaPermesso, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipologiaPermesso>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipologiaPermesso[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
