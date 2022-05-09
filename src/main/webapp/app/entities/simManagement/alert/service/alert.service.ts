import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAlert, getAlertIdentifier } from '../alert.model';

export type EntityResponseType = HttpResponse<IAlert>;
export type EntityArrayResponseType = HttpResponse<IAlert[]>;

@Injectable({ providedIn: 'root' })
export class AlertService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/alerts', 'simmanagement');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(alert: IAlert): Observable<EntityResponseType> {
    return this.http.post<IAlert>(this.resourceUrl, alert, { observe: 'response' });
  }

  update(alert: IAlert): Observable<EntityResponseType> {
    return this.http.put<IAlert>(`${this.resourceUrl}/${getAlertIdentifier(alert) as number}`, alert, { observe: 'response' });
  }

  partialUpdate(alert: IAlert): Observable<EntityResponseType> {
    return this.http.patch<IAlert>(`${this.resourceUrl}/${getAlertIdentifier(alert) as number}`, alert, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAlert>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAlert[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAlertToCollectionIfMissing(alertCollection: IAlert[], ...alertsToCheck: (IAlert | null | undefined)[]): IAlert[] {
    const alerts: IAlert[] = alertsToCheck.filter(isPresent);
    if (alerts.length > 0) {
      const alertCollectionIdentifiers = alertCollection.map(alertItem => getAlertIdentifier(alertItem)!);
      const alertsToAdd = alerts.filter(alertItem => {
        const alertIdentifier = getAlertIdentifier(alertItem);
        if (alertIdentifier == null || alertCollectionIdentifiers.includes(alertIdentifier)) {
          return false;
        }
        alertCollectionIdentifiers.push(alertIdentifier);
        return true;
      });
      return [...alertsToAdd, ...alertCollection];
    }
    return alertCollection;
  }
}
