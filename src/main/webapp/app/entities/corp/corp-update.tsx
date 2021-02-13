import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './corp.reducer';
import { ICorp } from 'app/shared/model/corp.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICorpUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CorpUpdate = (props: ICorpUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { corpEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/corp' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...corpEntity,
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
          <h2 id="crmMariaApp.corp.home.createOrEditLabel">Create or edit a Corp</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : corpEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="corp-id">ID</Label>
                  <AvInput id="corp-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="corpCodeLabel" for="corp-corpCode">
                  Corp Code
                </Label>
                <AvField
                  id="corp-corpCode"
                  type="text"
                  name="corpCode"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="corpNameLabel" for="corp-corpName">
                  Corp Name
                </Label>
                <AvField
                  id="corp-corpName"
                  type="text"
                  name="corpName"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="useYnLabel" for="corp-useYn">
                  Use Yn
                </Label>
                <AvInput id="corp-useYn" type="select" className="form-control" name="useYn" value={(!isNew && corpEntity.useYn) || 'Y'}>
                  <option value="Y">Y</option>
                  <option value="N">N</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/corp" replace color="info">
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
  corpEntity: storeState.corp.entity,
  loading: storeState.corp.loading,
  updating: storeState.corp.updating,
  updateSuccess: storeState.corp.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CorpUpdate);
