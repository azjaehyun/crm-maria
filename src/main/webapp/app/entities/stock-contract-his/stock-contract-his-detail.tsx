import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './stock-contract-his.reducer';
import { IStockContractHis } from 'app/shared/model/stock-contract-his.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IStockContractHisDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const StockContractHisDetail = (props: IStockContractHisDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { stockContractHisEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          StockContractHis [<b>{stockContractHisEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="fromContractDt">From Contract Dt</span>
          </dt>
          <dd>
            {stockContractHisEntity.fromContractDt ? (
              <TextFormat value={stockContractHisEntity.fromContractDt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="toContractDt">To Contract Dt</span>
          </dt>
          <dd>
            {stockContractHisEntity.toContractDt ? (
              <TextFormat value={stockContractHisEntity.toContractDt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="contractAmount">Contract Amount</span>
          </dt>
          <dd>{stockContractHisEntity.contractAmount}</dd>
          <dt>
            <span id="regDtm">Reg Dtm</span>
          </dt>
          <dd>
            {stockContractHisEntity.regDtm ? (
              <TextFormat value={stockContractHisEntity.regDtm} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="useYn">Use Yn</span>
          </dt>
          <dd>{stockContractHisEntity.useYn}</dd>
          <dt>Crm Custom</dt>
          <dd>{stockContractHisEntity.crmCustomId ? stockContractHisEntity.crmCustomId : ''}</dd>
        </dl>
        <Button tag={Link} to="/stock-contract-his" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/stock-contract-his/${stockContractHisEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ stockContractHis }: IRootState) => ({
  stockContractHisEntity: stockContractHis.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(StockContractHisDetail);
