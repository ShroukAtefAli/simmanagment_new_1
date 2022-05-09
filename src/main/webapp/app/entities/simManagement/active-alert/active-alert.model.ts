import { IDial } from 'app/entities/simManagement/dial/dial.model';
import { AlertPeriod } from 'app/entities/enumerations/alert-period.model';

export interface IActiveAlert {
  id?: number;
  activeAlertId?: number;
  activeAlertName?: string;
  activeAlertCapacity?: number;
  activeAlertCustomerId?: number;
  activeAlertDialNumber?: number;
  activeAlertSmsMessage?: string | null;
  activeAlertEmailMessage?: string | null;
  activeAlertSmsMessageActive?: boolean | null;
  activeAlertEmailMessageActive?: boolean | null;
  activeAlertPeriod?: AlertPeriod | null;
  dial?: IDial | null;
}

export class ActiveAlert implements IActiveAlert {
  constructor(
    public id?: number,
    public activeAlertId?: number,
    public activeAlertName?: string,
    public activeAlertCapacity?: number,
    public activeAlertCustomerId?: number,
    public activeAlertDialNumber?: number,
    public activeAlertSmsMessage?: string | null,
    public activeAlertEmailMessage?: string | null,
    public activeAlertSmsMessageActive?: boolean | null,
    public activeAlertEmailMessageActive?: boolean | null,
    public activeAlertPeriod?: AlertPeriod | null,
    public dial?: IDial | null
  ) {
    this.activeAlertSmsMessageActive = this.activeAlertSmsMessageActive ?? false;
    this.activeAlertEmailMessageActive = this.activeAlertEmailMessageActive ?? false;
  }
}

export function getActiveAlertIdentifier(activeAlert: IActiveAlert): number | undefined {
  return activeAlert.id;
}
