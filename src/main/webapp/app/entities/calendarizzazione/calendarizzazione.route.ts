import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICalendarizzazione, Calendarizzazione } from 'app/shared/model/calendarizzazione.model';
import { CalendarizzazioneService } from './calendarizzazione.service';
import { CalendarizzazioneComponent } from './calendarizzazione.component';
import { CalendarizzazioneDetailComponent } from './calendarizzazione-detail.component';
import { CalendarizzazioneUpdateComponent } from './calendarizzazione-update.component';

@Injectable({ providedIn: 'root' })
export class CalendarizzazioneResolve implements Resolve<ICalendarizzazione> {
  constructor(private service: CalendarizzazioneService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICalendarizzazione> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((calendarizzazione: HttpResponse<Calendarizzazione>) => {
          if (calendarizzazione.body) {
            return of(calendarizzazione.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Calendarizzazione());
  }
}

export const calendarizzazioneRoute: Routes = [
  {
    path: '',
    component: CalendarizzazioneComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Calendarizzaziones',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CalendarizzazioneDetailComponent,
    resolve: {
      calendarizzazione: CalendarizzazioneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Calendarizzaziones',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CalendarizzazioneUpdateComponent,
    resolve: {
      calendarizzazione: CalendarizzazioneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Calendarizzaziones',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CalendarizzazioneUpdateComponent,
    resolve: {
      calendarizzazione: CalendarizzazioneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Calendarizzaziones',
    },
    canActivate: [UserRouteAccessService],
  },
];
