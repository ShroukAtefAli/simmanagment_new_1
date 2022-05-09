import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BucketService } from '../service/bucket.service';
import { IBucket, Bucket } from '../bucket.model';
import { ICustomer } from 'app/entities/simManagement/customer/customer.model';
import { CustomerService } from 'app/entities/simManagement/customer/service/customer.service';

import { BucketUpdateComponent } from './bucket-update.component';

describe('Bucket Management Update Component', () => {
  let comp: BucketUpdateComponent;
  let fixture: ComponentFixture<BucketUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bucketService: BucketService;
  let customerService: CustomerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BucketUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(BucketUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BucketUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    bucketService = TestBed.inject(BucketService);
    customerService = TestBed.inject(CustomerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Customer query and add missing value', () => {
      const bucket: IBucket = { id: 456 };
      const customer: ICustomer = { id: 67854 };
      bucket.customer = customer;

      const customerCollection: ICustomer[] = [{ id: 47283 }];
      jest.spyOn(customerService, 'query').mockReturnValue(of(new HttpResponse({ body: customerCollection })));
      const additionalCustomers = [customer];
      const expectedCollection: ICustomer[] = [...additionalCustomers, ...customerCollection];
      jest.spyOn(customerService, 'addCustomerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bucket });
      comp.ngOnInit();

      expect(customerService.query).toHaveBeenCalled();
      expect(customerService.addCustomerToCollectionIfMissing).toHaveBeenCalledWith(customerCollection, ...additionalCustomers);
      expect(comp.customersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const bucket: IBucket = { id: 456 };
      const customer: ICustomer = { id: 34070 };
      bucket.customer = customer;

      activatedRoute.data = of({ bucket });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(bucket));
      expect(comp.customersSharedCollection).toContain(customer);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Bucket>>();
      const bucket = { id: 123 };
      jest.spyOn(bucketService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bucket });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bucket }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(bucketService.update).toHaveBeenCalledWith(bucket);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Bucket>>();
      const bucket = new Bucket();
      jest.spyOn(bucketService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bucket });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bucket }));
      saveSubject.complete();

      // THEN
      expect(bucketService.create).toHaveBeenCalledWith(bucket);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Bucket>>();
      const bucket = { id: 123 };
      jest.spyOn(bucketService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bucket });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(bucketService.update).toHaveBeenCalledWith(bucket);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackCustomerById', () => {
      it('Should return tracked Customer primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCustomerById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
