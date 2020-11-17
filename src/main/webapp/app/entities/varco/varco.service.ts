import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVarco } from 'app/shared/model/varco.model';

type EntityResponseType = HttpResponse<IVarco>;
type EntityArrayResponseType = HttpResponse<IVarco[]>;

@Injectable({ providedIn: 'root' })
export class VarcoService {
  public resourceUrl = SERVER_API_URL + 'api/varcos';

  constructor(protected http: HttpClient) {}

  create(varco: IVarco): Observable<EntityResponseType> {
    return this.http.post<IVarco>(this.resourceUrl, varco, { observe: 'response' });
  }

  update(varco: IVarco): Observable<EntityResponseType> {
    return this.http.put<IVarco>(this.resourceUrl, varco, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVarco>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVarco[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
