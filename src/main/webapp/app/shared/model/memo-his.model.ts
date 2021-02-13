import { Moment } from 'moment';
import { Yn } from 'app/shared/model/enumerations/yn.model';

export interface IMemoHis {
  id?: number;
  memoContent?: string;
  regDtm?: string;
  useYn?: Yn;
  crmCustomId?: number;
}

export const defaultValue: Readonly<IMemoHis> = {};
