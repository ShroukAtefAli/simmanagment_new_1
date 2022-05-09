import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDial } from '../dial.model';
import { DialService } from '../service/dial.service';

@Component({
  templateUrl: './dial-delete-dialog.component.html',
})
export class DialDeleteDialogComponent {
  dial?: IDial;

  constructor(protected dialService: DialService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dialService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
