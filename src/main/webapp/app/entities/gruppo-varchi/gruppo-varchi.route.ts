import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGruppoVarchi, GruppoVarchi } from 'app/shared/model/gruppo-varchi.model';
import { GruppoVarchiService } from './gruppo-varchi.service';
import { GruppoVarchiComponent } from './gruppo-varchi.component';
import { GruppoVarchiDetailComponent } from './gruppo-varchi-detail.component';
import { GruppoVarchiUpdateComponent } from './gruppo-varchi-update.component';

@Injectable({ providedIn: 'root' })
export class GruppoVarchiResolve implements Resolve<IGruppoVarchi> {
  constructor(private service: GruppoVarchiService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGruppoVarchi> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((gruppoVarchi: HttpResponse<GruppoVarchi>) => {
          if (gruppoVarchi.body) {
            return of(gruppoVarchi.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GruppoVarchi());
  }
}

export const gruppoVarchiRoute: Routes = [
  {
    path: '',
    component: GruppoVarchiComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'GruppoVarchis',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GruppoVarchiDetailComponent,
    resolve: {
      gruppoVarchi: GruppoVarchiResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GruppoVarchis',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GruppoVarchiUpdateComponent,
    resolve: {
      gruppoVarchi: GruppoVarchiResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GruppoVarchis',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GruppoVarchiUpdateComponent,
    resolve: {
      gruppoVarchi: GruppoVarchiResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GruppoVarchis',
    },
    canActivate: [UserRouteAccessService],
  },
];
