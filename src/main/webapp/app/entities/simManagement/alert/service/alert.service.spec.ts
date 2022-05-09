import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { AlertPeriod } from 'app/entities/enumerations/alert-period.model';
import { IAlert, Alert } from '../alert.model';

import { AlertService } from './alert.service';

describe('Alert Service', () => {
  let service: AlertService;
  let httpMock: HttpTestingController;
  let elemDefault: IAlert;
  let expectedResult: IAlert | IAlert[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AlertService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      alertId: 'AAAAAAA',
      alertName: 'AAAAAAA',
      alertCapacity: 0,
      alertSmsMessage: 'AAAAAAA',
      alertEmailMessage: 'AAAAAAA',
      alertSmsMessageActive: false,
      alertEmailMessageActive: false,
      alertPeriod: AlertPeriod.Daily,
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

    it('should create a Alert', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Alert()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Alert', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          alertId: 'BBBBBB',
          alertName: 'BBBBBB',
          alertCapacity: 1,
          alertSmsMessage: 'BBBBBB',
          alertEmailMessage: 'BBBBBB',
          alertSmsMessageActive: true,
          alertEmailMessageActive: true,
          alertPeriod: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Alert', () => {
      const patchObject = Object.assign(
        {
          alertId: 'BBBBBB',
          alertEmailMessage: 'BBBBBB',
          alertSmsMessageActive: true,
        },
        new Alert()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Alert', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          alertId: 'BBBBBB',
          alertName: 'BBBBBB',
          alertCapacity: 1,
          alertSmsMessage: 'BBBBBB',
          alertEmailMessage: 'BBBBBB',
          alertSmsMessageActive: true,
          alertEmailMessageActive: true,
          alertPeriod: 'BBBBBB',
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

    it('should delete a Alert', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAlertToCollectionIfMissing', () => {
      it('should add a Alert to an empty array', () => {
        const alert: IAlert = { id: 123 };
        expectedResult = service.addAlertToCollectionIfMissing([], alert);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(alert);
      });

      it('should not add a Alert to an array that contains it', () => {
        const alert: IAlert = { id: 123 };
        const alertCollection: IAlert[] = [
          {
            ...alert,
          },
          { id: 456 },
        ];
        expectedResult = service.addAlertToCollectionIfMissing(alertCollection, alert);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Alert to an array that doesn't contain it", () => {
        const alert: IAlert = { id: 123 };
        const alertCollection: IAlert[] = [{ id: 456 }];
        expectedResult = service.addAlertToCollectionIfMissing(alertCollection, alert);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(alert);
      });

      it('should add only unique Alert to an array', () => {
        const alertArray: IAlert[] = [{ id: 123 }, { id: 456 }, { id: 34403 }];
        const alertCollection: IAlert[] = [{ id: 123 }];
        expectedResult = service.addAlertToCollectionIfMissing(alertCollection, ...alertArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const alert: IAlert = { id: 123 };
        const alert2: IAlert = { id: 456 };
        expectedResult = service.addAlertToCollectionIfMissing([], alert, alert2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(alert);
        expect(expectedResult).toContain(alert2);
      });

      it('should accept null and undefined values', () => {
        const alert: IAlert = { id: 123 };
        expectedResult = service.addAlertToCollectionIfMissing([], null, alert, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(alert);
      });

      it('should return initial array if no Alert is added', () => {
        const alertCollection: IAlert[] = [{ id: 123 }];
        expectedResult = service.addAlertToCollectionIfMissing(alertCollection, undefined, null);
        expect(expectedResult).toEqual(alertCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
