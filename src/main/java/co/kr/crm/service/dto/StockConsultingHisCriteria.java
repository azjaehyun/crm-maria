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
 * Criteria class for the {@link co.kr.crm.domain.StockConsultingHis} entity. This class is used
 * in {@link co.kr.crm.web.rest.StockConsultingHisResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /stock-consulting-his?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StockConsultingHisCriteria implements Serializable, Criteria {
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

    private StringFilter consultingMemo;

    private LocalDateFilter regDtm;

    private YnFilter useYn;

    private LongFilter crmCustomId;

    public StockConsultingHisCriteria() {
    }

    public StockConsultingHisCriteria(StockConsultingHisCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.consultingMemo = other.consultingMemo == null ? null : other.consultingMemo.copy();
        this.regDtm = other.regDtm == null ? null : other.regDtm.copy();
        this.useYn = other.useYn == null ? null : other.useYn.copy();
        this.crmCustomId = other.crmCustomId == null ? null : other.crmCustomId.copy();
    }

    @Override
    public StockConsultingHisCriteria copy() {
        return new StockConsultingHisCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getConsultingMemo() {
        return consultingMemo;
    }

    public void setConsultingMemo(StringFilter consultingMemo) {
        this.consultingMemo = consultingMemo;
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
        final StockConsultingHisCriteria that = (StockConsultingHisCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(consultingMemo, that.consultingMemo) &&
            Objects.equals(regDtm, that.regDtm) &&
            Objects.equals(useYn, that.useYn) &&
            Objects.equals(crmCustomId, that.crmCustomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        consultingMemo,
        regDtm,
        useYn,
        crmCustomId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StockConsultingHisCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (consultingMemo != null ? "consultingMemo=" + consultingMemo + ", " : "") +
                (regDtm != null ? "regDtm=" + regDtm + ", " : "") +
                (useYn != null ? "useYn=" + useYn + ", " : "") +
                (crmCustomId != null ? "crmCustomId=" + crmCustomId + ", " : "") +
            "}";
    }

}
