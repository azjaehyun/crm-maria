import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICorp, defaultValue } from 'app/shared/model/corp.model';

export const ACTION_TYPES = {
  FETCH_CORP_LIST: 'corp/FETCH_CORP_LIST',
  FETCH_CORP: 'corp/FETCH_CORP',
  CREATE_CORP: 'corp/CREATE_CORP',
  UPDATE_CORP: 'corp/UPDATE_CORP',
  DELETE_CORP: 'corp/DELETE_CORP',
  RESET: 'corp/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICorp>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type CorpState = Readonly<typeof initialState>;

// Reducer

export default (state: CorpState = initialState, action): CorpState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CORP_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CORP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CORP):
    case REQUEST(ACTION_TYPES.UPDATE_CORP):
    case REQUEST(ACTION_TYPES.DELETE_CORP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CORP_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CORP):
    case FAILURE(ACTION_TYPES.CREATE_CORP):
    case FAILURE(ACTION_TYPES.UPDATE_CORP):
    case FAILURE(ACTION_TYPES.DELETE_CORP):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CORP_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CORP):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CORP):
    case SUCCESS(ACTION_TYPES.UPDATE_CORP):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CORP):
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

const apiUrl = 'api/corps';

// Actions

export const getEntities: ICrudGetAllAction<ICorp> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CORP_LIST,
    payload: axios.get<ICorp>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ICorp> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CORP,
    payload: axios.get<ICorp>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICorp> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CORP,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICorp> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CORP,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICorp> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CORP,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
