package co.kr.crm.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import co.kr.crm.domain.enumeration.Yn;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link co.kr.crm.domain.TeamGrp} entity. This class is used
 * in {@link co.kr.crm.web.rest.TeamGrpResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /team-grps?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TeamGrpCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Yn
     */
    public static class YnFilter extends Filter<Yn> {

        public YnFilter() {
        }

        public YnFilter(YnFilter filter) {
            super(filter);
        }

        @Override
        public YnFilter copy() {
            return new YnFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter teamCode;

    private StringFilter teamName;

    private YnFilter useYn;

    private LongFilter managerId;

    private LongFilter tmManagerId;

    private LongFilter corpId;

    public TeamGrpCriteria() {
    }

    public TeamGrpCriteria(TeamGrpCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.teamCode = other.teamCode == null ? null : other.teamCode.copy();
        this.teamName = other.teamName == null ? null : other.teamName.copy();
        this.useYn = other.useYn == null ? null : other.useYn.copy();
        this.managerId = other.managerId == null ? null : other.managerId.copy();
        this.tmManagerId = other.tmManagerId == null ? null : other.tmManagerId.copy();
        this.corpId = other.corpId == null ? null : other.corpId.copy();
    }

    @Override
    public TeamGrpCriteria copy() {
        return new TeamGrpCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(StringFilter teamCode) {
        this.teamCode = teamCode;
    }

    public StringFilter getTeamName() {
        return teamName;
    }

    public void setTeamName(StringFilter teamName) {
        this.teamName = teamName;
    }

    public YnFilter getUseYn() {
        return useYn;
    }

    public void setUseYn(YnFilter useYn) {
        this.useYn = useYn;
    }

    public LongFilter getManagerId() {
        return managerId;
    }

    public void setManagerId(LongFilter managerId) {
        this.managerId = managerId;
    }

    public LongFilter getTmManagerId() {
        return tmManagerId;
    }

    public void setTmManagerId(LongFilter tmManagerId) {
        this.tmManagerId = tmManagerId;
    }

    public LongFilter getCorpId() {
        return corpId;
    }

    public void setCorpId(LongFilter corpId) {
        this.corpId = corpId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TeamGrpCriteria that = (TeamGrpCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(teamCode, that.teamCode) &&
            Objects.equals(teamName, that.teamName) &&
            Objects.equals(useYn, that.useYn) &&
            Objects.equals(managerId, that.managerId) &&
            Objects.equals(tmManagerId, that.tmManagerId) &&
            Objects.equals(corpId, that.corpId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        teamCode,
        teamName,
        useYn,
        managerId,
        tmManagerId,
        corpId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeamGrpCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (teamCode != null ? "teamCode=" + teamCode + ", " : "") +
                (teamName != null ? "teamName=" + teamName + ", " : "") +
                (useYn != null ? "useYn=" + useYn + ", " : "") +
                (managerId != null ? "managerId=" + managerId + ", " : "") +
                (tmManagerId != null ? "tmManagerId=" + tmManagerId + ", " : "") +
                (corpId != null ? "corpId=" + corpId + ", " : "") +
            "}";
    }

}
