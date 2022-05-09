import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBucket, Bucket } from '../bucket.model';

import { BucketService } from './bucket.service';

describe('Bucket Service', () => {
  let service: BucketService;
  let httpMock: HttpTestingController;
  let elemDefault: IBucket;
  let expectedResult: IBucket | IBucket[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BucketService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      bucketId: 'AAAAAAA',
      bucketName: 'AAAAAAA',
      bucketCapacity: 0,
      bucketType: 'AAAAAAA',
      bucketDescription: 'AAAAAAA',
      ratePlan: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Bucket', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Bucket()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Bucket', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          bucketId: 'BBBBBB',
          bucketName: 'BBBBBB',
          bucketCapacity: 1,
          bucketType: 'BBBBBB',
          bucketDescription: 'BBBBBB',
          ratePlan: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Bucket', () => {
      const patchObject = Object.assign(
        {
          bucketId: 'BBBBBB',
          bucketName: 'BBBBBB',
          bucketDescription: 'BBBBBB',
        },
        new Bucket()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Bucket', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          bucketId: 'BBBBBB',
          bucketName: 'BBBBBB',
          bucketCapacity: 1,
          bucketType: 'BBBBBB',
          bucketDescription: 'BBBBBB',
          ratePlan: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Bucket', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addBucketToCollectionIfMissing', () => {
      it('should add a Bucket to an empty array', () => {
        const bucket: IBucket = { id: 123 };
        expectedResult = service.addBucketToCollectionIfMissing([], bucket);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bucket);
      });

      it('should not add a Bucket to an array that contains it', () => {
        const bucket: IBucket = { id: 123 };
        const bucketCollection: IBucket[] = [
          {
            ...bucket,
          },
          { id: 456 },
        ];
        expectedResult = service.addBucketToCollectionIfMissing(bucketCollection, bucket);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Bucket to an array that doesn't contain it", () => {
        const bucket: IBucket = { id: 123 };
        const bucketCollection: IBucket[] = [{ id: 456 }];
        expectedResult = service.addBucketToCollectionIfMissing(bucketCollection, bucket);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bucket);
      });

      it('should add only unique Bucket to an array', () => {
        const bucketArray: IBucket[] = [{ id: 123 }, { id: 456 }, { id: 86678 }];
        const bucketCollection: IBucket[] = [{ id: 123 }];
        expectedResult = service.addBucketToCollectionIfMissing(bucketCollection, ...bucketArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const bucket: IBucket = { id: 123 };
        const bucket2: IBucket = { id: 456 };
        expectedResult = service.addBucketToCollectionIfMissing([], bucket, bucket2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bucket);
        expect(expectedResult).toContain(bucket2);
      });

      it('should accept null and undefined values', () => {
        const bucket: IBucket = { id: 123 };
        expectedResult = service.addBucketToCollectionIfMissing([], null, bucket, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bucket);
      });

      it('should return initial array if no Bucket is added', () => {
        const bucketCollection: IBucket[] = [{ id: 123 }];
        expectedResult = service.addBucketToCollectionIfMissing(bucketCollection, undefined, null);
        expect(expectedResult).toEqual(bucketCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
