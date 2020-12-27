import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICorp } from 'app/shared/model/corp.model';
import { getEntities as getCorps } from 'app/entities/corp/corp.reducer';
import { getEntity, updateEntity, createEntity, reset } from './team-grp.reducer';
import { ITeamGrp } from 'app/shared/model/team-grp.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITeamGrpUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TeamGrpUpdate = (props: ITeamGrpUpdateProps) => {
  const [corpId, setCorpId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { teamGrpEntity, corps, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/team-grp' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCorps();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...teamGrpEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="crmMariaApp.teamGrp.home.createOrEditLabel">Create or edit a TeamGrp</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : teamGrpEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="team-grp-id">ID</Label>
                  <AvInput id="team-grp-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="teamCodeLabel" for="team-grp-teamCode">
                  Team Code
                </Label>
                <AvField id="team-grp-teamCode" type="text" name="teamCode" />
              </AvGroup>
              <AvGroup>
                <Label id="teamNameLabel" for="team-grp-teamName">
                  Team Name
                </Label>
                <AvField id="team-grp-teamName" type="text" name="teamName" />
              </AvGroup>
              <AvGroup>
                <Label id="useYnLabel" for="team-grp-useYn">
                  Use Yn
                </Label>
                <AvInput
                  id="team-grp-useYn"
                  type="select"
                  className="form-control"
                  name="useYn"
                  value={(!isNew && teamGrpEntity.useYn) || 'Y'}
                >
                  <option value="Y">Y</option>
                  <option value="N">N</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="team-grp-corp">Corp</Label>
                <AvInput id="team-grp-corp" type="select" className="form-control" name="corpId">
                  <option value="" key="0" />
                  {corps
                    ? corps.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/team-grp" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  corps: storeState.corp.entities,
  teamGrpEntity: storeState.teamGrp.entity,
  loading: storeState.teamGrp.loading,
  updating: storeState.teamGrp.updating,
  updateSuccess: storeState.teamGrp.updateSuccess,
});

const mapDispatchToProps = {
  getCorps,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TeamGrpUpdate);
