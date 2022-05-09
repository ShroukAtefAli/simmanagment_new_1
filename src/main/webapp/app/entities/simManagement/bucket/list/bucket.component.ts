import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBucket } from '../bucket.model';
import { BucketService } from '../service/bucket.service';
import { BucketDeleteDialogComponent } from '../delete/bucket-delete-dialog.component';

@Component({
  selector: 'jhi-bucket',
  templateUrl: './bucket.component.html',
})
export class BucketComponent implements OnInit {
  buckets?: IBucket[];
  isLoading = false;

  constructor(protected bucketService: BucketService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.bucketService.query().subscribe({
      next: (res: HttpResponse<IBucket[]>) => {
        this.isLoading = false;
        this.buckets = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IBucket): number {
    return item.id!;
  }

  delete(bucket: IBucket): void {
    const modalRef = this.modalService.open(BucketDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.bucket = bucket;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
