import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Corp from './corp';
import CrmCustom from './crm-custom';
import TeamGrp from './team-grp';
import Manager from './manager';
import TmManager from './tm-manager';
import MemoHis from './memo-his';
import SendSmsHis from './send-sms-his';
import StockContractHis from './stock-contract-his';
import StockConsultingHis from './stock-consulting-his';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}corp`} component={Corp} />
      <ErrorBoundaryRoute path={`${match.url}crm-custom`} component={CrmCustom} />
      <ErrorBoundaryRoute path={`${match.url}team-grp`} component={TeamGrp} />
      <ErrorBoundaryRoute path={`${match.url}manager`} component={Manager} />
      <ErrorBoundaryRoute path={`${match.url}tm-manager`} component={TmManager} />
      <ErrorBoundaryRoute path={`${match.url}memo-his`} component={MemoHis} />
      <ErrorBoundaryRoute path={`${match.url}send-sms-his`} component={SendSmsHis} />
      <ErrorBoundaryRoute path={`${match.url}stock-contract-his`} component={StockContractHis} />
      <ErrorBoundaryRoute path={`${match.url}stock-consulting-his`} component={StockConsultingHis} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
