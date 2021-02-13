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
 * Criteria class for the {@link co.kr.crm.domain.Corp} entity. This class is used
 * in {@link co.kr.crm.web.rest.CorpResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /corps?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CorpCriteria implements Serializable, Criteria {
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

    private StringFilter corpName;

    private YnFilter useYn;

    private LongFilter teamGrpId;

    public CorpCriteria() {
    }

    public CorpCriteria(CorpCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.corpCode = other.corpCode == null ? null : other.corpCode.copy();
        this.corpName = other.corpName == null ? null : other.corpName.copy();
        this.useYn = other.useYn == null ? null : other.useYn.copy();
        this.teamGrpId = other.teamGrpId == null ? null : other.teamGrpId.copy();
    }

    @Override
    public CorpCriteria copy() {
        return new CorpCriteria(this);
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

    public StringFilter getCorpName() {
        return corpName;
    }

    public void setCorpName(StringFilter corpName) {
        this.corpName = corpName;
    }

    public YnFilter getUseYn() {
        return useYn;
    }

    public void setUseYn(YnFilter useYn) {
        this.useYn = useYn;
    }

    public LongFilter getTeamGrpId() {
        return teamGrpId;
    }

    public void setTeamGrpId(LongFilter teamGrpId) {
        this.teamGrpId = teamGrpId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CorpCriteria that = (CorpCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(corpCode, that.corpCode) &&
            Objects.equals(corpName, that.corpName) &&
            Objects.equals(useYn, that.useYn) &&
            Objects.equals(teamGrpId, that.teamGrpId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        corpCode,
        corpName,
        useYn,
        teamGrpId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CorpCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (corpCode != null ? "corpCode=" + corpCode + ", " : "") +
                (corpName != null ? "corpName=" + corpName + ", " : "") +
                (useYn != null ? "useYn=" + useYn + ", " : "") +
                (teamGrpId != null ? "teamGrpId=" + teamGrpId + ", " : "") +
            "}";
    }

}
