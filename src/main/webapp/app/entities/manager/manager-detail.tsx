import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './manager.reducer';
import { IManager } from 'app/shared/model/manager.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IManagerDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ManagerDetail = (props: IManagerDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { managerEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Manager [<b>{managerEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="corpCode">Corp Code</span>
          </dt>
          <dd>{managerEntity.corpCode}</dd>
          <dt>
            <span id="managerName">Manager Name</span>
          </dt>
          <dd>{managerEntity.managerName}</dd>
          <dt>
            <span id="managerPhoneNum">Manager Phone Num</span>
          </dt>
          <dd>{managerEntity.managerPhoneNum}</dd>
          <dt>
            <span id="teamCode">Team Code</span>
          </dt>
          <dd>{managerEntity.teamCode}</dd>
          <dt>
            <span id="totalSalesAmount">Total Sales Amount</span>
          </dt>
          <dd>{managerEntity.totalSalesAmount}</dd>
          <dt>
            <span id="enterDay">Enter Day</span>
          </dt>
          <dd>
            {managerEntity.enterDay ? <TextFormat value={managerEntity.enterDay} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="outDay">Out Day</span>
          </dt>
          <dd>{managerEntity.outDay ? <TextFormat value={managerEntity.outDay} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="useYn">Use Yn</span>
          </dt>
          <dd>{managerEntity.useYn}</dd>
          <dt>Team</dt>
          <dd>{managerEntity.teamId ? managerEntity.teamId : ''}</dd>
        </dl>
        <Button tag={Link} to="/manager" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/manager/${managerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ manager }: IRootState) => ({
  managerEntity: manager.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ManagerDetail);
