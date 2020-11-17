import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFestivita, Festivita } from 'app/shared/model/festivita.model';
import { FestivitaService } from './festivita.service';
import { FestivitaComponent } from './festivita.component';
import { FestivitaDetailComponent } from './festivita-detail.component';
import { FestivitaUpdateComponent } from './festivita-update.component';

@Injectable({ providedIn: 'root' })
export class FestivitaResolve implements Resolve<IFestivita> {
  constructor(private service: FestivitaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFestivita> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((festivita: HttpResponse<Festivita>) => {
          if (festivita.body) {
            return of(festivita.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Festivita());
  }
}

export const festivitaRoute: Routes = [
  {
    path: '',
    component: FestivitaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Festivitas',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FestivitaDetailComponent,
    resolve: {
      festivita: FestivitaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Festivitas',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FestivitaUpdateComponent,
    resolve: {
      festivita: FestivitaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Festivitas',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FestivitaUpdateComponent,
    resolve: {
      festivita: FestivitaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Festivitas',
    },
    canActivate: [UserRouteAccessService],
  },
];
