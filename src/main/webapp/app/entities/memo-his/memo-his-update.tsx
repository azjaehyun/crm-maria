import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICrmCustom } from 'app/shared/model/crm-custom.model';
import { getEntities as getCrmCustoms } from 'app/entities/crm-custom/crm-custom.reducer';
import { getEntity, updateEntity, createEntity, reset } from './memo-his.reducer';
import { IMemoHis } from 'app/shared/model/memo-his.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMemoHisUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MemoHisUpdate = (props: IMemoHisUpdateProps) => {
  const [crmCustomId, setCrmCustomId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { memoHisEntity, crmCustoms, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/memo-his' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCrmCustoms();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...memoHisEntity,
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
          <h2 id="crmMariaApp.memoHis.home.createOrEditLabel">Create or edit a MemoHis</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : memoHisEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="memo-his-id">ID</Label>
                  <AvInput id="memo-his-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="memoContentLabel" for="memo-his-memoContent">
                  Memo Content
                </Label>
                <AvField id="memo-his-memoContent" type="text" name="memoContent" />
              </AvGroup>
              <AvGroup>
                <Label id="regDtmLabel" for="memo-his-regDtm">
                  Reg Dtm
                </Label>
                <AvField id="memo-his-regDtm" type="date" className="form-control" name="regDtm" />
              </AvGroup>
              <AvGroup>
                <Label id="useYnLabel" for="memo-his-useYn">
                  Use Yn
                </Label>
                <AvInput
                  id="memo-his-useYn"
                  type="select"
                  className="form-control"
                  name="useYn"
                  value={(!isNew && memoHisEntity.useYn) || 'Y'}
                >
                  <option value="Y">Y</option>
                  <option value="N">N</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="memo-his-crmCustom">Crm Custom</Label>
                <AvInput id="memo-his-crmCustom" type="select" className="form-control" name="crmCustomId">
                  <option value="" key="0" />
                  {crmCustoms
                    ? crmCustoms.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/memo-his" replace color="info">
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
  crmCustoms: storeState.crmCustom.entities,
  memoHisEntity: storeState.memoHis.entity,
  loading: storeState.memoHis.loading,
  updating: storeState.memoHis.updating,
  updateSuccess: storeState.memoHis.updateSuccess,
});

const mapDispatchToProps = {
  getCrmCustoms,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MemoHisUpdate);
