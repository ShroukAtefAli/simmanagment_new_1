import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBucket } from '../bucket.model';
import { BucketService } from '../service/bucket.service';

@Component({
  templateUrl: './bucket-delete-dialog.component.html',
})
export class BucketDeleteDialogComponent {
  bucket?: IBucket;

  constructor(protected bucketService: BucketService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bucketService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
