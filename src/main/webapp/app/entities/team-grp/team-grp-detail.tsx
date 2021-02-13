import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './team-grp.reducer';
import { ITeamGrp } from 'app/shared/model/team-grp.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITeamGrpDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TeamGrpDetail = (props: ITeamGrpDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { teamGrpEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          TeamGrp [<b>{teamGrpEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="teamCode">Team Code</span>
          </dt>
          <dd>{teamGrpEntity.teamCode}</dd>
          <dt>
            <span id="teamName">Team Name</span>
          </dt>
          <dd>{teamGrpEntity.teamName}</dd>
          <dt>
            <span id="useYn">Use Yn</span>
          </dt>
          <dd>{teamGrpEntity.useYn}</dd>
          <dt>Corp</dt>
          <dd>{teamGrpEntity.corpId ? teamGrpEntity.corpId : ''}</dd>
        </dl>
        <Button tag={Link} to="/team-grp" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/team-grp/${teamGrpEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ teamGrp }: IRootState) => ({
  teamGrpEntity: teamGrp.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TeamGrpDetail);
