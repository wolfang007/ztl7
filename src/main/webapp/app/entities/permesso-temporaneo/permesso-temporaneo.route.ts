import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPermessoTemporaneo, PermessoTemporaneo } from 'app/shared/model/permesso-temporaneo.model';
import { PermessoTemporaneoService } from './permesso-temporaneo.service';
import { PermessoTemporaneoComponent } from './permesso-temporaneo.component';
import { PermessoTemporaneoDetailComponent } from './permesso-temporaneo-detail.component';
import { PermessoTemporaneoUpdateComponent } from './permesso-temporaneo-update.component';

@Injectable({ providedIn: 'root' })
export class PermessoTemporaneoResolve implements Resolve<IPermessoTemporaneo> {
  constructor(private service: PermessoTemporaneoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPermessoTemporaneo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((permessoTemporaneo: HttpResponse<PermessoTemporaneo>) => {
          if (permessoTemporaneo.body) {
            return of(permessoTemporaneo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PermessoTemporaneo());
  }
}

export const permessoTemporaneoRoute: Routes = [
  {
    path: '',
    component: PermessoTemporaneoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'PermessoTemporaneos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PermessoTemporaneoDetailComponent,
    resolve: {
      permessoTemporaneo: PermessoTemporaneoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PermessoTemporaneos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PermessoTemporaneoUpdateComponent,
    resolve: {
      permessoTemporaneo: PermessoTemporaneoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PermessoTemporaneos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PermessoTemporaneoUpdateComponent,
    resolve: {
      permessoTemporaneo: PermessoTemporaneoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PermessoTemporaneos',
    },
    canActivate: [UserRouteAccessService],
  },
];
