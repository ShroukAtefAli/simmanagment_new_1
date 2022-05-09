import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ActiveAlertComponent } from './list/active-alert.component';
import { ActiveAlertDetailComponent } from './detail/active-alert-detail.component';
import { ActiveAlertUpdateComponent } from './update/active-alert-update.component';
import { ActiveAlertDeleteDialogComponent } from './delete/active-alert-delete-dialog.component';
import { ActiveAlertRoutingModule } from './route/active-alert-routing.module';

@NgModule({
  imports: [SharedModule, ActiveAlertRoutingModule],
  declarations: [ActiveAlertComponent, ActiveAlertDetailComponent, ActiveAlertUpdateComponent, ActiveAlertDeleteDialogComponent],
  entryComponents: [ActiveAlertDeleteDialogComponent],
})
export class SimManagementActiveAlertModule {}
