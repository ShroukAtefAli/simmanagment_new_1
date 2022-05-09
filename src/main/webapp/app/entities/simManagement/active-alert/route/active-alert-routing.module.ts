import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ActiveAlertComponent } from '../list/active-alert.component';
import { ActiveAlertDetailComponent } from '../detail/active-alert-detail.component';
import { ActiveAlertUpdateComponent } from '../update/active-alert-update.component';
import { ActiveAlertRoutingResolveService } from './active-alert-routing-resolve.service';

const activeAlertRoute: Routes = [
  {
    path: '',
    component: ActiveAlertComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ActiveAlertDetailComponent,
    resolve: {
      activeAlert: ActiveAlertRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ActiveAlertUpdateComponent,
    resolve: {
      activeAlert: ActiveAlertRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ActiveAlertUpdateComponent,
    resolve: {
      activeAlert: ActiveAlertRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(activeAlertRoute)],
  exports: [RouterModule],
})
export class ActiveAlertRoutingModule {}
