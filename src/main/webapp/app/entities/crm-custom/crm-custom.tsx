import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './crm-custom.reducer';
import { ICrmCustom } from 'app/shared/model/crm-custom.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface ICrmCustomProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const CrmCustom = (props: ICrmCustomProps) => {
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

  const { crmCustomList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="crm-custom-heading">
        Crm Customs
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Create new Crm Custom
        </Link>
      </h2>
      <div className="table-responsive">
        {crmCustomList && crmCustomList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('corpCode')}>
                  Corp Code <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('phoneNum')}>
                  Phone Num <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fiveDayfreeYn')}>
                  Five Dayfree Yn <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('salesStatus')}>
                  Sales Status <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('smsReceptionYn')}>
                  Sms Reception Yn <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('callStatus')}>
                  Call Status <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('customStatus')}>
                  Custom Status <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tempOneStatus')}>
                  Temp One Status <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tempTwoStatus')}>
                  Temp Two Status <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dbInsertType')}>
                  Db Insert Type <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('useYn')}>
                  Use Yn <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Manager <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Tm Manager <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {crmCustomList.map((crmCustom, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${crmCustom.id}`} color="link" size="sm">
                      {crmCustom.id}
                    </Button>
                  </td>
                  <td>{crmCustom.corpCode}</td>
                  <td>{crmCustom.phoneNum}</td>
                  <td>{crmCustom.fiveDayfreeYn}</td>
                  <td>{crmCustom.salesStatus}</td>
                  <td>{crmCustom.smsReceptionYn}</td>
                  <td>{crmCustom.callStatus}</td>
                  <td>{crmCustom.customStatus}</td>
                  <td>{crmCustom.tempOneStatus}</td>
                  <td>{crmCustom.tempTwoStatus}</td>
                  <td>{crmCustom.dbInsertType}</td>
                  <td>{crmCustom.useYn}</td>
                  <td>{crmCustom.managerId ? <Link to={`manager/${crmCustom.managerId}`}>{crmCustom.managerId}</Link> : ''}</td>
                  <td>{crmCustom.tmManagerId ? <Link to={`tm-manager/${crmCustom.tmManagerId}`}>{crmCustom.tmManagerId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${crmCustom.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${crmCustom.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${crmCustom.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Crm Customs found</div>
        )}
      </div>
      {props.totalItems ? (
        <div className={crmCustomList && crmCustomList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ crmCustom }: IRootState) => ({
  crmCustomList: crmCustom.entities,
  loading: crmCustom.loading,
  totalItems: crmCustom.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CrmCustom);
