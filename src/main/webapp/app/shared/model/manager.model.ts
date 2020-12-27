import { Moment } from 'moment';
import { ICrmCustom } from 'app/shared/model/crm-custom.model';
import { Yn } from 'app/shared/model/enumerations/yn.model';

export interface IManager {
  id?: number;
  corpCode?: string;
  managerName?: string;
  managerPhoneNum?: string;
  teamCode?: string;
  totalSalesAmount?: number;
  enterDay?: string;
  outDay?: string;
  useYn?: Yn;
  crmCustoms?: ICrmCustom[];
  teamId?: number;
}

export const defaultValue: Readonly<IManager> = {};
