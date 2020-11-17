import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMotivazione, Motivazione } from 'app/shared/model/motivazione.model';
import { MotivazioneService } from './motivazione.service';
import { MotivazioneComponent } from './motivazione.component';
import { MotivazioneDetailComponent } from './motivazione-detail.component';
import { MotivazioneUpdateComponent } from './motivazione-update.component';

@Injectable({ providedIn: 'root' })
export class MotivazioneResolve implements Resolve<IMotivazione> {
  constructor(private service: MotivazioneService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMotivazione> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((motivazione: HttpResponse<Motivazione>) => {
          if (motivazione.body) {
            return of(motivazione.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Motivazione());
  }
}

export const motivazioneRoute: Routes = [
  {
    path: '',
    component: MotivazioneComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Motivaziones',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MotivazioneDetailComponent,
    resolve: {
      motivazione: MotivazioneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Motivaziones',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MotivazioneUpdateComponent,
    resolve: {
      motivazione: MotivazioneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Motivaziones',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MotivazioneUpdateComponent,
    resolve: {
      motivazione: MotivazioneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Motivaziones',
    },
    canActivate: [UserRouteAccessService],
  },
];
