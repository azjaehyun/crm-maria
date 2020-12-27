import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TeamGrp from './team-grp';
import TeamGrpDetail from './team-grp-detail';
import TeamGrpUpdate from './team-grp-update';
import TeamGrpDeleteDialog from './team-grp-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TeamGrpUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TeamGrpUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TeamGrpDetail} />
      <ErrorBoundaryRoute path={match.url} component={TeamGrp} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TeamGrpDeleteDialog} />
  </>
);

export default Routes;
