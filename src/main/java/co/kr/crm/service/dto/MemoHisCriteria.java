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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link co.kr.crm.domain.MemoHis} entity. This class is used
 * in {@link co.kr.crm.web.rest.MemoHisResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /memo-his?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MemoHisCriteria implements Serializable, Criteria {
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

    private StringFilter memoContent;

    private LocalDateFilter regDtm;

    private YnFilter useYn;

    private LongFilter crmCustomId;

    public MemoHisCriteria() {
    }

    public MemoHisCriteria(MemoHisCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.memoContent = other.memoContent == null ? null : other.memoContent.copy();
        this.regDtm = other.regDtm == null ? null : other.regDtm.copy();
        this.useYn = other.useYn == null ? null : other.useYn.copy();
        this.crmCustomId = other.crmCustomId == null ? null : other.crmCustomId.copy();
    }

    @Override
    public MemoHisCriteria copy() {
        return new MemoHisCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMemoContent() {
        return memoContent;
    }

    public void setMemoContent(StringFilter memoContent) {
        this.memoContent = memoContent;
    }

    public LocalDateFilter getRegDtm() {
        return regDtm;
    }

    public void setRegDtm(LocalDateFilter regDtm) {
        this.regDtm = regDtm;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MemoHisCriteria that = (MemoHisCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(memoContent, that.memoContent) &&
            Objects.equals(regDtm, that.regDtm) &&
            Objects.equals(useYn, that.useYn) &&
            Objects.equals(crmCustomId, that.crmCustomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        memoContent,
        regDtm,
        useYn,
        crmCustomId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MemoHisCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (memoContent != null ? "memoContent=" + memoContent + ", " : "") +
                (regDtm != null ? "regDtm=" + regDtm + ", " : "") +
                (useYn != null ? "useYn=" + useYn + ", " : "") +
                (crmCustomId != null ? "crmCustomId=" + crmCustomId + ", " : "") +
            "}";
    }

}
