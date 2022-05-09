import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { AlertPeriod } from 'app/entities/enumerations/alert-period.model';
import { IActiveAlert, ActiveAlert } from '../active-alert.model';

import { ActiveAlertService } from './active-alert.service';

describe('ActiveAlert Service', () => {
  let service: ActiveAlertService;
  let httpMock: HttpTestingController;
  let elemDefault: IActiveAlert;
  let expectedResult: IActiveAlert | IActiveAlert[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ActiveAlertService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      activeAlertId: 0,
      activeAlertName: 'AAAAAAA',
      activeAlertCapacity: 0,
      activeAlertCustomerId: 0,
      activeAlertDialNumber: 0,
      activeAlertSmsMessage: 'AAAAAAA',
      activeAlertEmailMessage: 'AAAAAAA',
      activeAlertSmsMessageActive: false,
      activeAlertEmailMessageActive: false,
      activeAlertPeriod: AlertPeriod.Daily,
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

    it('should create a ActiveAlert', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ActiveAlert()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ActiveAlert', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          activeAlertId: 1,
          activeAlertName: 'BBBBBB',
          activeAlertCapacity: 1,
          activeAlertCustomerId: 1,
          activeAlertDialNumber: 1,
          activeAlertSmsMessage: 'BBBBBB',
          activeAlertEmailMessage: 'BBBBBB',
          activeAlertSmsMessageActive: true,
          activeAlertEmailMessageActive: true,
          activeAlertPeriod: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ActiveAlert', () => {
      const patchObject = Object.assign(
        {
          activeAlertId: 1,
          activeAlertName: 'BBBBBB',
          activeAlertDialNumber: 1,
          activeAlertEmailMessageActive: true,
        },
        new ActiveAlert()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ActiveAlert', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          activeAlertId: 1,
          activeAlertName: 'BBBBBB',
          activeAlertCapacity: 1,
          activeAlertCustomerId: 1,
          activeAlertDialNumber: 1,
          activeAlertSmsMessage: 'BBBBBB',
          activeAlertEmailMessage: 'BBBBBB',
          activeAlertSmsMessageActive: true,
          activeAlertEmailMessageActive: true,
          activeAlertPeriod: 'BBBBBB',
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

    it('should delete a ActiveAlert', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addActiveAlertToCollectionIfMissing', () => {
      it('should add a ActiveAlert to an empty array', () => {
        const activeAlert: IActiveAlert = { id: 123 };
        expectedResult = service.addActiveAlertToCollectionIfMissing([], activeAlert);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(activeAlert);
      });

      it('should not add a ActiveAlert to an array that contains it', () => {
        const activeAlert: IActiveAlert = { id: 123 };
        const activeAlertCollection: IActiveAlert[] = [
          {
            ...activeAlert,
          },
          { id: 456 },
        ];
        expectedResult = service.addActiveAlertToCollectionIfMissing(activeAlertCollection, activeAlert);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ActiveAlert to an array that doesn't contain it", () => {
        const activeAlert: IActiveAlert = { id: 123 };
        const activeAlertCollection: IActiveAlert[] = [{ id: 456 }];
        expectedResult = service.addActiveAlertToCollectionIfMissing(activeAlertCollection, activeAlert);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(activeAlert);
      });

      it('should add only unique ActiveAlert to an array', () => {
        const activeAlertArray: IActiveAlert[] = [{ id: 123 }, { id: 456 }, { id: 93080 }];
        const activeAlertCollection: IActiveAlert[] = [{ id: 123 }];
        expectedResult = service.addActiveAlertToCollectionIfMissing(activeAlertCollection, ...activeAlertArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const activeAlert: IActiveAlert = { id: 123 };
        const activeAlert2: IActiveAlert = { id: 456 };
        expectedResult = service.addActiveAlertToCollectionIfMissing([], activeAlert, activeAlert2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(activeAlert);
        expect(expectedResult).toContain(activeAlert2);
      });

      it('should accept null and undefined values', () => {
        const activeAlert: IActiveAlert = { id: 123 };
        expectedResult = service.addActiveAlertToCollectionIfMissing([], null, activeAlert, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(activeAlert);
      });

      it('should return initial array if no ActiveAlert is added', () => {
        const activeAlertCollection: IActiveAlert[] = [{ id: 123 }];
        expectedResult = service.addActiveAlertToCollectionIfMissing(activeAlertCollection, undefined, null);
        expect(expectedResult).toEqual(activeAlertCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
