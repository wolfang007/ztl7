import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGruppoVarchi } from 'app/shared/model/gruppo-varchi.model';

type EntityResponseType = HttpResponse<IGruppoVarchi>;
type EntityArrayResponseType = HttpResponse<IGruppoVarchi[]>;

@Injectable({ providedIn: 'root' })
export class GruppoVarchiService {
  public resourceUrl = SERVER_API_URL + 'api/gruppo-varchis';

  constructor(protected http: HttpClient) {}

  create(gruppoVarchi: IGruppoVarchi): Observable<EntityResponseType> {
    return this.http.post<IGruppoVarchi>(this.resourceUrl, gruppoVarchi, { observe: 'response' });
  }

  update(gruppoVarchi: IGruppoVarchi): Observable<EntityResponseType> {
    return this.http.put<IGruppoVarchi>(this.resourceUrl, gruppoVarchi, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGruppoVarchi>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGruppoVarchi[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
