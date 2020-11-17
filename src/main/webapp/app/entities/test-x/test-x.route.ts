import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITestX, TestX } from 'app/shared/model/test-x.model';
import { TestXService } from './test-x.service';
import { TestXComponent } from './test-x.component';
import { TestXDetailComponent } from './test-x-detail.component';
import { TestXUpdateComponent } from './test-x-update.component';

@Injectable({ providedIn: 'root' })
export class TestXResolve implements Resolve<ITestX> {
  constructor(private service: TestXService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITestX> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((testX: HttpResponse<TestX>) => {
          if (testX.body) {
            return of(testX.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TestX());
  }
}

export const testXRoute: Routes = [
  {
    path: '',
    component: TestXComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'TestXES',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TestXDetailComponent,
    resolve: {
      testX: TestXResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'TestXES',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TestXUpdateComponent,
    resolve: {
      testX: TestXResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'TestXES',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TestXUpdateComponent,
    resolve: {
      testX: TestXResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'TestXES',
    },
    canActivate: [UserRouteAccessService],
  },
];
