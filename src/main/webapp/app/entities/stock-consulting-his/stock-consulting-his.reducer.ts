import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IStockConsultingHis, defaultValue } from 'app/shared/model/stock-consulting-his.model';

export const ACTION_TYPES = {
  FETCH_STOCKCONSULTINGHIS_LIST: 'stockConsultingHis/FETCH_STOCKCONSULTINGHIS_LIST',
  FETCH_STOCKCONSULTINGHIS: 'stockConsultingHis/FETCH_STOCKCONSULTINGHIS',
  CREATE_STOCKCONSULTINGHIS: 'stockConsultingHis/CREATE_STOCKCONSULTINGHIS',
  UPDATE_STOCKCONSULTINGHIS: 'stockConsultingHis/UPDATE_STOCKCONSULTINGHIS',
  DELETE_STOCKCONSULTINGHIS: 'stockConsultingHis/DELETE_STOCKCONSULTINGHIS',
  RESET: 'stockConsultingHis/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IStockConsultingHis>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type StockConsultingHisState = Readonly<typeof initialState>;

// Reducer

export default (state: StockConsultingHisState = initialState, action): StockConsultingHisState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_STOCKCONSULTINGHIS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_STOCKCONSULTINGHIS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_STOCKCONSULTINGHIS):
    case REQUEST(ACTION_TYPES.UPDATE_STOCKCONSULTINGHIS):
    case REQUEST(ACTION_TYPES.DELETE_STOCKCONSULTINGHIS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_STOCKCONSULTINGHIS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_STOCKCONSULTINGHIS):
    case FAILURE(ACTION_TYPES.CREATE_STOCKCONSULTINGHIS):
    case FAILURE(ACTION_TYPES.UPDATE_STOCKCONSULTINGHIS):
    case FAILURE(ACTION_TYPES.DELETE_STOCKCONSULTINGHIS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_STOCKCONSULTINGHIS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_STOCKCONSULTINGHIS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_STOCKCONSULTINGHIS):
    case SUCCESS(ACTION_TYPES.UPDATE_STOCKCONSULTINGHIS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_STOCKCONSULTINGHIS):
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

const apiUrl = 'api/stock-consulting-his';

// Actions

export const getEntities: ICrudGetAllAction<IStockConsultingHis> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_STOCKCONSULTINGHIS_LIST,
    payload: axios.get<IStockConsultingHis>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IStockConsultingHis> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_STOCKCONSULTINGHIS,
    payload: axios.get<IStockConsultingHis>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IStockConsultingHis> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_STOCKCONSULTINGHIS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IStockConsultingHis> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_STOCKCONSULTINGHIS,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IStockConsultingHis> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_STOCKCONSULTINGHIS,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
