import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BucketComponent } from './list/bucket.component';
import { BucketDetailComponent } from './detail/bucket-detail.component';
import { BucketUpdateComponent } from './update/bucket-update.component';
import { BucketDeleteDialogComponent } from './delete/bucket-delete-dialog.component';
import { BucketRoutingModule } from './route/bucket-routing.module';

@NgModule({
  imports: [SharedModule, BucketRoutingModule],
  declarations: [BucketComponent, BucketDetailComponent, BucketUpdateComponent, BucketDeleteDialogComponent],
  entryComponents: [BucketDeleteDialogComponent],
})
export class SimManagementBucketModule {}
