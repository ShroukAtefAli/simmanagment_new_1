import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDial } from '../dial.model';

@Component({
  selector: 'jhi-dial-detail',
  templateUrl: './dial-detail.component.html',
})
export class DialDetailComponent implements OnInit {
  dial: IDial | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dial }) => {
      this.dial = dial;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
