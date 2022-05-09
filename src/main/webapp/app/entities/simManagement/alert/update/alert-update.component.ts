import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IAlert, Alert } from '../alert.model';
import { AlertService } from '../service/alert.service';
import { AlertPeriod } from 'app/entities/enumerations/alert-period.model';

@Component({
  selector: 'jhi-alert-update',
  templateUrl: './alert-update.component.html',
})
export class AlertUpdateComponent implements OnInit {
  isSaving = false;
  alertPeriodValues = Object.keys(AlertPeriod);

  editForm = this.fb.group({
    id: [],
    alertId: [null, [Validators.required]],
    alertName: [null, [Validators.required]],
    alertCapacity: [null, [Validators.required]],
    alertSmsMessage: [],
    alertEmailMessage: [],
    alertSmsMessageActive: [],
    alertEmailMessageActive: [],
    alertPeriod: [],
  });

  constructor(protected alertService: AlertService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alert }) => {
      this.updateForm(alert);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const alert = this.createFromForm();
    if (alert.id !== undefined) {
      this.subscribeToSaveResponse(this.alertService.update(alert));
    } else {
      this.subscribeToSaveResponse(this.alertService.create(alert));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAlert>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(alert: IAlert): void {
    this.editForm.patchValue({
      id: alert.id,
      alertId: alert.alertId,
      alertName: alert.alertName,
      alertCapacity: alert.alertCapacity,
      alertSmsMessage: alert.alertSmsMessage,
      alertEmailMessage: alert.alertEmailMessage,
      alertSmsMessageActive: alert.alertSmsMessageActive,
      alertEmailMessageActive: alert.alertEmailMessageActive,
      alertPeriod: alert.alertPeriod,
    });
  }

  protected createFromForm(): IAlert {
    return {
      ...new Alert(),
      id: this.editForm.get(['id'])!.value,
      alertId: this.editForm.get(['alertId'])!.value,
      alertName: this.editForm.get(['alertName'])!.value,
      alertCapacity: this.editForm.get(['alertCapacity'])!.value,
      alertSmsMessage: this.editForm.get(['alertSmsMessage'])!.value,
      alertEmailMessage: this.editForm.get(['alertEmailMessage'])!.value,
      alertSmsMessageActive: this.editForm.get(['alertSmsMessageActive'])!.value,
      alertEmailMessageActive: this.editForm.get(['alertEmailMessageActive'])!.value,
      alertPeriod: this.editForm.get(['alertPeriod'])!.value,
    };
  }
}
