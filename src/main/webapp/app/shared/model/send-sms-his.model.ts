import { Moment } from 'moment';
import { Yn } from 'app/shared/model/enumerations/yn.model';

export interface ISendSmsHis {
  id?: number;
  sendDtm?: string;
  fromPhoneNum?: string;
  toPhoneNum?: string;
  useYn?: Yn;
  crmCustomId?: number;
}

export const defaultValue: Readonly<ISendSmsHis> = {};
