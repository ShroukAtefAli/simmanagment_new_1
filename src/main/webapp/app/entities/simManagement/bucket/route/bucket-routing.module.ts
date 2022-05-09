import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BucketComponent } from '../list/bucket.component';
import { BucketDetailComponent } from '../detail/bucket-detail.component';
import { BucketUpdateComponent } from '../update/bucket-update.component';
import { BucketRoutingResolveService } from './bucket-routing-resolve.service';

const bucketRoute: Routes = [
  {
    path: '',
    component: BucketComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BucketDetailComponent,
    resolve: {
      bucket: BucketRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BucketUpdateComponent,
    resolve: {
      bucket: BucketRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BucketUpdateComponent,
    resolve: {
      bucket: BucketRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(bucketRoute)],
  exports: [RouterModule],
})
export class BucketRoutingModule {}
