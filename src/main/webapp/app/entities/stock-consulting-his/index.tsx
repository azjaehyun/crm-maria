import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import StockConsultingHis from './stock-consulting-his';
import StockConsultingHisDetail from './stock-consulting-his-detail';
import StockConsultingHisUpdate from './stock-consulting-his-update';
import StockConsultingHisDeleteDialog from './stock-consulting-his-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={StockConsultingHisUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={StockConsultingHisUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={StockConsultingHisDetail} />
      <ErrorBoundaryRoute path={match.url} component={StockConsultingHis} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={StockConsultingHisDeleteDialog} />
  </>
);

export default Routes;
