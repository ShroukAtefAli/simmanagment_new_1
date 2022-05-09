import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDial, Dial } from '../dial.model';
import { DialService } from '../service/dial.service';
import { ICustomer } from 'app/entities/simManagement/customer/customer.model';
import { CustomerService } from 'app/entities/simManagement/customer/service/customer.service';

@Component({
  selector: 'jhi-dial-update',
  templateUrl: './dial-update.component.html',
})
export class DialUpdateComponent implements OnInit {
  isSaving = false;

  customersSharedCollection: ICustomer[] = [];

  editForm = this.fb.group({
    id: [],
    dialId: [null, [Validators.required]],
    dial: [null, [Validators.required]],
    activeAlertId: [],
    dialConsumption: [],
    bucketId: [],
    bucketdate: [],
    contractStatus: [],
    contractDate: [],
    simNum: [],
    volStatusFlag: [],
    apnName: [],
    softDisconnect: [],
    billCycle: [],
    m2mMonitoringService: [],
    customer: [],
  });

  constructor(
    protected dialService: DialService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dial }) => {
      this.updateForm(dial);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dial = this.createFromForm();
    if (dial.id !== undefined) {
      this.subscribeToSaveResponse(this.dialService.update(dial));
    } else {
      this.subscribeToSaveResponse(this.dialService.create(dial));
    }
  }

  trackCustomerById(_index: number, item: ICustomer): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDial>>): void {
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

  protected updateForm(dial: IDial): void {
    this.editForm.patchValue({
      id: dial.id,
      dialId: dial.dialId,
      dial: dial.dial,
      activeAlertId: dial.activeAlertId,
      dialConsumption: dial.dialConsumption,
      bucketId: dial.bucketId,
      bucketdate: dial.bucketdate,
      contractStatus: dial.contractStatus,
      contractDate: dial.contractDate,
      simNum: dial.simNum,
      volStatusFlag: dial.volStatusFlag,
      apnName: dial.apnName,
      softDisconnect: dial.softDisconnect,
      billCycle: dial.billCycle,
      m2mMonitoringService: dial.m2mMonitoringService,
      customer: dial.customer,
    });

    this.customersSharedCollection = this.customerService.addCustomerToCollectionIfMissing(this.customersSharedCollection, dial.customer);
  }

  protected loadRelationshipsOptions(): void {
    this.customerService
      .query()
      .pipe(map((res: HttpResponse<ICustomer[]>) => res.body ?? []))
      .pipe(
        map((customers: ICustomer[]) =>
          this.customerService.addCustomerToCollectionIfMissing(customers, this.editForm.get('customer')!.value)
        )
      )
      .subscribe((customers: ICustomer[]) => (this.customersSharedCollection = customers));
  }

  protected createFromForm(): IDial {
    return {
      ...new Dial(),
      id: this.editForm.get(['id'])!.value,
      dialId: this.editForm.get(['dialId'])!.value,
      dial: this.editForm.get(['dial'])!.value,
      activeAlertId: this.editForm.get(['activeAlertId'])!.value,
      dialConsumption: this.editForm.get(['dialConsumption'])!.value,
      bucketId: this.editForm.get(['bucketId'])!.value,
      bucketdate: this.editForm.get(['bucketdate'])!.value,
      contractStatus: this.editForm.get(['contractStatus'])!.value,
      contractDate: this.editForm.get(['contractDate'])!.value,
      simNum: this.editForm.get(['simNum'])!.value,
      volStatusFlag: this.editForm.get(['volStatusFlag'])!.value,
      apnName: this.editForm.get(['apnName'])!.value,
      softDisconnect: this.editForm.get(['softDisconnect'])!.value,
      billCycle: this.editForm.get(['billCycle'])!.value,
      m2mMonitoringService: this.editForm.get(['m2mMonitoringService'])!.value,
      customer: this.editForm.get(['customer'])!.value,
    };
  }
}
