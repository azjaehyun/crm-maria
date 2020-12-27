import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICrmCustom, defaultValue } from 'app/shared/model/crm-custom.model';

export const ACTION_TYPES = {
  FETCH_CRMCUSTOM_LIST: 'crmCustom/FETCH_CRMCUSTOM_LIST',
  FETCH_CRMCUSTOM: 'crmCustom/FETCH_CRMCUSTOM',
  CREATE_CRMCUSTOM: 'crmCustom/CREATE_CRMCUSTOM',
  UPDATE_CRMCUSTOM: 'crmCustom/UPDATE_CRMCUSTOM',
  DELETE_CRMCUSTOM: 'crmCustom/DELETE_CRMCUSTOM',
  RESET: 'crmCustom/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICrmCustom>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type CrmCustomState = Readonly<typeof initialState>;

// Reducer

export default (state: CrmCustomState = initialState, action): CrmCustomState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CRMCUSTOM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CRMCUSTOM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CRMCUSTOM):
    case REQUEST(ACTION_TYPES.UPDATE_CRMCUSTOM):
    case REQUEST(ACTION_TYPES.DELETE_CRMCUSTOM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CRMCUSTOM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CRMCUSTOM):
    case FAILURE(ACTION_TYPES.CREATE_CRMCUSTOM):
    case FAILURE(ACTION_TYPES.UPDATE_CRMCUSTOM):
    case FAILURE(ACTION_TYPES.DELETE_CRMCUSTOM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CRMCUSTOM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CRMCUSTOM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CRMCUSTOM):
    case SUCCESS(ACTION_TYPES.UPDATE_CRMCUSTOM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CRMCUSTOM):
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

const apiUrl = 'api/crm-customs';

// Actions

export const getEntities: ICrudGetAllAction<ICrmCustom> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CRMCUSTOM_LIST,
    payload: axios.get<ICrmCustom>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ICrmCustom> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CRMCUSTOM,
    payload: axios.get<ICrmCustom>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICrmCustom> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CRMCUSTOM,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICrmCustom> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CRMCUSTOM,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICrmCustom> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CRMCUSTOM,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
