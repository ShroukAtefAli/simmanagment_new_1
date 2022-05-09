import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDial, Dial } from '../dial.model';
import { DialService } from '../service/dial.service';

@Injectable({ providedIn: 'root' })
export class DialRoutingResolveService implements Resolve<IDial> {
  constructor(protected service: DialService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDial> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dial: HttpResponse<Dial>) => {
          if (dial.body) {
            return of(dial.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Dial());
  }
}
