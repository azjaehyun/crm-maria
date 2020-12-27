import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITmManager, defaultValue } from 'app/shared/model/tm-manager.model';

export const ACTION_TYPES = {
  FETCH_TMMANAGER_LIST: 'tmManager/FETCH_TMMANAGER_LIST',
  FETCH_TMMANAGER: 'tmManager/FETCH_TMMANAGER',
  CREATE_TMMANAGER: 'tmManager/CREATE_TMMANAGER',
  UPDATE_TMMANAGER: 'tmManager/UPDATE_TMMANAGER',
  DELETE_TMMANAGER: 'tmManager/DELETE_TMMANAGER',
  RESET: 'tmManager/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITmManager>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type TmManagerState = Readonly<typeof initialState>;

// Reducer

export default (state: TmManagerState = initialState, action): TmManagerState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TMMANAGER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TMMANAGER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_TMMANAGER):
    case REQUEST(ACTION_TYPES.UPDATE_TMMANAGER):
    case REQUEST(ACTION_TYPES.DELETE_TMMANAGER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_TMMANAGER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TMMANAGER):
    case FAILURE(ACTION_TYPES.CREATE_TMMANAGER):
    case FAILURE(ACTION_TYPES.UPDATE_TMMANAGER):
    case FAILURE(ACTION_TYPES.DELETE_TMMANAGER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TMMANAGER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_TMMANAGER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_TMMANAGER):
    case SUCCESS(ACTION_TYPES.UPDATE_TMMANAGER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_TMMANAGER):
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

const apiUrl = 'api/tm-managers';

// Actions

export const getEntities: ICrudGetAllAction<ITmManager> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TMMANAGER_LIST,
    payload: axios.get<ITmManager>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ITmManager> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TMMANAGER,
    payload: axios.get<ITmManager>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ITmManager> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TMMANAGER,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITmManager> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TMMANAGER,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITmManager> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TMMANAGER,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
