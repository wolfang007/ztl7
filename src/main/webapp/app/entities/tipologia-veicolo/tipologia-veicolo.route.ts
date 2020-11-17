import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITipologiaVeicolo, TipologiaVeicolo } from 'app/shared/model/tipologia-veicolo.model';
import { TipologiaVeicoloService } from './tipologia-veicolo.service';
import { TipologiaVeicoloComponent } from './tipologia-veicolo.component';
import { TipologiaVeicoloDetailComponent } from './tipologia-veicolo-detail.component';
import { TipologiaVeicoloUpdateComponent } from './tipologia-veicolo-update.component';

@Injectable({ providedIn: 'root' })
export class TipologiaVeicoloResolve implements Resolve<ITipologiaVeicolo> {
  constructor(private service: TipologiaVeicoloService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipologiaVeicolo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tipologiaVeicolo: HttpResponse<TipologiaVeicolo>) => {
          if (tipologiaVeicolo.body) {
            return of(tipologiaVeicolo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipologiaVeicolo());
  }
}

export const tipologiaVeicoloRoute: Routes = [
  {
    path: '',
    component: TipologiaVeicoloComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'TipologiaVeicolos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TipologiaVeicoloDetailComponent,
    resolve: {
      tipologiaVeicolo: TipologiaVeicoloResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'TipologiaVeicolos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TipologiaVeicoloUpdateComponent,
    resolve: {
      tipologiaVeicolo: TipologiaVeicoloResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'TipologiaVeicolos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TipologiaVeicoloUpdateComponent,
    resolve: {
      tipologiaVeicolo: TipologiaVeicoloResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'TipologiaVeicolos',
    },
    canActivate: [UserRouteAccessService],
  },
];
