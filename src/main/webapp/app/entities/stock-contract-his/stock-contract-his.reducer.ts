import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IStockContractHis, defaultValue } from 'app/shared/model/stock-contract-his.model';

export const ACTION_TYPES = {
  FETCH_STOCKCONTRACTHIS_LIST: 'stockContractHis/FETCH_STOCKCONTRACTHIS_LIST',
  FETCH_STOCKCONTRACTHIS: 'stockContractHis/FETCH_STOCKCONTRACTHIS',
  CREATE_STOCKCONTRACTHIS: 'stockContractHis/CREATE_STOCKCONTRACTHIS',
  UPDATE_STOCKCONTRACTHIS: 'stockContractHis/UPDATE_STOCKCONTRACTHIS',
  DELETE_STOCKCONTRACTHIS: 'stockContractHis/DELETE_STOCKCONTRACTHIS',
  RESET: 'stockContractHis/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IStockContractHis>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type StockContractHisState = Readonly<typeof initialState>;

// Reducer

export default (state: StockContractHisState = initialState, action): StockContractHisState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_STOCKCONTRACTHIS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_STOCKCONTRACTHIS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_STOCKCONTRACTHIS):
    case REQUEST(ACTION_TYPES.UPDATE_STOCKCONTRACTHIS):
    case REQUEST(ACTION_TYPES.DELETE_STOCKCONTRACTHIS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_STOCKCONTRACTHIS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_STOCKCONTRACTHIS):
    case FAILURE(ACTION_TYPES.CREATE_STOCKCONTRACTHIS):
    case FAILURE(ACTION_TYPES.UPDATE_STOCKCONTRACTHIS):
    case FAILURE(ACTION_TYPES.DELETE_STOCKCONTRACTHIS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_STOCKCONTRACTHIS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_STOCKCONTRACTHIS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_STOCKCONTRACTHIS):
    case SUCCESS(ACTION_TYPES.UPDATE_STOCKCONTRACTHIS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_STOCKCONTRACTHIS):
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

const apiUrl = 'api/stock-contract-his';

// Actions

export const getEntities: ICrudGetAllAction<IStockContractHis> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_STOCKCONTRACTHIS_LIST,
    payload: axios.get<IStockContractHis>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IStockContractHis> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_STOCKCONTRACTHIS,
    payload: axios.get<IStockContractHis>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IStockContractHis> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_STOCKCONTRACTHIS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IStockContractHis> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_STOCKCONTRACTHIS,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IStockContractHis> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_STOCKCONTRACTHIS,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
