import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './stock-contract-his.reducer';
import { IStockContractHis } from 'app/shared/model/stock-contract-his.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IStockContractHisProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const StockContractHis = (props: IStockContractHisProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get('sort');
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const { stockContractHisList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="stock-contract-his-heading">
        Stock Contract His
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Create new Stock Contract His
        </Link>
      </h2>
      <div className="table-responsive">
        {stockContractHisList && stockContractHisList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fromContractDt')}>
                  From Contract Dt <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('toContractDt')}>
                  To Contract Dt <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('contractAmount')}>
                  Contract Amount <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('regDtm')}>
                  Reg Dtm <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('useYn')}>
                  Use Yn <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Crm Custom <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {stockContractHisList.map((stockContractHis, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${stockContractHis.id}`} color="link" size="sm">
                      {stockContractHis.id}
                    </Button>
                  </td>
                  <td>
                    {stockContractHis.fromContractDt ? (
                      <TextFormat type="date" value={stockContractHis.fromContractDt} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {stockContractHis.toContractDt ? (
                      <TextFormat type="date" value={stockContractHis.toContractDt} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{stockContractHis.contractAmount}</td>
                  <td>
                    {stockContractHis.regDtm ? (
                      <TextFormat type="date" value={stockContractHis.regDtm} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{stockContractHis.useYn}</td>
                  <td>
                    {stockContractHis.crmCustomId ? (
                      <Link to={`crm-custom/${stockContractHis.crmCustomId}`}>{stockContractHis.crmCustomId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${stockContractHis.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${stockContractHis.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${stockContractHis.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Stock Contract His found</div>
        )}
      </div>
      {props.totalItems ? (
        <div className={stockContractHisList && stockContractHisList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={props.totalItems}
            />
          </Row>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

const mapStateToProps = ({ stockContractHis }: IRootState) => ({
  stockContractHisList: stockContractHis.entities,
  loading: stockContractHis.loading,
  totalItems: stockContractHis.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(StockContractHis);
