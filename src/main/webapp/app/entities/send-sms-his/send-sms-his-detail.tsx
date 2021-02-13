import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './send-sms-his.reducer';
import { ISendSmsHis } from 'app/shared/model/send-sms-his.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISendSmsHisDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SendSmsHisDetail = (props: ISendSmsHisDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { sendSmsHisEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          SendSmsHis [<b>{sendSmsHisEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="sendDtm">Send Dtm</span>
          </dt>
          <dd>
            {sendSmsHisEntity.sendDtm ? <TextFormat value={sendSmsHisEntity.sendDtm} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="fromPhoneNum">From Phone Num</span>
          </dt>
          <dd>{sendSmsHisEntity.fromPhoneNum}</dd>
          <dt>
            <span id="toPhoneNum">To Phone Num</span>
          </dt>
          <dd>{sendSmsHisEntity.toPhoneNum}</dd>
          <dt>
            <span id="useYn">Use Yn</span>
          </dt>
          <dd>{sendSmsHisEntity.useYn}</dd>
          <dt>Crm Custom</dt>
          <dd>{sendSmsHisEntity.crmCustomId ? sendSmsHisEntity.crmCustomId : ''}</dd>
        </dl>
        <Button tag={Link} to="/send-sms-his" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/send-sms-his/${sendSmsHisEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ sendSmsHis }: IRootState) => ({
  sendSmsHisEntity: sendSmsHis.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SendSmsHisDetail);
