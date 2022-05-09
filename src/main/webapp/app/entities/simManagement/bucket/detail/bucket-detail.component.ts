import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBucket } from '../bucket.model';

@Component({
  selector: 'jhi-bucket-detail',
  templateUrl: './bucket-detail.component.html',
})
export class BucketDetailComponent implements OnInit {
  bucket: IBucket | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bucket }) => {
      this.bucket = bucket;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
