import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMotivazione } from 'app/shared/model/motivazione.model';

type EntityResponseType = HttpResponse<IMotivazione>;
type EntityArrayResponseType = HttpResponse<IMotivazione[]>;

@Injectable({ providedIn: 'root' })
export class MotivazioneService {
  public resourceUrl = SERVER_API_URL + 'api/motivaziones';

  constructor(protected http: HttpClient) {}

  create(motivazione: IMotivazione): Observable<EntityResponseType> {
    return this.http.post<IMotivazione>(this.resourceUrl, motivazione, { observe: 'response' });
  }

  update(motivazione: IMotivazione): Observable<EntityResponseType> {
    return this.http.put<IMotivazione>(this.resourceUrl, motivazione, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMotivazione>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMotivazione[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
