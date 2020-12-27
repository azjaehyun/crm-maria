import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TmManager from './tm-manager';
import TmManagerDetail from './tm-manager-detail';
import TmManagerUpdate from './tm-manager-update';
import TmManagerDeleteDialog from './tm-manager-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TmManagerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TmManagerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TmManagerDetail} />
      <ErrorBoundaryRoute path={match.url} component={TmManager} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TmManagerDeleteDialog} />
  </>
);

export default Routes;
