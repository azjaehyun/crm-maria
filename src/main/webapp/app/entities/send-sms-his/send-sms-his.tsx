import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './send-sms-his.reducer';
import { ISendSmsHis } from 'app/shared/model/send-sms-his.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface ISendSmsHisProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const SendSmsHis = (props: ISendSmsHisProps) => {
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

  const { sendSmsHisList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="send-sms-his-heading">
        Send Sms His
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Create new Send Sms His
        </Link>
      </h2>
      <div className="table-responsive">
        {sendSmsHisList && sendSmsHisList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('sendDtm')}>
                  Send Dtm <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fromPhoneNum')}>
                  From Phone Num <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('toPhoneNum')}>
                  To Phone Num <FontAwesomeIcon icon="sort" />
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
              {sendSmsHisList.map((sendSmsHis, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${sendSmsHis.id}`} color="link" size="sm">
                      {sendSmsHis.id}
                    </Button>
                  </td>
                  <td>
                    {sendSmsHis.sendDtm ? <TextFormat type="date" value={sendSmsHis.sendDtm} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{sendSmsHis.fromPhoneNum}</td>
                  <td>{sendSmsHis.toPhoneNum}</td>
                  <td>{sendSmsHis.useYn}</td>
                  <td>{sendSmsHis.crmCustomId ? <Link to={`crm-custom/${sendSmsHis.crmCustomId}`}>{sendSmsHis.crmCustomId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${sendSmsHis.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${sendSmsHis.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${sendSmsHis.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Send Sms His found</div>
        )}
      </div>
      {props.totalItems ? (
        <div className={sendSmsHisList && sendSmsHisList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ sendSmsHis }: IRootState) => ({
  sendSmsHisList: sendSmsHis.entities,
  loading: sendSmsHis.loading,
  totalItems: sendSmsHis.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SendSmsHis);
