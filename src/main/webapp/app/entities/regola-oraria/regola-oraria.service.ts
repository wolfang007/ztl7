import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRegolaOraria } from 'app/shared/model/regola-oraria.model';

type EntityResponseType = HttpResponse<IRegolaOraria>;
type EntityArrayResponseType = HttpResponse<IRegolaOraria[]>;

@Injectable({ providedIn: 'root' })
export class RegolaOrariaService {
  public resourceUrl = SERVER_API_URL + 'api/regola-orarias';

  constructor(protected http: HttpClient) {}

  create(regolaOraria: IRegolaOraria): Observable<EntityResponseType> {
    return this.http.post<IRegolaOraria>(this.resourceUrl, regolaOraria, { observe: 'response' });
  }

  update(regolaOraria: IRegolaOraria): Observable<EntityResponseType> {
    return this.http.put<IRegolaOraria>(this.resourceUrl, regolaOraria, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRegolaOraria>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRegolaOraria[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
