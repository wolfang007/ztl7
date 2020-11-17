import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDurataCosto } from 'app/shared/model/durata-costo.model';

type EntityResponseType = HttpResponse<IDurataCosto>;
type EntityArrayResponseType = HttpResponse<IDurataCosto[]>;

@Injectable({ providedIn: 'root' })
export class DurataCostoService {
  public resourceUrl = SERVER_API_URL + 'api/durata-costos';

  constructor(protected http: HttpClient) {}

  create(durataCosto: IDurataCosto): Observable<EntityResponseType> {
    return this.http.post<IDurataCosto>(this.resourceUrl, durataCosto, { observe: 'response' });
  }

  update(durataCosto: IDurataCosto): Observable<EntityResponseType> {
    return this.http.put<IDurataCosto>(this.resourceUrl, durataCosto, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDurataCosto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDurataCosto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
