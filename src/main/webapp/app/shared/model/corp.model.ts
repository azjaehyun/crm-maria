import { ITeamGrp } from 'app/shared/model/team-grp.model';
import { Yn } from 'app/shared/model/enumerations/yn.model';

export interface ICorp {
  id?: number;
  corpCode?: string;
  corpName?: string;
  useYn?: Yn;
  teamGrps?: ITeamGrp[];
}

export const defaultValue: Readonly<ICorp> = {};
