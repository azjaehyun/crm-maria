import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Corp from './corp';
import CorpDetail from './corp-detail';
import CorpUpdate from './corp-update';
import CorpDeleteDialog from './corp-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CorpUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CorpUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CorpDetail} />
      <ErrorBoundaryRoute path={match.url} component={Corp} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CorpDeleteDialog} />
  </>
);

export default Routes;
