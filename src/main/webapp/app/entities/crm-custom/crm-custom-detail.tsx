import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './crm-custom.reducer';
import { ICrmCustom } from 'app/shared/model/crm-custom.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICrmCustomDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CrmCustomDetail = (props: ICrmCustomDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { crmCustomEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          CrmCustom [<b>{crmCustomEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="corpCode">Corp Code</span>
          </dt>
          <dd>{crmCustomEntity.corpCode}</dd>
          <dt>
            <span id="phoneNum">Phone Num</span>
          </dt>
          <dd>{crmCustomEntity.phoneNum}</dd>
          <dt>
            <span id="fiveDayfreeYn">Five Dayfree Yn</span>
          </dt>
          <dd>{crmCustomEntity.fiveDayfreeYn}</dd>
          <dt>
            <span id="salesStatus">Sales Status</span>
          </dt>
          <dd>{crmCustomEntity.salesStatus}</dd>
          <dt>
            <span id="smsReceptionYn">Sms Reception Yn</span>
            <UncontrolledTooltip target="smsReceptionYn">PAY , FIVEDAYFREE , STANDBY , BLACKLIST , OUT</UncontrolledTooltip>
          </dt>
          <dd>{crmCustomEntity.smsReceptionYn}</dd>
          <dt>
            <span id="callStatus">Call Status</span>
          </dt>
          <dd>{crmCustomEntity.callStatus}</dd>
          <dt>
            <span id="customStatus">Custom Status</span>
            <UncontrolledTooltip target="customStatus">가망 , 차단 , 거절 , 재통화 , 배팅 ,</UncontrolledTooltip>
          </dt>
          <dd>{crmCustomEntity.customStatus}</dd>
          <dt>
            <span id="tempOneStatus">Temp One Status</span>
          </dt>
          <dd>{crmCustomEntity.tempOneStatus}</dd>
          <dt>
            <span id="tempTwoStatus">Temp Two Status</span>
          </dt>
          <dd>{crmCustomEntity.tempTwoStatus}</dd>
          <dt>
            <span id="dbInsertType">Db Insert Type</span>
          </dt>
          <dd>{crmCustomEntity.dbInsertType}</dd>
          <dt>
            <span id="useYn">Use Yn</span>
            <UncontrolledTooltip target="useYn">dbInsertType</UncontrolledTooltip>
          </dt>
          <dd>{crmCustomEntity.useYn}</dd>
          <dt>Manager</dt>
          <dd>{crmCustomEntity.managerId ? crmCustomEntity.managerId : ''}</dd>
          <dt>Tm Manager</dt>
          <dd>{crmCustomEntity.tmManagerId ? crmCustomEntity.tmManagerId : ''}</dd>
        </dl>
        <Button tag={Link} to="/crm-custom" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/crm-custom/${crmCustomEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ crmCustom }: IRootState) => ({
  crmCustomEntity: crmCustom.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CrmCustomDetail);
