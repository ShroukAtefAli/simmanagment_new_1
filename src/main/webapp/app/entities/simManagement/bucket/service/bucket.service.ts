import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBucket, getBucketIdentifier } from '../bucket.model';

export type EntityResponseType = HttpResponse<IBucket>;
export type EntityArrayResponseType = HttpResponse<IBucket[]>;

@Injectable({ providedIn: 'root' })
export class BucketService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/buckets', 'simmanagement');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(bucket: IBucket): Observable<EntityResponseType> {
    return this.http.post<IBucket>(this.resourceUrl, bucket, { observe: 'response' });
  }

  update(bucket: IBucket): Observable<EntityResponseType> {
    return this.http.put<IBucket>(`${this.resourceUrl}/${getBucketIdentifier(bucket) as number}`, bucket, { observe: 'response' });
  }

  partialUpdate(bucket: IBucket): Observable<EntityResponseType> {
    return this.http.patch<IBucket>(`${this.resourceUrl}/${getBucketIdentifier(bucket) as number}`, bucket, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBucket>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBucket[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBucketToCollectionIfMissing(bucketCollection: IBucket[], ...bucketsToCheck: (IBucket | null | undefined)[]): IBucket[] {
    const buckets: IBucket[] = bucketsToCheck.filter(isPresent);
    if (buckets.length > 0) {
      const bucketCollectionIdentifiers = bucketCollection.map(bucketItem => getBucketIdentifier(bucketItem)!);
      const bucketsToAdd = buckets.filter(bucketItem => {
        const bucketIdentifier = getBucketIdentifier(bucketItem);
        if (bucketIdentifier == null || bucketCollectionIdentifiers.includes(bucketIdentifier)) {
          return false;
        }
        bucketCollectionIdentifiers.push(bucketIdentifier);
        return true;
      });
      return [...bucketsToAdd, ...bucketCollection];
    }
    return bucketCollection;
  }
}
