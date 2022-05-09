import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'customer',
        data: { pageTitle: 'simManagementApp.simManagementCustomer.home.title' },
        loadChildren: () => import('./simManagement/customer/customer.module').then(m => m.SimManagementCustomerModule),
      },
      {
        path: 'dial',
        data: { pageTitle: 'simManagementApp.simManagementDial.home.title' },
        loadChildren: () => import('./simManagement/dial/dial.module').then(m => m.SimManagementDialModule),
      },
      {
        path: 'bucket',
        data: { pageTitle: 'simManagementApp.simManagementBucket.home.title' },
        loadChildren: () => import('./simManagement/bucket/bucket.module').then(m => m.SimManagementBucketModule),
      },
      {
        path: 'alert',
        data: { pageTitle: 'simManagementApp.simManagementAlert.home.title' },
        loadChildren: () => import('./simManagement/alert/alert.module').then(m => m.SimManagementAlertModule),
      },
      {
        path: 'active-alert',
        data: { pageTitle: 'simManagementApp.simManagementActiveAlert.home.title' },
        loadChildren: () => import('./simManagement/active-alert/active-alert.module').then(m => m.SimManagementActiveAlertModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
