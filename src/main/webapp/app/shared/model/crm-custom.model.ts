import { IMemoHis } from 'app/shared/model/memo-his.model';
import { ISendSmsHis } from 'app/shared/model/send-sms-his.model';
import { IStockContractHis } from 'app/shared/model/stock-contract-his.model';
import { IStockConsultingHis } from 'app/shared/model/stock-consulting-his.model';
import { SalesStatus } from 'app/shared/model/enumerations/sales-status.model';
import { SmsReceptionYn } from 'app/shared/model/enumerations/sms-reception-yn.model';
import { CallStatus } from 'app/shared/model/enumerations/call-status.model';
import { CustomStatus } from 'app/shared/model/enumerations/custom-status.model';
import { Yn } from 'app/shared/model/enumerations/yn.model';

export interface ICrmCustom {
  id?: number;
  corpCode?: string;
  phoneNum?: string;
  fiveDayfreeYn?: string;
  salesStatus?: SalesStatus;
  smsReceptionYn?: SmsReceptionYn;
  callStatus?: CallStatus;
  customStatus?: CustomStatus;
  tempOneStatus?: string;
  tempTwoStatus?: string;
  dbInsertType?: string;
  useYn?: Yn;
  memoHis?: IMemoHis[];
  sendSmsHis?: ISendSmsHis[];
  stockContractHis?: IStockContractHis[];
  stockConsultingHis?: IStockConsultingHis[];
  managerId?: number;
  tmManagerId?: number;
}

export const defaultValue: Readonly<ICrmCustom> = {};
