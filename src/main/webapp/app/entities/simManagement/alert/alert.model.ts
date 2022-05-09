import { AlertPeriod } from 'app/entities/enumerations/alert-period.model';

export interface IAlert {
  id?: number;
  alertId?: string;
  alertName?: string;
  alertCapacity?: number;
  alertSmsMessage?: string | null;
  alertEmailMessage?: string | null;
  alertSmsMessageActive?: boolean | null;
  alertEmailMessageActive?: boolean | null;
  alertPeriod?: AlertPeriod | null;
}

export class Alert implements IAlert {
  constructor(
    public id?: number,
    public alertId?: string,
    public alertName?: string,
    public alertCapacity?: number,
    public alertSmsMessage?: string | null,
    public alertEmailMessage?: string | null,
    public alertSmsMessageActive?: boolean | null,
    public alertEmailMessageActive?: boolean | null,
    public alertPeriod?: AlertPeriod | null
  ) {
    this.alertSmsMessageActive = this.alertSmsMessageActive ?? false;
    this.alertEmailMessageActive = this.alertEmailMessageActive ?? false;
  }
}

export function getAlertIdentifier(alert: IAlert): number | undefined {
  return alert.id;
}
