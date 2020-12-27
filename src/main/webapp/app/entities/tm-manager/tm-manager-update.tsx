import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITeamGrp } from 'app/shared/model/team-grp.model';
import { getEntities as getTeamGrps } from 'app/entities/team-grp/team-grp.reducer';
import { getEntity, updateEntity, createEntity, reset } from './tm-manager.reducer';
import { ITmManager } from 'app/shared/model/tm-manager.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITmManagerUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TmManagerUpdate = (props: ITmManagerUpdateProps) => {
  const [teamId, setTeamId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { tmManagerEntity, teamGrps, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/tm-manager' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getTeamGrps();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...tmManagerEntity,
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
          <h2 id="crmMariaApp.tmManager.home.createOrEditLabel">Create or edit a TmManager</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : tmManagerEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="tm-manager-id">ID</Label>
                  <AvInput id="tm-manager-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="corpCodeLabel" for="tm-manager-corpCode">
                  Corp Code
                </Label>
                <AvField id="tm-manager-corpCode" type="text" name="corpCode" />
              </AvGroup>
              <AvGroup>
                <Label id="tmManagerNameLabel" for="tm-manager-tmManagerName">
                  Tm Manager Name
                </Label>
                <AvField
                  id="tm-manager-tmManagerName"
                  type="text"
                  name="tmManagerName"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="tmManagerPhoneNumLabel" for="tm-manager-tmManagerPhoneNum">
                  Tm Manager Phone Num
                </Label>
                <AvField id="tm-manager-tmManagerPhoneNum" type="text" name="tmManagerPhoneNum" />
              </AvGroup>
              <AvGroup>
                <Label id="teamCodeLabel" for="tm-manager-teamCode">
                  Team Code
                </Label>
                <AvField id="tm-manager-teamCode" type="text" name="teamCode" />
              </AvGroup>
              <AvGroup>
                <Label id="crmManageCntLabel" for="tm-manager-crmManageCnt">
                  Crm Manage Cnt
                </Label>
                <AvField id="tm-manager-crmManageCnt" type="string" className="form-control" name="crmManageCnt" />
              </AvGroup>
              <AvGroup>
                <Label id="useYnLabel" for="tm-manager-useYn">
                  Use Yn
                </Label>
                <AvInput
                  id="tm-manager-useYn"
                  type="select"
                  className="form-control"
                  name="useYn"
                  value={(!isNew && tmManagerEntity.useYn) || 'Y'}
                >
                  <option value="Y">Y</option>
                  <option value="N">N</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="tm-manager-team">Team</Label>
                <AvInput id="tm-manager-team" type="select" className="form-control" name="teamId">
                  <option value="" key="0" />
                  {teamGrps
                    ? teamGrps.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/tm-manager" replace color="info">
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
  teamGrps: storeState.teamGrp.entities,
  tmManagerEntity: storeState.tmManager.entity,
  loading: storeState.tmManager.loading,
  updating: storeState.tmManager.updating,
  updateSuccess: storeState.tmManager.updateSuccess,
});

const mapDispatchToProps = {
  getTeamGrps,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TmManagerUpdate);
