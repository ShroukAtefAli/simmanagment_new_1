import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IBucket, Bucket } from '../bucket.model';
import { BucketService } from '../service/bucket.service';
import { ICustomer } from 'app/entities/simManagement/customer/customer.model';
import { CustomerService } from 'app/entities/simManagement/customer/service/customer.service';

@Component({
  selector: 'jhi-bucket-update',
  templateUrl: './bucket-update.component.html',
})
export class BucketUpdateComponent implements OnInit {
  isSaving = false;

  customersSharedCollection: ICustomer[] = [];

  editForm = this.fb.group({
    id: [],
    bucketId: [null, [Validators.required]],
    bucketName: [null, [Validators.required]],
    bucketCapacity: [null, [Validators.required]],
    bucketType: [null, [Validators.required]],
    bucketDescription: [],
    ratePlan: [],
    customer: [],
  });

  constructor(
    protected bucketService: BucketService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bucket }) => {
      this.updateForm(bucket);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bucket = this.createFromForm();
    if (bucket.id !== undefined) {
      this.subscribeToSaveResponse(this.bucketService.update(bucket));
    } else {
      this.subscribeToSaveResponse(this.bucketService.create(bucket));
    }
  }

  trackCustomerById(_index: number, item: ICustomer): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBucket>>): void {
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

  protected updateForm(bucket: IBucket): void {
    this.editForm.patchValue({
      id: bucket.id,
      bucketId: bucket.bucketId,
      bucketName: bucket.bucketName,
      bucketCapacity: bucket.bucketCapacity,
      bucketType: bucket.bucketType,
      bucketDescription: bucket.bucketDescription,
      ratePlan: bucket.ratePlan,
      customer: bucket.customer,
    });

    this.customersSharedCollection = this.customerService.addCustomerToCollectionIfMissing(this.customersSharedCollection, bucket.customer);
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

  protected createFromForm(): IBucket {
    return {
      ...new Bucket(),
      id: this.editForm.get(['id'])!.value,
      bucketId: this.editForm.get(['bucketId'])!.value,
      bucketName: this.editForm.get(['bucketName'])!.value,
      bucketCapacity: this.editForm.get(['bucketCapacity'])!.value,
      bucketType: this.editForm.get(['bucketType'])!.value,
      bucketDescription: this.editForm.get(['bucketDescription'])!.value,
      ratePlan: this.editForm.get(['ratePlan'])!.value,
      customer: this.editForm.get(['customer'])!.value,
    };
  }
}
