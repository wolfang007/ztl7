import { Moment } from 'moment';

export interface ITestX {
  id?: number;
  xString?: string;
  xInteger?: number;
  xLong?: number;
  xBigDecimal?: number;
  xFloat?: number;
  xDouble?: number;
  xBoolean?: boolean;
  xLocalDate?: Moment;
  xZonedDateTime?: Moment;
  xInstant?: Moment;
  xUUID?: string;
  xBlobContentType?: string;
  xBlob?: any;
  xAnyBlobContentType?: string;
  xAnyBlob?: any;
  xImageBlobContentType?: string;
  xImageBlob?: any;
  xTextBlob?: any;
}

export class TestX implements ITestX {
  constructor(
    public id?: number,
    public xString?: string,
    public xInteger?: number,
    public xLong?: number,
    public xBigDecimal?: number,
    public xFloat?: number,
    public xDouble?: number,
    public xBoolean?: boolean,
    public xLocalDate?: Moment,
    public xZonedDateTime?: Moment,
    public xInstant?: Moment,
    public xUUID?: string,
    public xBlobContentType?: string,
    public xBlob?: any,
    public xAnyBlobContentType?: string,
    public xAnyBlob?: any,
    public xImageBlobContentType?: string,
    public xImageBlob?: any,
    public xTextBlob?: any
  ) {
    this.xBoolean = this.xBoolean || false;
  }
}
