import { ICrmCustom } from 'app/shared/model/crm-custom.model';
import { Yn } from 'app/shared/model/enumerations/yn.model';

export interface ITmManager {
  id?: number;
  corpCode?: string;
  tmManagerName?: string;
  tmManagerPhoneNum?: string;
  teamCode?: string;
  crmManageCnt?: number;
  useYn?: Yn;
  crmCustoms?: ICrmCustom[];
  teamId?: number;
}

export const defaultValue: Readonly<ITmManager> = {};
