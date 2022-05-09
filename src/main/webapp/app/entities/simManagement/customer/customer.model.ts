import { IDial } from 'app/entities/simManagement/dial/dial.model';
import { IBucket } from 'app/entities/simManagement/bucket/bucket.model';

export interface ICustomer {
  id?: number;
  customerName?: string;
  customerId?: string;
  contarctId?: number | null;
  customerParent?: string | null;
  dummyContract?: number | null;
  customerIdHight?: number | null;
  customerCode?: string | null;
  dials?: IDial[] | null;
  buckets?: IBucket[] | null;
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public customerName?: string,
    public customerId?: string,
    public contarctId?: number | null,
    public customerParent?: string | null,
    public dummyContract?: number | null,
    public customerIdHight?: number | null,
    public customerCode?: string | null,
    public dials?: IDial[] | null,
    public buckets?: IBucket[] | null
  ) {}
}

export function getCustomerIdentifier(customer: ICustomer): number | undefined {
  return customer.id;
}
