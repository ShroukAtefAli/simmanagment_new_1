import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DialComponent } from '../list/dial.component';
import { DialDetailComponent } from '../detail/dial-detail.component';
import { DialUpdateComponent } from '../update/dial-update.component';
import { DialRoutingResolveService } from './dial-routing-resolve.service';

const dialRoute: Routes = [
  {
    path: '',
    component: DialComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DialDetailComponent,
    resolve: {
      dial: DialRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DialUpdateComponent,
    resolve: {
      dial: DialRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DialUpdateComponent,
    resolve: {
      dial: DialRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dialRoute)],
  exports: [RouterModule],
})
export class DialRoutingModule {}
