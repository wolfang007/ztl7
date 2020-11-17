import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICalendarizzazione } from 'app/shared/model/calendarizzazione.model';

type EntityResponseType = HttpResponse<ICalendarizzazione>;
type EntityArrayResponseType = HttpResponse<ICalendarizzazione[]>;

@Injectable({ providedIn: 'root' })
export class CalendarizzazioneService {
  public resourceUrl = SERVER_API_URL + 'api/calendarizzaziones';

  constructor(protected http: HttpClient) {}

  create(calendarizzazione: ICalendarizzazione): Observable<EntityResponseType> {
    return this.http.post<ICalendarizzazione>(this.resourceUrl, calendarizzazione, { observe: 'response' });
  }

  update(calendarizzazione: ICalendarizzazione): Observable<EntityResponseType> {
    return this.http.put<ICalendarizzazione>(this.resourceUrl, calendarizzazione, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICalendarizzazione>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICalendarizzazione[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
