import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITestX } from 'app/shared/model/test-x.model';

type EntityResponseType = HttpResponse<ITestX>;
type EntityArrayResponseType = HttpResponse<ITestX[]>;

@Injectable({ providedIn: 'root' })
export class TestXService {
  public resourceUrl = SERVER_API_URL + 'api/test-xes';

  constructor(protected http: HttpClient) {}

  create(testX: ITestX): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(testX);
    return this.http
      .post<ITestX>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(testX: ITestX): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(testX);
    return this.http
      .put<ITestX>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITestX>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITestX[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(testX: ITestX): ITestX {
    const copy: ITestX = Object.assign({}, testX, {
      xLocalDate: testX.xLocalDate && testX.xLocalDate.isValid() ? testX.xLocalDate.format(DATE_FORMAT) : undefined,
      xZonedDateTime: testX.xZonedDateTime && testX.xZonedDateTime.isValid() ? testX.xZonedDateTime.toJSON() : undefined,
      xInstant: testX.xInstant && testX.xInstant.isValid() ? testX.xInstant.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.xLocalDate = res.body.xLocalDate ? moment(res.body.xLocalDate) : undefined;
      res.body.xZonedDateTime = res.body.xZonedDateTime ? moment(res.body.xZonedDateTime) : undefined;
      res.body.xInstant = res.body.xInstant ? moment(res.body.xInstant) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((testX: ITestX) => {
        testX.xLocalDate = testX.xLocalDate ? moment(testX.xLocalDate) : undefined;
        testX.xZonedDateTime = testX.xZonedDateTime ? moment(testX.xZonedDateTime) : undefined;
        testX.xInstant = testX.xInstant ? moment(testX.xInstant) : undefined;
      });
    }
    return res;
  }
}
