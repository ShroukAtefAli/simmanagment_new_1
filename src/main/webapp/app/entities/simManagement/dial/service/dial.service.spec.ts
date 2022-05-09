import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IDial, Dial } from '../dial.model';

import { DialService } from './dial.service';

describe('Dial Service', () => {
  let service: DialService;
  let httpMock: HttpTestingController;
  let elemDefault: IDial;
  let expectedResult: IDial | IDial[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DialService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      dialId: 'AAAAAAA',
      dial: 'AAAAAAA',
      activeAlertId: 0,
      dialConsumption: 0,
      bucketId: 0,
      bucketdate: currentDate,
      contractStatus: 'AAAAAAA',
      contractDate: currentDate,
      simNum: 'AAAAAAA',
      volStatusFlag: 'AAAAAAA',
      apnName: 'AAAAAAA',
      softDisconnect: 'AAAAAAA',
      billCycle: 0,
      m2mMonitoringService: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          bucketdate: currentDate.format(DATE_FORMAT),
          contractDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Dial', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          bucketdate: currentDate.format(DATE_FORMAT),
          contractDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          bucketdate: currentDate,
          contractDate: currentDate,
        },
        returnedFromService
      );

      service.create(new Dial()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Dial', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          dialId: 'BBBBBB',
          dial: 'BBBBBB',
          activeAlertId: 1,
          dialConsumption: 1,
          bucketId: 1,
          bucketdate: currentDate.format(DATE_FORMAT),
          contractStatus: 'BBBBBB',
          contractDate: currentDate.format(DATE_FORMAT),
          simNum: 'BBBBBB',
          volStatusFlag: 'BBBBBB',
          apnName: 'BBBBBB',
          softDisconnect: 'BBBBBB',
          billCycle: 1,
          m2mMonitoringService: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          bucketdate: currentDate,
          contractDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Dial', () => {
      const patchObject = Object.assign(
        {
          dialId: 'BBBBBB',
          dialConsumption: 1,
          bucketId: 1,
          bucketdate: currentDate.format(DATE_FORMAT),
          contractDate: currentDate.format(DATE_FORMAT),
          apnName: 'BBBBBB',
          softDisconnect: 'BBBBBB',
        },
        new Dial()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          bucketdate: currentDate,
          contractDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Dial', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          dialId: 'BBBBBB',
          dial: 'BBBBBB',
          activeAlertId: 1,
          dialConsumption: 1,
          bucketId: 1,
          bucketdate: currentDate.format(DATE_FORMAT),
          contractStatus: 'BBBBBB',
          contractDate: currentDate.format(DATE_FORMAT),
          simNum: 'BBBBBB',
          volStatusFlag: 'BBBBBB',
          apnName: 'BBBBBB',
          softDisconnect: 'BBBBBB',
          billCycle: 1,
          m2mMonitoringService: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          bucketdate: currentDate,
          contractDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Dial', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDialToCollectionIfMissing', () => {
      it('should add a Dial to an empty array', () => {
        const dial: IDial = { id: 123 };
        expectedResult = service.addDialToCollectionIfMissing([], dial);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dial);
      });

      it('should not add a Dial to an array that contains it', () => {
        const dial: IDial = { id: 123 };
        const dialCollection: IDial[] = [
          {
            ...dial,
          },
          { id: 456 },
        ];
        expectedResult = service.addDialToCollectionIfMissing(dialCollection, dial);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Dial to an array that doesn't contain it", () => {
        const dial: IDial = { id: 123 };
        const dialCollection: IDial[] = [{ id: 456 }];
        expectedResult = service.addDialToCollectionIfMissing(dialCollection, dial);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dial);
      });

      it('should add only unique Dial to an array', () => {
        const dialArray: IDial[] = [{ id: 123 }, { id: 456 }, { id: 86074 }];
        const dialCollection: IDial[] = [{ id: 123 }];
        expectedResult = service.addDialToCollectionIfMissing(dialCollection, ...dialArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const dial: IDial = { id: 123 };
        const dial2: IDial = { id: 456 };
        expectedResult = service.addDialToCollectionIfMissing([], dial, dial2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dial);
        expect(expectedResult).toContain(dial2);
      });

      it('should accept null and undefined values', () => {
        const dial: IDial = { id: 123 };
        expectedResult = service.addDialToCollectionIfMissing([], null, dial, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dial);
      });

      it('should return initial array if no Dial is added', () => {
        const dialCollection: IDial[] = [{ id: 123 }];
        expectedResult = service.addDialToCollectionIfMissing(dialCollection, undefined, null);
        expect(expectedResult).toEqual(dialCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
