import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMemoHis, defaultValue } from 'app/shared/model/memo-his.model';

export const ACTION_TYPES = {
  FETCH_MEMOHIS_LIST: 'memoHis/FETCH_MEMOHIS_LIST',
  FETCH_MEMOHIS: 'memoHis/FETCH_MEMOHIS',
  CREATE_MEMOHIS: 'memoHis/CREATE_MEMOHIS',
  UPDATE_MEMOHIS: 'memoHis/UPDATE_MEMOHIS',
  DELETE_MEMOHIS: 'memoHis/DELETE_MEMOHIS',
  RESET: 'memoHis/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMemoHis>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type MemoHisState = Readonly<typeof initialState>;

// Reducer

export default (state: MemoHisState = initialState, action): MemoHisState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MEMOHIS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MEMOHIS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_MEMOHIS):
    case REQUEST(ACTION_TYPES.UPDATE_MEMOHIS):
    case REQUEST(ACTION_TYPES.DELETE_MEMOHIS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_MEMOHIS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MEMOHIS):
    case FAILURE(ACTION_TYPES.CREATE_MEMOHIS):
    case FAILURE(ACTION_TYPES.UPDATE_MEMOHIS):
    case FAILURE(ACTION_TYPES.DELETE_MEMOHIS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MEMOHIS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_MEMOHIS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_MEMOHIS):
    case SUCCESS(ACTION_TYPES.UPDATE_MEMOHIS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_MEMOHIS):
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

const apiUrl = 'api/memo-his';

// Actions

export const getEntities: ICrudGetAllAction<IMemoHis> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MEMOHIS_LIST,
    payload: axios.get<IMemoHis>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IMemoHis> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MEMOHIS,
    payload: axios.get<IMemoHis>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IMemoHis> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MEMOHIS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMemoHis> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MEMOHIS,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMemoHis> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MEMOHIS,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
