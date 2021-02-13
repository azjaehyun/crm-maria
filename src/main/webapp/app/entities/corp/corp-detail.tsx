import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './corp.reducer';
import { ICorp } from 'app/shared/model/corp.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICorpDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CorpDetail = (props: ICorpDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { corpEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Corp [<b>{corpEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="corpCode">Corp Code</span>
          </dt>
          <dd>{corpEntity.corpCode}</dd>
          <dt>
            <span id="corpName">Corp Name</span>
          </dt>
          <dd>{corpEntity.corpName}</dd>
          <dt>
            <span id="useYn">Use Yn</span>
          </dt>
          <dd>{corpEntity.useYn}</dd>
        </dl>
        <Button tag={Link} to="/corp" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/corp/${corpEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ corp }: IRootState) => ({
  corpEntity: corp.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CorpDetail);
