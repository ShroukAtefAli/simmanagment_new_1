import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IActiveAlert, ActiveAlert } from '../active-alert.model';
import { ActiveAlertService } from '../service/active-alert.service';

@Injectable({ providedIn: 'root' })
export class ActiveAlertRoutingResolveService implements Resolve<IActiveAlert> {
  constructor(protected service: ActiveAlertService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IActiveAlert> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((activeAlert: HttpResponse<ActiveAlert>) => {
          if (activeAlert.body) {
            return of(activeAlert.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ActiveAlert());
  }
}
