import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IActiveAlert } from '../active-alert.model';
import { ActiveAlertService } from '../service/active-alert.service';

@Component({
  templateUrl: './active-alert-delete-dialog.component.html',
})
export class ActiveAlertDeleteDialogComponent {
  activeAlert?: IActiveAlert;

  constructor(protected activeAlertService: ActiveAlertService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.activeAlertService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
