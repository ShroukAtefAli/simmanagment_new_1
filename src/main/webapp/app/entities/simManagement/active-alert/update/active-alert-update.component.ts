import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IActiveAlert, ActiveAlert } from '../active-alert.model';
import { ActiveAlertService } from '../service/active-alert.service';
import { IDial } from 'app/entities/simManagement/dial/dial.model';
import { DialService } from 'app/entities/simManagement/dial/service/dial.service';
import { AlertPeriod } from 'app/entities/enumerations/alert-period.model';

@Component({
  selector: 'jhi-active-alert-update',
  templateUrl: './active-alert-update.component.html',
})
export class ActiveAlertUpdateComponent implements OnInit {
  isSaving = false;
  alertPeriodValues = Object.keys(AlertPeriod);

  dialsSharedCollection: IDial[] = [];

  editForm = this.fb.group({
    id: [],
    activeAlertId: [null, [Validators.required]],
    activeAlertName: [null, [Validators.required]],
    activeAlertCapacity: [null, [Validators.required]],
    activeAlertCustomerId: [null, [Validators.required]],
    activeAlertDialNumber: [null, [Validators.required]],
    activeAlertSmsMessage: [],
    activeAlertEmailMessage: [],
    activeAlertSmsMessageActive: [],
    activeAlertEmailMessageActive: [],
    activeAlertPeriod: [],
    dial: [],
  });

  constructor(
    protected activeAlertService: ActiveAlertService,
    protected dialService: DialService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ activeAlert }) => {
      this.updateForm(activeAlert);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const activeAlert = this.createFromForm();
    if (activeAlert.id !== undefined) {
      this.subscribeToSaveResponse(this.activeAlertService.update(activeAlert));
    } else {
      this.subscribeToSaveResponse(this.activeAlertService.create(activeAlert));
    }
  }

  trackDialById(_index: number, item: IDial): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActiveAlert>>): void {
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

  protected updateForm(activeAlert: IActiveAlert): void {
    this.editForm.patchValue({
      id: activeAlert.id,
      activeAlertId: activeAlert.activeAlertId,
      activeAlertName: activeAlert.activeAlertName,
      activeAlertCapacity: activeAlert.activeAlertCapacity,
      activeAlertCustomerId: activeAlert.activeAlertCustomerId,
      activeAlertDialNumber: activeAlert.activeAlertDialNumber,
      activeAlertSmsMessage: activeAlert.activeAlertSmsMessage,
      activeAlertEmailMessage: activeAlert.activeAlertEmailMessage,
      activeAlertSmsMessageActive: activeAlert.activeAlertSmsMessageActive,
      activeAlertEmailMessageActive: activeAlert.activeAlertEmailMessageActive,
      activeAlertPeriod: activeAlert.activeAlertPeriod,
      dial: activeAlert.dial,
    });

    this.dialsSharedCollection = this.dialService.addDialToCollectionIfMissing(this.dialsSharedCollection, activeAlert.dial);
  }

  protected loadRelationshipsOptions(): void {
    this.dialService
      .query()
      .pipe(map((res: HttpResponse<IDial[]>) => res.body ?? []))
      .pipe(map((dials: IDial[]) => this.dialService.addDialToCollectionIfMissing(dials, this.editForm.get('dial')!.value)))
      .subscribe((dials: IDial[]) => (this.dialsSharedCollection = dials));
  }

  protected createFromForm(): IActiveAlert {
    return {
      ...new ActiveAlert(),
      id: this.editForm.get(['id'])!.value,
      activeAlertId: this.editForm.get(['activeAlertId'])!.value,
      activeAlertName: this.editForm.get(['activeAlertName'])!.value,
      activeAlertCapacity: this.editForm.get(['activeAlertCapacity'])!.value,
      activeAlertCustomerId: this.editForm.get(['activeAlertCustomerId'])!.value,
      activeAlertDialNumber: this.editForm.get(['activeAlertDialNumber'])!.value,
      activeAlertSmsMessage: this.editForm.get(['activeAlertSmsMessage'])!.value,
      activeAlertEmailMessage: this.editForm.get(['activeAlertEmailMessage'])!.value,
      activeAlertSmsMessageActive: this.editForm.get(['activeAlertSmsMessageActive'])!.value,
      activeAlertEmailMessageActive: this.editForm.get(['activeAlertEmailMessageActive'])!.value,
      activeAlertPeriod: this.editForm.get(['activeAlertPeriod'])!.value,
      dial: this.editForm.get(['dial'])!.value,
    };
  }
}
