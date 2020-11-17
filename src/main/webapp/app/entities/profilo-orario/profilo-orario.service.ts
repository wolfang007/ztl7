import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProfiloOrario } from 'app/shared/model/profilo-orario.model';

type EntityResponseType = HttpResponse<IProfiloOrario>;
type EntityArrayResponseType = HttpResponse<IProfiloOrario[]>;

@Injectable({ providedIn: 'root' })
export class ProfiloOrarioService {
  public resourceUrl = SERVER_API_URL + 'api/profilo-orarios';

  constructor(protected http: HttpClient) {}

  create(profiloOrario: IProfiloOrario): Observable<EntityResponseType> {
    return this.http.post<IProfiloOrario>(this.resourceUrl, profiloOrario, { observe: 'response' });
  }

  update(profiloOrario: IProfiloOrario): Observable<EntityResponseType> {
    return this.http.put<IProfiloOrario>(this.resourceUrl, profiloOrario, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProfiloOrario>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProfiloOrario[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
