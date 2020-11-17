import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITipologiaZona, TipologiaZona } from 'app/shared/model/tipologia-zona.model';
import { TipologiaZonaService } from './tipologia-zona.service';
import { TipologiaZonaComponent } from './tipologia-zona.component';
import { TipologiaZonaDetailComponent } from './tipologia-zona-detail.component';
import { TipologiaZonaUpdateComponent } from './tipologia-zona-update.component';

@Injectable({ providedIn: 'root' })
export class TipologiaZonaResolve implements Resolve<ITipologiaZona> {
  constructor(private service: TipologiaZonaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipologiaZona> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tipologiaZona: HttpResponse<TipologiaZona>) => {
          if (tipologiaZona.body) {
            return of(tipologiaZona.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipologiaZona());
  }
}

export const tipologiaZonaRoute: Routes = [
  {
    path: '',
    component: TipologiaZonaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'TipologiaZonas',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TipologiaZonaDetailComponent,
    resolve: {
      tipologiaZona: TipologiaZonaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'TipologiaZonas',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TipologiaZonaUpdateComponent,
    resolve: {
      tipologiaZona: TipologiaZonaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'TipologiaZonas',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TipologiaZonaUpdateComponent,
    resolve: {
      tipologiaZona: TipologiaZonaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'TipologiaZonas',
    },
    canActivate: [UserRouteAccessService],
  },
];
