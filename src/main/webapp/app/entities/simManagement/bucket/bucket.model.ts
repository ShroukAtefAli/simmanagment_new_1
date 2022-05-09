import { ICustomer } from 'app/entities/simManagement/customer/customer.model';

export interface IBucket {
  id?: number;
  bucketId?: string;
  bucketName?: string;
  bucketCapacity?: number;
  bucketType?: string;
  bucketDescription?: string | null;
  ratePlan?: string | null;
  customer?: ICustomer | null;
}

export class Bucket implements IBucket {
  constructor(
    public id?: number,
    public bucketId?: string,
    public bucketName?: string,
    public bucketCapacity?: number,
    public bucketType?: string,
    public bucketDescription?: string | null,
    public ratePlan?: string | null,
    public customer?: ICustomer | null
  ) {}
}

export function getBucketIdentifier(bucket: IBucket): number | undefined {
  return bucket.id;
}
