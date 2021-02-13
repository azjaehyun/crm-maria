import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import StockContractHis from './stock-contract-his';
import StockContractHisDetail from './stock-contract-his-detail';
import StockContractHisUpdate from './stock-contract-his-update';
import StockContractHisDeleteDialog from './stock-contract-his-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={StockContractHisUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={StockContractHisUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={StockContractHisDetail} />
      <ErrorBoundaryRoute path={match.url} component={StockContractHis} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={StockContractHisDeleteDialog} />
  </>
);

export default Routes;
