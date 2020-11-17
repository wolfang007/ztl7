import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAutorizzazione } from 'app/shared/model/autorizzazione.model';

type EntityResponseType = HttpResponse<IAutorizzazione>;
type EntityArrayResponseType = HttpResponse<IAutorizzazione[]>;

@Injectable({ providedIn: 'root' })
export class AutorizzazioneService {
  public resourceUrl = SERVER_API_URL + 'api/autorizzaziones';

  constructor(protected http: HttpClient) {}

  create(autorizzazione: IAutorizzazione): Observable<EntityResponseType> {
    return this.http.post<IAutorizzazione>(this.resourceUrl, autorizzazione, { observe: 'response' });
  }

  update(autorizzazione: IAutorizzazione): Observable<EntityResponseType> {
    return this.http.put<IAutorizzazione>(this.resourceUrl, autorizzazione, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAutorizzazione>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAutorizzazione[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
