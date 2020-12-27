import { Moment } from 'moment';
import { Yn } from 'app/shared/model/enumerations/yn.model';

export interface IStockContractHis {
  id?: number;
  fromContractDt?: string;
  toContractDt?: string;
  contractAmount?: number;
  regDtm?: string;
  useYn?: Yn;
  crmCustomId?: number;
}

export const defaultValue: Readonly<IStockContractHis> = {};
