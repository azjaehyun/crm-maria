import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITeamGrp, defaultValue } from 'app/shared/model/team-grp.model';

export const ACTION_TYPES = {
  FETCH_TEAMGRP_LIST: 'teamGrp/FETCH_TEAMGRP_LIST',
  FETCH_TEAMGRP: 'teamGrp/FETCH_TEAMGRP',
  CREATE_TEAMGRP: 'teamGrp/CREATE_TEAMGRP',
  UPDATE_TEAMGRP: 'teamGrp/UPDATE_TEAMGRP',
  DELETE_TEAMGRP: 'teamGrp/DELETE_TEAMGRP',
  RESET: 'teamGrp/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITeamGrp>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type TeamGrpState = Readonly<typeof initialState>;

// Reducer

export default (state: TeamGrpState = initialState, action): TeamGrpState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TEAMGRP_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TEAMGRP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_TEAMGRP):
    case REQUEST(ACTION_TYPES.UPDATE_TEAMGRP):
    case REQUEST(ACTION_TYPES.DELETE_TEAMGRP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_TEAMGRP_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TEAMGRP):
    case FAILURE(ACTION_TYPES.CREATE_TEAMGRP):
    case FAILURE(ACTION_TYPES.UPDATE_TEAMGRP):
    case FAILURE(ACTION_TYPES.DELETE_TEAMGRP):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TEAMGRP_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_TEAMGRP):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_TEAMGRP):
    case SUCCESS(ACTION_TYPES.UPDATE_TEAMGRP):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_TEAMGRP):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/team-grps';

// Actions

export const getEntities: ICrudGetAllAction<ITeamGrp> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TEAMGRP_LIST,
    payload: axios.get<ITeamGrp>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ITeamGrp> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TEAMGRP,
    payload: axios.get<ITeamGrp>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ITeamGrp> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TEAMGRP,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITeamGrp> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TEAMGRP,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITeamGrp> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TEAMGRP,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
