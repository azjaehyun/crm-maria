import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MemoHis from './memo-his';
import MemoHisDetail from './memo-his-detail';
import MemoHisUpdate from './memo-his-update';
import MemoHisDeleteDialog from './memo-his-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MemoHisUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MemoHisUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MemoHisDetail} />
      <ErrorBoundaryRoute path={match.url} component={MemoHis} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MemoHisDeleteDialog} />
  </>
);

export default Routes;
