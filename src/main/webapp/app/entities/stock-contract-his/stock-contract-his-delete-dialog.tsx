import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IStockContractHis } from 'app/shared/model/stock-contract-his.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './stock-contract-his.reducer';

export interface IStockContractHisDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const StockContractHisDeleteDialog = (props: IStockContractHisDeleteDialogProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const handleClose = () => {
    props.history.push('/stock-contract-his' + props.location.search);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const confirmDelete = () => {
    props.deleteEntity(props.stockContractHisEntity.id);
  };

  const { stockContractHisEntity } = props;
  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose}>Confirm delete operation</ModalHeader>
      <ModalBody id="crmMariaApp.stockContractHis.delete.question">Are you sure you want to delete this StockContractHis?</ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp; Cancel
        </Button>
        <Button id="jhi-confirm-delete-stockContractHis" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp; Delete
        </Button>
      </ModalFooter>
    </Modal>
  );
};

const mapStateToProps = ({ stockContractHis }: IRootState) => ({
  stockContractHisEntity: stockContractHis.entity,
  updateSuccess: stockContractHis.updateSuccess,
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(StockContractHisDeleteDialog);
