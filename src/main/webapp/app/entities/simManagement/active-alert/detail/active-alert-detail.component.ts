import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IActiveAlert } from '../active-alert.model';

@Component({
  selector: 'jhi-active-alert-detail',
  templateUrl: './active-alert-detail.component.html',
})
export class ActiveAlertDetailComponent implements OnInit {
  activeAlert: IActiveAlert | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ activeAlert }) => {
      this.activeAlert = activeAlert;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
