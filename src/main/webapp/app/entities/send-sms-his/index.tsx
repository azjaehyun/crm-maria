import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SendSmsHis from './send-sms-his';
import SendSmsHisDetail from './send-sms-his-detail';
import SendSmsHisUpdate from './send-sms-his-update';
import SendSmsHisDeleteDialog from './send-sms-his-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SendSmsHisUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SendSmsHisUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SendSmsHisDetail} />
      <ErrorBoundaryRoute path={match.url} component={SendSmsHis} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SendSmsHisDeleteDialog} />
  </>
);

export default Routes;
