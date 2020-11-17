import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAutorizzazione, Autorizzazione } from 'app/shared/model/autorizzazione.model';
import { AutorizzazioneService } from './autorizzazione.service';
import { AutorizzazioneComponent } from './autorizzazione.component';
import { AutorizzazioneDetailComponent } from './autorizzazione-detail.component';
import { AutorizzazioneUpdateComponent } from './autorizzazione-update.component';

@Injectable({ providedIn: 'root' })
export class AutorizzazioneResolve implements Resolve<IAutorizzazione> {
  constructor(private service: AutorizzazioneService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAutorizzazione> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((autorizzazione: HttpResponse<Autorizzazione>) => {
          if (autorizzazione.body) {
            return of(autorizzazione.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Autorizzazione());
  }
}

export const autorizzazioneRoute: Routes = [
  {
    path: '',
    component: AutorizzazioneComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Autorizzaziones',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AutorizzazioneDetailComponent,
    resolve: {
      autorizzazione: AutorizzazioneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Autorizzaziones',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AutorizzazioneUpdateComponent,
    resolve: {
      autorizzazione: AutorizzazioneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Autorizzaziones',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AutorizzazioneUpdateComponent,
    resolve: {
      autorizzazione: AutorizzazioneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Autorizzaziones',
    },
    canActivate: [UserRouteAccessService],
  },
];
