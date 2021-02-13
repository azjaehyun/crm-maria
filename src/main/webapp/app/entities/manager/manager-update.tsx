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
import { getEntity, updateEntity, createEntity, reset } from './manager.reducer';
import { IManager } from 'app/shared/model/manager.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IManagerUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ManagerUpdate = (props: IManagerUpdateProps) => {
  const [teamId, setTeamId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { managerEntity, teamGrps, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/manager' + props.location.search);
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
        ...managerEntity,
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
          <h2 id="crmMariaApp.manager.home.createOrEditLabel">Create or edit a Manager</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : managerEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="manager-id">ID</Label>
                  <AvInput id="manager-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="corpCodeLabel" for="manager-corpCode">
                  Corp Code
                </Label>
                <AvField id="manager-corpCode" type="text" name="corpCode" />
              </AvGroup>
              <AvGroup>
                <Label id="managerNameLabel" for="manager-managerName">
                  Manager Name
                </Label>
                <AvField
                  id="manager-managerName"
                  type="text"
                  name="managerName"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="managerPhoneNumLabel" for="manager-managerPhoneNum">
                  Manager Phone Num
                </Label>
                <AvField id="manager-managerPhoneNum" type="text" name="managerPhoneNum" />
              </AvGroup>
              <AvGroup>
                <Label id="teamCodeLabel" for="manager-teamCode">
                  Team Code
                </Label>
                <AvField id="manager-teamCode" type="text" name="teamCode" />
              </AvGroup>
              <AvGroup>
                <Label id="totalSalesAmountLabel" for="manager-totalSalesAmount">
                  Total Sales Amount
                </Label>
                <AvField id="manager-totalSalesAmount" type="string" className="form-control" name="totalSalesAmount" />
              </AvGroup>
              <AvGroup>
                <Label id="enterDayLabel" for="manager-enterDay">
                  Enter Day
                </Label>
                <AvField id="manager-enterDay" type="date" className="form-control" name="enterDay" />
              </AvGroup>
              <AvGroup>
                <Label id="outDayLabel" for="manager-outDay">
                  Out Day
                </Label>
                <AvField id="manager-outDay" type="date" className="form-control" name="outDay" />
              </AvGroup>
              <AvGroup>
                <Label id="useYnLabel" for="manager-useYn">
                  Use Yn
                </Label>
                <AvInput
                  id="manager-useYn"
                  type="select"
                  className="form-control"
                  name="useYn"
                  value={(!isNew && managerEntity.useYn) || 'Y'}
                >
                  <option value="Y">Y</option>
                  <option value="N">N</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="manager-team">Team</Label>
                <AvInput id="manager-team" type="select" className="form-control" name="teamId">
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
              <Button tag={Link} id="cancel-save" to="/manager" replace color="info">
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
  managerEntity: storeState.manager.entity,
  loading: storeState.manager.loading,
  updating: storeState.manager.updating,
  updateSuccess: storeState.manager.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(ManagerUpdate);
