import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITipologiaPermesso, TipologiaPermesso } from 'app/shared/model/tipologia-permesso.model';
import { TipologiaPermessoService } from './tipologia-permesso.service';
import { TipologiaPermessoComponent } from './tipologia-permesso.component';
import { TipologiaPermessoDetailComponent } from './tipologia-permesso-detail.component';
import { TipologiaPermessoUpdateComponent } from './tipologia-permesso-update.component';

@Injectable({ providedIn: 'root' })
export class TipologiaPermessoResolve implements Resolve<ITipologiaPermesso> {
  constructor(private service: TipologiaPermessoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipologiaPermesso> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tipologiaPermesso: HttpResponse<TipologiaPermesso>) => {
          if (tipologiaPermesso.body) {
            return of(tipologiaPermesso.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipologiaPermesso());
  }
}

export const tipologiaPermessoRoute: Routes = [
  {
    path: '',
    component: TipologiaPermessoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'TipologiaPermessos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TipologiaPermessoDetailComponent,
    resolve: {
      tipologiaPermesso: TipologiaPermessoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'TipologiaPermessos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TipologiaPermessoUpdateComponent,
    resolve: {
      tipologiaPermesso: TipologiaPermessoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'TipologiaPermessos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TipologiaPermessoUpdateComponent,
    resolve: {
      tipologiaPermesso: TipologiaPermessoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'TipologiaPermessos',
    },
    canActivate: [UserRouteAccessService],
  },
];
