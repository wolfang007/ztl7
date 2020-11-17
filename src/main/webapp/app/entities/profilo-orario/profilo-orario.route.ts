import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProfiloOrario, ProfiloOrario } from 'app/shared/model/profilo-orario.model';
import { ProfiloOrarioService } from './profilo-orario.service';
import { ProfiloOrarioComponent } from './profilo-orario.component';
import { ProfiloOrarioDetailComponent } from './profilo-orario-detail.component';
import { ProfiloOrarioUpdateComponent } from './profilo-orario-update.component';

@Injectable({ providedIn: 'root' })
export class ProfiloOrarioResolve implements Resolve<IProfiloOrario> {
  constructor(private service: ProfiloOrarioService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProfiloOrario> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((profiloOrario: HttpResponse<ProfiloOrario>) => {
          if (profiloOrario.body) {
            return of(profiloOrario.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProfiloOrario());
  }
}

export const profiloOrarioRoute: Routes = [
  {
    path: '',
    component: ProfiloOrarioComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ProfiloOrarios',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProfiloOrarioDetailComponent,
    resolve: {
      profiloOrario: ProfiloOrarioResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ProfiloOrarios',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProfiloOrarioUpdateComponent,
    resolve: {
      profiloOrario: ProfiloOrarioResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ProfiloOrarios',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProfiloOrarioUpdateComponent,
    resolve: {
      profiloOrario: ProfiloOrarioResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ProfiloOrarios',
    },
    canActivate: [UserRouteAccessService],
  },
];
