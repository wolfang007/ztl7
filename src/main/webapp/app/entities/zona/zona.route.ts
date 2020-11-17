import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IZona, Zona } from 'app/shared/model/zona.model';
import { ZonaService } from './zona.service';
import { ZonaComponent } from './zona.component';
import { ZonaDetailComponent } from './zona-detail.component';
import { ZonaUpdateComponent } from './zona-update.component';

@Injectable({ providedIn: 'root' })
export class ZonaResolve implements Resolve<IZona> {
  constructor(private service: ZonaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IZona> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((zona: HttpResponse<Zona>) => {
          if (zona.body) {
            return of(zona.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Zona());
  }
}

export const zonaRoute: Routes = [
  {
    path: '',
    component: ZonaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Zonas',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ZonaDetailComponent,
    resolve: {
      zona: ZonaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Zonas',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ZonaUpdateComponent,
    resolve: {
      zona: ZonaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Zonas',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ZonaUpdateComponent,
    resolve: {
      zona: ZonaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Zonas',
    },
    canActivate: [UserRouteAccessService],
  },
];
