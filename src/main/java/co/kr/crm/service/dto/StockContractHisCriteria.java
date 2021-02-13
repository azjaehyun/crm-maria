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
 * Criteria class for the {@link co.kr.crm.domain.StockContractHis} entity. This class is used
 * in {@link co.kr.crm.web.rest.StockContractHisResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /stock-contract-his?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StockContractHisCriteria implements Serializable, Criteria {
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

    private LocalDateFilter fromContractDt;

    private LocalDateFilter toContractDt;

    private IntegerFilter contractAmount;

    private LocalDateFilter regDtm;

    private YnFilter useYn;

    private LongFilter crmCustomId;

    public StockContractHisCriteria() {
    }

    public StockContractHisCriteria(StockContractHisCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fromContractDt = other.fromContractDt == null ? null : other.fromContractDt.copy();
        this.toContractDt = other.toContractDt == null ? null : other.toContractDt.copy();
        this.contractAmount = other.contractAmount == null ? null : other.contractAmount.copy();
        this.regDtm = other.regDtm == null ? null : other.regDtm.copy();
        this.useYn = other.useYn == null ? null : other.useYn.copy();
        this.crmCustomId = other.crmCustomId == null ? null : other.crmCustomId.copy();
    }

    @Override
    public StockContractHisCriteria copy() {
        return new StockContractHisCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getFromContractDt() {
        return fromContractDt;
    }

    public void setFromContractDt(LocalDateFilter fromContractDt) {
        this.fromContractDt = fromContractDt;
    }

    public LocalDateFilter getToContractDt() {
        return toContractDt;
    }

    public void setToContractDt(LocalDateFilter toContractDt) {
        this.toContractDt = toContractDt;
    }

    public IntegerFilter getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(IntegerFilter contractAmount) {
        this.contractAmount = contractAmount;
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
        final StockContractHisCriteria that = (StockContractHisCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fromContractDt, that.fromContractDt) &&
            Objects.equals(toContractDt, that.toContractDt) &&
            Objects.equals(contractAmount, that.contractAmount) &&
            Objects.equals(regDtm, that.regDtm) &&
            Objects.equals(useYn, that.useYn) &&
            Objects.equals(crmCustomId, that.crmCustomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fromContractDt,
        toContractDt,
        contractAmount,
        regDtm,
        useYn,
        crmCustomId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StockContractHisCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fromContractDt != null ? "fromContractDt=" + fromContractDt + ", " : "") +
                (toContractDt != null ? "toContractDt=" + toContractDt + ", " : "") +
                (contractAmount != null ? "contractAmount=" + contractAmount + ", " : "") +
                (regDtm != null ? "regDtm=" + regDtm + ", " : "") +
                (useYn != null ? "useYn=" + useYn + ", " : "") +
                (crmCustomId != null ? "crmCustomId=" + crmCustomId + ", " : "") +
            "}";
    }

}
