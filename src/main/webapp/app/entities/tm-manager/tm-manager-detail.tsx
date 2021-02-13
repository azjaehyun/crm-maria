import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './tm-manager.reducer';
import { ITmManager } from 'app/shared/model/tm-manager.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITmManagerDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TmManagerDetail = (props: ITmManagerDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { tmManagerEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          TmManager [<b>{tmManagerEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="corpCode">Corp Code</span>
          </dt>
          <dd>{tmManagerEntity.corpCode}</dd>
          <dt>
            <span id="tmManagerName">Tm Manager Name</span>
          </dt>
          <dd>{tmManagerEntity.tmManagerName}</dd>
          <dt>
            <span id="tmManagerPhoneNum">Tm Manager Phone Num</span>
          </dt>
          <dd>{tmManagerEntity.tmManagerPhoneNum}</dd>
          <dt>
            <span id="teamCode">Team Code</span>
          </dt>
          <dd>{tmManagerEntity.teamCode}</dd>
          <dt>
            <span id="crmManageCnt">Crm Manage Cnt</span>
          </dt>
          <dd>{tmManagerEntity.crmManageCnt}</dd>
          <dt>
            <span id="useYn">Use Yn</span>
          </dt>
          <dd>{tmManagerEntity.useYn}</dd>
          <dt>Team</dt>
          <dd>{tmManagerEntity.teamId ? tmManagerEntity.teamId : ''}</dd>
        </dl>
        <Button tag={Link} to="/tm-manager" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tm-manager/${tmManagerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ tmManager }: IRootState) => ({
  tmManagerEntity: tmManager.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TmManagerDetail);
