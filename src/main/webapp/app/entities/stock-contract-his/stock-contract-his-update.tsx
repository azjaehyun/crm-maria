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
import { getEntity, updateEntity, createEntity, reset } from './stock-contract-his.reducer';
import { IStockContractHis } from 'app/shared/model/stock-contract-his.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IStockContractHisUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const StockContractHisUpdate = (props: IStockContractHisUpdateProps) => {
  const [crmCustomId, setCrmCustomId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { stockContractHisEntity, crmCustoms, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/stock-contract-his' + props.location.search);
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
        ...stockContractHisEntity,
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
          <h2 id="crmMariaApp.stockContractHis.home.createOrEditLabel">Create or edit a StockContractHis</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : stockContractHisEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="stock-contract-his-id">ID</Label>
                  <AvInput id="stock-contract-his-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="fromContractDtLabel" for="stock-contract-his-fromContractDt">
                  From Contract Dt
                </Label>
                <AvField id="stock-contract-his-fromContractDt" type="date" className="form-control" name="fromContractDt" />
              </AvGroup>
              <AvGroup>
                <Label id="toContractDtLabel" for="stock-contract-his-toContractDt">
                  To Contract Dt
                </Label>
                <AvField id="stock-contract-his-toContractDt" type="date" className="form-control" name="toContractDt" />
              </AvGroup>
              <AvGroup>
                <Label id="contractAmountLabel" for="stock-contract-his-contractAmount">
                  Contract Amount
                </Label>
                <AvField id="stock-contract-his-contractAmount" type="string" className="form-control" name="contractAmount" />
              </AvGroup>
              <AvGroup>
                <Label id="regDtmLabel" for="stock-contract-his-regDtm">
                  Reg Dtm
                </Label>
                <AvField id="stock-contract-his-regDtm" type="date" className="form-control" name="regDtm" />
              </AvGroup>
              <AvGroup>
                <Label id="useYnLabel" for="stock-contract-his-useYn">
                  Use Yn
                </Label>
                <AvInput
                  id="stock-contract-his-useYn"
                  type="select"
                  className="form-control"
                  name="useYn"
                  value={(!isNew && stockContractHisEntity.useYn) || 'Y'}
                >
                  <option value="Y">Y</option>
                  <option value="N">N</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="stock-contract-his-crmCustom">Crm Custom</Label>
                <AvInput id="stock-contract-his-crmCustom" type="select" className="form-control" name="crmCustomId">
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
              <Button tag={Link} id="cancel-save" to="/stock-contract-his" replace color="info">
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
  stockContractHisEntity: storeState.stockContractHis.entity,
  loading: storeState.stockContractHis.loading,
  updating: storeState.stockContractHis.updating,
  updateSuccess: storeState.stockContractHis.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(StockContractHisUpdate);
