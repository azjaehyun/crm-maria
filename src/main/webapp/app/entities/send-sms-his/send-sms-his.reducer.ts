import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISendSmsHis, defaultValue } from 'app/shared/model/send-sms-his.model';

export const ACTION_TYPES = {
  FETCH_SENDSMSHIS_LIST: 'sendSmsHis/FETCH_SENDSMSHIS_LIST',
  FETCH_SENDSMSHIS: 'sendSmsHis/FETCH_SENDSMSHIS',
  CREATE_SENDSMSHIS: 'sendSmsHis/CREATE_SENDSMSHIS',
  UPDATE_SENDSMSHIS: 'sendSmsHis/UPDATE_SENDSMSHIS',
  DELETE_SENDSMSHIS: 'sendSmsHis/DELETE_SENDSMSHIS',
  RESET: 'sendSmsHis/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISendSmsHis>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type SendSmsHisState = Readonly<typeof initialState>;

// Reducer

export default (state: SendSmsHisState = initialState, action): SendSmsHisState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SENDSMSHIS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SENDSMSHIS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SENDSMSHIS):
    case REQUEST(ACTION_TYPES.UPDATE_SENDSMSHIS):
    case REQUEST(ACTION_TYPES.DELETE_SENDSMSHIS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SENDSMSHIS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SENDSMSHIS):
    case FAILURE(ACTION_TYPES.CREATE_SENDSMSHIS):
    case FAILURE(ACTION_TYPES.UPDATE_SENDSMSHIS):
    case FAILURE(ACTION_TYPES.DELETE_SENDSMSHIS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SENDSMSHIS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_SENDSMSHIS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SENDSMSHIS):
    case SUCCESS(ACTION_TYPES.UPDATE_SENDSMSHIS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SENDSMSHIS):
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

const apiUrl = 'api/send-sms-his';

// Actions

export const getEntities: ICrudGetAllAction<ISendSmsHis> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SENDSMSHIS_LIST,
    payload: axios.get<ISendSmsHis>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ISendSmsHis> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SENDSMSHIS,
    payload: axios.get<ISendSmsHis>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISendSmsHis> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SENDSMSHIS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISendSmsHis> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SENDSMSHIS,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISendSmsHis> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SENDSMSHIS,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
