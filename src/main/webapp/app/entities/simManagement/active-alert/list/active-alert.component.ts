import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IActiveAlert } from '../active-alert.model';
import { ActiveAlertService } from '../service/active-alert.service';
import { ActiveAlertDeleteDialogComponent } from '../delete/active-alert-delete-dialog.component';

@Component({
  selector: 'jhi-active-alert',
  templateUrl: './active-alert.component.html',
})
export class ActiveAlertComponent implements OnInit {
  activeAlerts?: IActiveAlert[];
  isLoading = false;

  constructor(protected activeAlertService: ActiveAlertService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.activeAlertService.query().subscribe({
      next: (res: HttpResponse<IActiveAlert[]>) => {
        this.isLoading = false;
        this.activeAlerts = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IActiveAlert): number {
    return item.id!;
  }

  delete(activeAlert: IActiveAlert): void {
    const modalRef = this.modalService.open(ActiveAlertDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.activeAlert = activeAlert;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
