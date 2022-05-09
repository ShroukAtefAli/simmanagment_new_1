import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDial } from '../dial.model';
import { DialService } from '../service/dial.service';
import { DialDeleteDialogComponent } from '../delete/dial-delete-dialog.component';

@Component({
  selector: 'jhi-dial',
  templateUrl: './dial.component.html',
})
export class DialComponent implements OnInit {
  dials?: IDial[];
  isLoading = false;

  constructor(protected dialService: DialService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.dialService.query().subscribe({
      next: (res: HttpResponse<IDial[]>) => {
        this.isLoading = false;
        this.dials = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IDial): number {
    return item.id!;
  }

  delete(dial: IDial): void {
    const modalRef = this.modalService.open(DialDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dial = dial;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
