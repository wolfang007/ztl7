import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IZona } from 'app/shared/model/zona.model';

type EntityResponseType = HttpResponse<IZona>;
type EntityArrayResponseType = HttpResponse<IZona[]>;

@Injectable({ providedIn: 'root' })
export class ZonaService {
  public resourceUrl = SERVER_API_URL + 'api/zonas';

  constructor(protected http: HttpClient) {}

  create(zona: IZona): Observable<EntityResponseType> {
    return this.http.post<IZona>(this.resourceUrl, zona, { observe: 'response' });
  }

  update(zona: IZona): Observable<EntityResponseType> {
    return this.http.put<IZona>(this.resourceUrl, zona, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IZona>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IZona[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
