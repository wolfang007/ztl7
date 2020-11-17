import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDurataCosto, DurataCosto } from 'app/shared/model/durata-costo.model';
import { DurataCostoService } from './durata-costo.service';
import { DurataCostoComponent } from './durata-costo.component';
import { DurataCostoDetailComponent } from './durata-costo-detail.component';
import { DurataCostoUpdateComponent } from './durata-costo-update.component';

@Injectable({ providedIn: 'root' })
export class DurataCostoResolve implements Resolve<IDurataCosto> {
  constructor(private service: DurataCostoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDurataCosto> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((durataCosto: HttpResponse<DurataCosto>) => {
          if (durataCosto.body) {
            return of(durataCosto.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DurataCosto());
  }
}

export const durataCostoRoute: Routes = [
  {
    path: '',
    component: DurataCostoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'DurataCostos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DurataCostoDetailComponent,
    resolve: {
      durataCosto: DurataCostoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DurataCostos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DurataCostoUpdateComponent,
    resolve: {
      durataCosto: DurataCostoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DurataCostos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DurataCostoUpdateComponent,
    resolve: {
      durataCosto: DurataCostoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DurataCostos',
    },
    canActivate: [UserRouteAccessService],
  },
];
