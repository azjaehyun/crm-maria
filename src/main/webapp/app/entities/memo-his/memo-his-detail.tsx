import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './memo-his.reducer';
import { IMemoHis } from 'app/shared/model/memo-his.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMemoHisDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MemoHisDetail = (props: IMemoHisDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { memoHisEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          MemoHis [<b>{memoHisEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="memoContent">Memo Content</span>
          </dt>
          <dd>{memoHisEntity.memoContent}</dd>
          <dt>
            <span id="regDtm">Reg Dtm</span>
          </dt>
          <dd>{memoHisEntity.regDtm ? <TextFormat value={memoHisEntity.regDtm} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="useYn">Use Yn</span>
          </dt>
          <dd>{memoHisEntity.useYn}</dd>
          <dt>Crm Custom</dt>
          <dd>{memoHisEntity.crmCustomId ? memoHisEntity.crmCustomId : ''}</dd>
        </dl>
        <Button tag={Link} to="/memo-his" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/memo-his/${memoHisEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ memoHis }: IRootState) => ({
  memoHisEntity: memoHis.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MemoHisDetail);
