import { Moment } from 'moment';
import { Yn } from 'app/shared/model/enumerations/yn.model';

export interface IStockConsultingHis {
  id?: number;
  consultingMemo?: string;
  regDtm?: string;
  useYn?: Yn;
  crmCustomId?: number;
}

export const defaultValue: Readonly<IStockConsultingHis> = {};
