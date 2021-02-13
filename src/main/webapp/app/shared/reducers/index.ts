import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import corp, {
  CorpState
} from 'app/entities/corp/corp.reducer';
// prettier-ignore
import crmCustom, {
  CrmCustomState
} from 'app/entities/crm-custom/crm-custom.reducer';
// prettier-ignore
import teamGrp, {
  TeamGrpState
} from 'app/entities/team-grp/team-grp.reducer';
// prettier-ignore
import manager, {
  ManagerState
} from 'app/entities/manager/manager.reducer';
// prettier-ignore
import tmManager, {
  TmManagerState
} from 'app/entities/tm-manager/tm-manager.reducer';
// prettier-ignore
import memoHis, {
  MemoHisState
} from 'app/entities/memo-his/memo-his.reducer';
// prettier-ignore
import sendSmsHis, {
  SendSmsHisState
} from 'app/entities/send-sms-his/send-sms-his.reducer';
// prettier-ignore
import stockContractHis, {
  StockContractHisState
} from 'app/entities/stock-contract-his/stock-contract-his.reducer';
// prettier-ignore
import stockConsultingHis, {
  StockConsultingHisState
} from 'app/entities/stock-consulting-his/stock-consulting-his.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly corp: CorpState;
  readonly crmCustom: CrmCustomState;
  readonly teamGrp: TeamGrpState;
  readonly manager: ManagerState;
  readonly tmManager: TmManagerState;
  readonly memoHis: MemoHisState;
  readonly sendSmsHis: SendSmsHisState;
  readonly stockContractHis: StockContractHisState;
  readonly stockConsultingHis: StockConsultingHisState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  corp,
  crmCustom,
  teamGrp,
  manager,
  tmManager,
  memoHis,
  sendSmsHis,
  stockContractHis,
  stockConsultingHis,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
