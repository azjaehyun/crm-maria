import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label, UncontrolledTooltip } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IManager } from 'app/shared/model/manager.model';
import { getEntities as getManagers } from 'app/entities/manager/manager.reducer';
import { ITmManager } from 'app/shared/model/tm-manager.model';
import { getEntities as getTmManagers } from 'app/entities/tm-manager/tm-manager.reducer';
import { getEntity, updateEntity, createEntity, reset } from './crm-custom.reducer';
import { ICrmCustom } from 'app/shared/model/crm-custom.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICrmCustomUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CrmCustomUpdate = (props: ICrmCustomUpdateProps) => {
  const [managerId, setManagerId] = useState('0');
  const [tmManagerId, setTmManagerId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { crmCustomEntity, managers, tmManagers, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/crm-custom' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getManagers();
    props.getTmManagers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...crmCustomEntity,
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
          <h2 id="crmMariaApp.crmCustom.home.createOrEditLabel">Create or edit a CrmCustom</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : crmCustomEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="crm-custom-id">ID</Label>
                  <AvInput id="crm-custom-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="corpCodeLabel" for="crm-custom-corpCode">
                  Corp Code
                </Label>
                <AvField
                  id="crm-custom-corpCode"
                  type="text"
                  name="corpCode"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="crmNameLabel" for="crm-custom-crmName">
                  Crm Name
                </Label>
                <AvField
                  id="crm-custom-crmName"
                  type="text"
                  name="crmName"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="phoneNumLabel" for="crm-custom-phoneNum">
                  Phone Num
                </Label>
                <AvField id="crm-custom-phoneNum" type="text" name="phoneNum" />
              </AvGroup>
              <AvGroup>
                <Label id="fiveDayfreeYnLabel" for="crm-custom-fiveDayfreeYn">
                  Five Dayfree Yn
                </Label>
                <AvField
                  id="crm-custom-fiveDayfreeYn"
                  type="text"
                  name="fiveDayfreeYn"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="salesStatusLabel" for="crm-custom-salesStatus">
                  Sales Status
                </Label>
                <AvInput
                  id="crm-custom-salesStatus"
                  type="select"
                  className="form-control"
                  name="salesStatus"
                  value={(!isNew && crmCustomEntity.salesStatus) || 'PAY'}
                >
                  <option value="PAY">PAY</option>
                  <option value="FIVEDAYFREE">FIVEDAYFREE</option>
                  <option value="STANDBY">STANDBY</option>
                  <option value="BLACKLIST">BLACKLIST</option>
                  <option value="OUT">OUT</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="smsReceptionYnLabel" for="crm-custom-smsReceptionYn">
                  Sms Reception Yn
                </Label>
                <AvInput
                  id="crm-custom-smsReceptionYn"
                  type="select"
                  className="form-control"
                  name="smsReceptionYn"
                  value={(!isNew && crmCustomEntity.smsReceptionYn) || 'Y'}
                >
                  <option value="Y">Y</option>
                  <option value="N">N</option>
                </AvInput>
                <UncontrolledTooltip target="smsReceptionYnLabel">PAY , FIVEDAYFREE , STANDBY , BLACKLIST , OUT</UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="callStatusLabel" for="crm-custom-callStatus">
                  Call Status
                </Label>
                <AvInput
                  id="crm-custom-callStatus"
                  type="select"
                  className="form-control"
                  name="callStatus"
                  value={(!isNew && crmCustomEntity.callStatus) || 'HOPE'}
                >
                  <option value="HOPE">HOPE</option>
                  <option value="REJECT">REJECT</option>
                  <option value="BLOCK">BLOCK</option>
                  <option value="RECALL">RECALL</option>
                  <option value="TRY">TRY</option>
                  <option value="FREENEED">FREENEED</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="customStatusLabel" for="crm-custom-customStatus">
                  Custom Status
                </Label>
                <AvInput
                  id="crm-custom-customStatus"
                  type="select"
                  className="form-control"
                  name="customStatus"
                  value={(!isNew && crmCustomEntity.customStatus) || 'BEST'}
                >
                  <option value="BEST">BEST</option>
                  <option value="NOMAl">NOMAl</option>
                  <option value="BAD">BAD</option>
                  <option value="WORST">WORST</option>
                </AvInput>
                <UncontrolledTooltip target="customStatusLabel">가망 , 차단 , 거절 , 재통화 , 배팅 ,</UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="tempOneStatusLabel" for="crm-custom-tempOneStatus">
                  Temp One Status
                </Label>
                <AvField id="crm-custom-tempOneStatus" type="text" name="tempOneStatus" />
              </AvGroup>
              <AvGroup>
                <Label id="tempTwoStatusLabel" for="crm-custom-tempTwoStatus">
                  Temp Two Status
                </Label>
                <AvField id="crm-custom-tempTwoStatus" type="text" name="tempTwoStatus" />
              </AvGroup>
              <AvGroup>
                <Label id="dbInsertTypeLabel" for="crm-custom-dbInsertType">
                  Db Insert Type
                </Label>
                <AvField id="crm-custom-dbInsertType" type="text" name="dbInsertType" />
              </AvGroup>
              <AvGroup>
                <Label id="useYnLabel" for="crm-custom-useYn">
                  Use Yn
                </Label>
                <AvInput
                  id="crm-custom-useYn"
                  type="select"
                  className="form-control"
                  name="useYn"
                  value={(!isNew && crmCustomEntity.useYn) || 'Y'}
                >
                  <option value="Y">Y</option>
                  <option value="N">N</option>
                </AvInput>
                <UncontrolledTooltip target="useYnLabel">dbInsertType</UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label for="crm-custom-manager">Manager</Label>
                <AvInput id="crm-custom-manager" type="select" className="form-control" name="managerId">
                  <option value="" key="0" />
                  {managers
                    ? managers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="crm-custom-tmManager">Tm Manager</Label>
                <AvInput id="crm-custom-tmManager" type="select" className="form-control" name="tmManagerId">
                  <option value="" key="0" />
                  {tmManagers
                    ? tmManagers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/crm-custom" replace color="info">
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
  managers: storeState.manager.entities,
  tmManagers: storeState.tmManager.entities,
  crmCustomEntity: storeState.crmCustom.entity,
  loading: storeState.crmCustom.loading,
  updating: storeState.crmCustom.updating,
  updateSuccess: storeState.crmCustom.updateSuccess,
});

const mapDispatchToProps = {
  getManagers,
  getTmManagers,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CrmCustomUpdate);
