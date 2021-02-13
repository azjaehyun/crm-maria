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
import { getEntity, updateEntity, createEntity, reset } from './send-sms-his.reducer';
import { ISendSmsHis } from 'app/shared/model/send-sms-his.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISendSmsHisUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SendSmsHisUpdate = (props: ISendSmsHisUpdateProps) => {
  const [crmCustomId, setCrmCustomId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { sendSmsHisEntity, crmCustoms, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/send-sms-his' + props.location.search);
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
        ...sendSmsHisEntity,
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
          <h2 id="crmMariaApp.sendSmsHis.home.createOrEditLabel">Create or edit a SendSmsHis</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : sendSmsHisEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="send-sms-his-id">ID</Label>
                  <AvInput id="send-sms-his-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="sendDtmLabel" for="send-sms-his-sendDtm">
                  Send Dtm
                </Label>
                <AvField id="send-sms-his-sendDtm" type="date" className="form-control" name="sendDtm" />
              </AvGroup>
              <AvGroup>
                <Label id="fromPhoneNumLabel" for="send-sms-his-fromPhoneNum">
                  From Phone Num
                </Label>
                <AvField id="send-sms-his-fromPhoneNum" type="text" name="fromPhoneNum" />
              </AvGroup>
              <AvGroup>
                <Label id="toPhoneNumLabel" for="send-sms-his-toPhoneNum">
                  To Phone Num
                </Label>
                <AvField id="send-sms-his-toPhoneNum" type="text" name="toPhoneNum" />
              </AvGroup>
              <AvGroup>
                <Label id="useYnLabel" for="send-sms-his-useYn">
                  Use Yn
                </Label>
                <AvInput
                  id="send-sms-his-useYn"
                  type="select"
                  className="form-control"
                  name="useYn"
                  value={(!isNew && sendSmsHisEntity.useYn) || 'Y'}
                >
                  <option value="Y">Y</option>
                  <option value="N">N</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="send-sms-his-crmCustom">Crm Custom</Label>
                <AvInput id="send-sms-his-crmCustom" type="select" className="form-control" name="crmCustomId">
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
              <Button tag={Link} id="cancel-save" to="/send-sms-his" replace color="info">
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
  sendSmsHisEntity: storeState.sendSmsHis.entity,
  loading: storeState.sendSmsHis.loading,
  updating: storeState.sendSmsHis.updating,
  updateSuccess: storeState.sendSmsHis.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(SendSmsHisUpdate);
