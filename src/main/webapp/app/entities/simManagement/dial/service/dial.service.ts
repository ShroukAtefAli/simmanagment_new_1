import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDial, getDialIdentifier } from '../dial.model';

export type EntityResponseType = HttpResponse<IDial>;
export type EntityArrayResponseType = HttpResponse<IDial[]>;

@Injectable({ providedIn: 'root' })
export class DialService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/dials', 'simmanagement');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(dial: IDial): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dial);
    return this.http
      .post<IDial>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dial: IDial): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dial);
    return this.http
      .put<IDial>(`${this.resourceUrl}/${getDialIdentifier(dial) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(dial: IDial): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dial);
    return this.http
      .patch<IDial>(`${this.resourceUrl}/${getDialIdentifier(dial) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDial>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDial[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDialToCollectionIfMissing(dialCollection: IDial[], ...dialsToCheck: (IDial | null | undefined)[]): IDial[] {
    const dials: IDial[] = dialsToCheck.filter(isPresent);
    if (dials.length > 0) {
      const dialCollectionIdentifiers = dialCollection.map(dialItem => getDialIdentifier(dialItem)!);
      const dialsToAdd = dials.filter(dialItem => {
        const dialIdentifier = getDialIdentifier(dialItem);
        if (dialIdentifier == null || dialCollectionIdentifiers.includes(dialIdentifier)) {
          return false;
        }
        dialCollectionIdentifiers.push(dialIdentifier);
        return true;
      });
      return [...dialsToAdd, ...dialCollection];
    }
    return dialCollection;
  }

  protected convertDateFromClient(dial: IDial): IDial {
    return Object.assign({}, dial, {
      bucketdate: dial.bucketdate?.isValid() ? dial.bucketdate.format(DATE_FORMAT) : undefined,
      contractDate: dial.contractDate?.isValid() ? dial.contractDate.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.bucketdate = res.body.bucketdate ? dayjs(res.body.bucketdate) : undefined;
      res.body.contractDate = res.body.contractDate ? dayjs(res.body.contractDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((dial: IDial) => {
        dial.bucketdate = dial.bucketdate ? dayjs(dial.bucketdate) : undefined;
        dial.contractDate = dial.contractDate ? dayjs(dial.contractDate) : undefined;
      });
    }
    return res;
  }
}
