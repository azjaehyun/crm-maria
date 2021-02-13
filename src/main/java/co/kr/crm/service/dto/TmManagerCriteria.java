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
 * Criteria class for the {@link co.kr.crm.domain.TmManager} entity. This class is used
 * in {@link co.kr.crm.web.rest.TmManagerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tm-managers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TmManagerCriteria implements Serializable, Criteria {
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

    private StringFilter corpCode;

    private StringFilter tmManagerName;

    private StringFilter tmManagerPhoneNum;

    private StringFilter teamCode;

    private IntegerFilter crmManageCnt;

    private YnFilter useYn;

    private LongFilter crmCustomId;

    private LongFilter teamId;

    public TmManagerCriteria() {
    }

    public TmManagerCriteria(TmManagerCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.corpCode = other.corpCode == null ? null : other.corpCode.copy();
        this.tmManagerName = other.tmManagerName == null ? null : other.tmManagerName.copy();
        this.tmManagerPhoneNum = other.tmManagerPhoneNum == null ? null : other.tmManagerPhoneNum.copy();
        this.teamCode = other.teamCode == null ? null : other.teamCode.copy();
        this.crmManageCnt = other.crmManageCnt == null ? null : other.crmManageCnt.copy();
        this.useYn = other.useYn == null ? null : other.useYn.copy();
        this.crmCustomId = other.crmCustomId == null ? null : other.crmCustomId.copy();
        this.teamId = other.teamId == null ? null : other.teamId.copy();
    }

    @Override
    public TmManagerCriteria copy() {
        return new TmManagerCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCorpCode() {
        return corpCode;
    }

    public void setCorpCode(StringFilter corpCode) {
        this.corpCode = corpCode;
    }

    public StringFilter getTmManagerName() {
        return tmManagerName;
    }

    public void setTmManagerName(StringFilter tmManagerName) {
        this.tmManagerName = tmManagerName;
    }

    public StringFilter getTmManagerPhoneNum() {
        return tmManagerPhoneNum;
    }

    public void setTmManagerPhoneNum(StringFilter tmManagerPhoneNum) {
        this.tmManagerPhoneNum = tmManagerPhoneNum;
    }

    public StringFilter getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(StringFilter teamCode) {
        this.teamCode = teamCode;
    }

    public IntegerFilter getCrmManageCnt() {
        return crmManageCnt;
    }

    public void setCrmManageCnt(IntegerFilter crmManageCnt) {
        this.crmManageCnt = crmManageCnt;
    }

    public YnFilter getUseYn() {
        return useYn;
    }

    public void setUseYn(YnFilter useYn) {
        this.useYn = useYn;
    }

    public LongFilter getCrmCustomId() {
        return crmCustomId;
    }

    public void setCrmCustomId(LongFilter crmCustomId) {
        this.crmCustomId = crmCustomId;
    }

    public LongFilter getTeamId() {
        return teamId;
    }

    public void setTeamId(LongFilter teamId) {
        this.teamId = teamId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TmManagerCriteria that = (TmManagerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(corpCode, that.corpCode) &&
            Objects.equals(tmManagerName, that.tmManagerName) &&
            Objects.equals(tmManagerPhoneNum, that.tmManagerPhoneNum) &&
            Objects.equals(teamCode, that.teamCode) &&
            Objects.equals(crmManageCnt, that.crmManageCnt) &&
            Objects.equals(useYn, that.useYn) &&
            Objects.equals(crmCustomId, that.crmCustomId) &&
            Objects.equals(teamId, that.teamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        corpCode,
        tmManagerName,
        tmManagerPhoneNum,
        teamCode,
        crmManageCnt,
        useYn,
        crmCustomId,
        teamId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TmManagerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (corpCode != null ? "corpCode=" + corpCode + ", " : "") +
                (tmManagerName != null ? "tmManagerName=" + tmManagerName + ", " : "") +
                (tmManagerPhoneNum != null ? "tmManagerPhoneNum=" + tmManagerPhoneNum + ", " : "") +
                (teamCode != null ? "teamCode=" + teamCode + ", " : "") +
                (crmManageCnt != null ? "crmManageCnt=" + crmManageCnt + ", " : "") +
                (useYn != null ? "useYn=" + useYn + ", " : "") +
                (crmCustomId != null ? "crmCustomId=" + crmCustomId + ", " : "") +
                (teamId != null ? "teamId=" + teamId + ", " : "") +
            "}";
    }

}
