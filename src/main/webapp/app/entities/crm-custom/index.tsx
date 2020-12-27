import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CrmCustom from './crm-custom';
import CrmCustomDetail from './crm-custom-detail';
import CrmCustomUpdate from './crm-custom-update';
import CrmCustomDeleteDialog from './crm-custom-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CrmCustomUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CrmCustomUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CrmCustomDetail} />
      <ErrorBoundaryRoute path={match.url} component={CrmCustom} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CrmCustomDeleteDialog} />
  </>
);

export default Routes;
