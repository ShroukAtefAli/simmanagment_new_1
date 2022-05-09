import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBucket, Bucket } from '../bucket.model';
import { BucketService } from '../service/bucket.service';

@Injectable({ providedIn: 'root' })
export class BucketRoutingResolveService implements Resolve<IBucket> {
  constructor(protected service: BucketService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBucket> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((bucket: HttpResponse<Bucket>) => {
          if (bucket.body) {
            return of(bucket.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Bucket());
  }
}
