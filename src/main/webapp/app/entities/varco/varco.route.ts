import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IVarco, Varco } from 'app/shared/model/varco.model';
import { VarcoService } from './varco.service';
import { VarcoComponent } from './varco.component';
import { VarcoDetailComponent } from './varco-detail.component';
import { VarcoUpdateComponent } from './varco-update.component';

@Injectable({ providedIn: 'root' })
export class VarcoResolve implements Resolve<IVarco> {
  constructor(private service: VarcoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVarco> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((varco: HttpResponse<Varco>) => {
          if (varco.body) {
            return of(varco.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Varco());
  }
}

export const varcoRoute: Routes = [
  {
    path: '',
    component: VarcoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Varcos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VarcoDetailComponent,
    resolve: {
      varco: VarcoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Varcos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VarcoUpdateComponent,
    resolve: {
      varco: VarcoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Varcos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VarcoUpdateComponent,
    resolve: {
      varco: VarcoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Varcos',
    },
    canActivate: [UserRouteAccessService],
  },
];
