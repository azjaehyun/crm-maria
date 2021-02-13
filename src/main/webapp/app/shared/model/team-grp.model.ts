import { IManager } from 'app/shared/model/manager.model';
import { ITmManager } from 'app/shared/model/tm-manager.model';
import { Yn } from 'app/shared/model/enumerations/yn.model';

export interface ITeamGrp {
  id?: number;
  teamCode?: string;
  teamName?: string;
  useYn?: Yn;
  managers?: IManager[];
  tmManagers?: ITmManager[];
  corpId?: number;
}

export const defaultValue: Readonly<ITeamGrp> = {};
