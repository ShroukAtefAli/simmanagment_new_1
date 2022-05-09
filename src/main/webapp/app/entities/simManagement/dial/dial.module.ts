import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DialComponent } from './list/dial.component';
import { DialDetailComponent } from './detail/dial-detail.component';
import { DialUpdateComponent } from './update/dial-update.component';
import { DialDeleteDialogComponent } from './delete/dial-delete-dialog.component';
import { DialRoutingModule } from './route/dial-routing.module';

@NgModule({
  imports: [SharedModule, DialRoutingModule],
  declarations: [DialComponent, DialDetailComponent, DialUpdateComponent, DialDeleteDialogComponent],
  entryComponents: [DialDeleteDialogComponent],
})
export class SimManagementDialModule {}
