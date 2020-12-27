import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './stock-consulting-his.reducer';
import { IStockConsultingHis } from 'app/shared/model/stock-consulting-his.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IStockConsultingHisDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const StockConsultingHisDetail = (props: IStockConsultingHisDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { stockConsultingHisEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          StockConsultingHis [<b>{stockConsultingHisEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="consultingMemo">Consulting Memo</span>
          </dt>
          <dd>{stockConsultingHisEntity.consultingMemo}</dd>
          <dt>
            <span id="regDtm">Reg Dtm</span>
          </dt>
          <dd>
            {stockConsultingHisEntity.regDtm ? (
              <TextFormat value={stockConsultingHisEntity.regDtm} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="useYn">Use Yn</span>
          </dt>
          <dd>{stockConsultingHisEntity.useYn}</dd>
          <dt>Crm Custom</dt>
          <dd>{stockConsultingHisEntity.crmCustomId ? stockConsultingHisEntity.crmCustomId : ''}</dd>
        </dl>
        <Button tag={Link} to="/stock-consulting-his" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/stock-consulting-his/${stockConsultingHisEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ stockConsultingHis }: IRootState) => ({
  stockConsultingHisEntity: stockConsultingHis.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(StockConsultingHisDetail);
