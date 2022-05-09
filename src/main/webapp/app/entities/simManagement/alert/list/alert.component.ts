import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAlert } from '../alert.model';
import { AlertService } from '../service/alert.service';
import { AlertDeleteDialogComponent } from '../delete/alert-delete-dialog.component';

@Component({
  selector: 'jhi-alert',
  templateUrl: './alert.component.html',
})
export class AlertComponent implements OnInit {
  alerts?: IAlert[];
  isLoading = false;

  constructor(protected alertService: AlertService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.alertService.query().subscribe({
      next: (res: HttpResponse<IAlert[]>) => {
        this.isLoading = false;
        this.alerts = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IAlert): number {
    return item.id!;
  }

  delete(alert: IAlert): void {
    const modalRef = this.modalService.open(AlertDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.alert = alert;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
