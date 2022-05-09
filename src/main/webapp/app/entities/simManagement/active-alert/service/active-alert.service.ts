import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IActiveAlert, getActiveAlertIdentifier } from '../active-alert.model';

export type EntityResponseType = HttpResponse<IActiveAlert>;
export type EntityArrayResponseType = HttpResponse<IActiveAlert[]>;

@Injectable({ providedIn: 'root' })
export class ActiveAlertService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/active-alerts', 'simmanagement');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(activeAlert: IActiveAlert): Observable<EntityResponseType> {
    return this.http.post<IActiveAlert>(this.resourceUrl, activeAlert, { observe: 'response' });
  }

  update(activeAlert: IActiveAlert): Observable<EntityResponseType> {
    return this.http.put<IActiveAlert>(`${this.resourceUrl}/${getActiveAlertIdentifier(activeAlert) as number}`, activeAlert, {
      observe: 'response',
    });
  }

  partialUpdate(activeAlert: IActiveAlert): Observable<EntityResponseType> {
    return this.http.patch<IActiveAlert>(`${this.resourceUrl}/${getActiveAlertIdentifier(activeAlert) as number}`, activeAlert, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IActiveAlert>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IActiveAlert[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addActiveAlertToCollectionIfMissing(
    activeAlertCollection: IActiveAlert[],
    ...activeAlertsToCheck: (IActiveAlert | null | undefined)[]
  ): IActiveAlert[] {
    const activeAlerts: IActiveAlert[] = activeAlertsToCheck.filter(isPresent);
    if (activeAlerts.length > 0) {
      const activeAlertCollectionIdentifiers = activeAlertCollection.map(activeAlertItem => getActiveAlertIdentifier(activeAlertItem)!);
      const activeAlertsToAdd = activeAlerts.filter(activeAlertItem => {
        const activeAlertIdentifier = getActiveAlertIdentifier(activeAlertItem);
        if (activeAlertIdentifier == null || activeAlertCollectionIdentifiers.includes(activeAlertIdentifier)) {
          return false;
        }
        activeAlertCollectionIdentifiers.push(activeAlertIdentifier);
        return true;
      });
      return [...activeAlertsToAdd, ...activeAlertCollection];
    }
    return activeAlertCollection;
  }
}
