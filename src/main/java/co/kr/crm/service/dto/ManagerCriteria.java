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
 * Criteria class for the {@link co.kr.crm.domain.Manager} entity. This class is used
 * in {@link co.kr.crm.web.rest.ManagerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /managers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ManagerCriteria implements Serializable, Criteria {
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

    private StringFilter managerName;

    private StringFilter managerPhoneNum;

    private StringFilter teamCode;

    private IntegerFilter totalSalesAmount;

    private LocalDateFilter enterDay;

    private LocalDateFilter outDay;

    private YnFilter useYn;

    private LongFilter crmCustomId;

    private LongFilter teamId;

    public ManagerCriteria() {
    }

    public ManagerCriteria(ManagerCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.corpCode = other.corpCode == null ? null : other.corpCode.copy();
        this.managerName = other.managerName == null ? null : other.managerName.copy();
        this.managerPhoneNum = other.managerPhoneNum == null ? null : other.managerPhoneNum.copy();
        this.teamCode = other.teamCode == null ? null : other.teamCode.copy();
        this.totalSalesAmount = other.totalSalesAmount == null ? null : other.totalSalesAmount.copy();
        this.enterDay = other.enterDay == null ? null : other.enterDay.copy();
        this.outDay = other.outDay == null ? null : other.outDay.copy();
        this.useYn = other.useYn == null ? null : other.useYn.copy();
        this.crmCustomId = other.crmCustomId == null ? null : other.crmCustomId.copy();
        this.teamId = other.teamId == null ? null : other.teamId.copy();
    }

    @Override
    public ManagerCriteria copy() {
        return new ManagerCriteria(this);
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

    public StringFilter getManagerName() {
        return managerName;
    }

    public void setManagerName(StringFilter managerName) {
        this.managerName = managerName;
    }

    public StringFilter getManagerPhoneNum() {
        return managerPhoneNum;
    }

    public void setManagerPhoneNum(StringFilter managerPhoneNum) {
        this.managerPhoneNum = managerPhoneNum;
    }

    public StringFilter getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(StringFilter teamCode) {
        this.teamCode = teamCode;
    }

    public IntegerFilter getTotalSalesAmount() {
        return totalSalesAmount;
    }

    public void setTotalSalesAmount(IntegerFilter totalSalesAmount) {
        this.totalSalesAmount = totalSalesAmount;
    }

    public LocalDateFilter getEnterDay() {
        return enterDay;
    }

    public void setEnterDay(LocalDateFilter enterDay) {
        this.enterDay = enterDay;
    }

    public LocalDateFilter getOutDay() {
        return outDay;
    }

    public void setOutDay(LocalDateFilter outDay) {
        this.outDay = outDay;
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
        final ManagerCriteria that = (ManagerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(corpCode, that.corpCode) &&
            Objects.equals(managerName, that.managerName) &&
            Objects.equals(managerPhoneNum, that.managerPhoneNum) &&
            Objects.equals(teamCode, that.teamCode) &&
            Objects.equals(totalSalesAmount, that.totalSalesAmount) &&
            Objects.equals(enterDay, that.enterDay) &&
            Objects.equals(outDay, that.outDay) &&
            Objects.equals(useYn, that.useYn) &&
            Objects.equals(crmCustomId, that.crmCustomId) &&
            Objects.equals(teamId, that.teamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        corpCode,
        managerName,
        managerPhoneNum,
        teamCode,
        totalSalesAmount,
        enterDay,
        outDay,
        useYn,
        crmCustomId,
        teamId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ManagerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (corpCode != null ? "corpCode=" + corpCode + ", " : "") +
                (managerName != null ? "managerName=" + managerName + ", " : "") +
                (managerPhoneNum != null ? "managerPhoneNum=" + managerPhoneNum + ", " : "") +
                (teamCode != null ? "teamCode=" + teamCode + ", " : "") +
                (totalSalesAmount != null ? "totalSalesAmount=" + totalSalesAmount + ", " : "") +
                (enterDay != null ? "enterDay=" + enterDay + ", " : "") +
                (outDay != null ? "outDay=" + outDay + ", " : "") +
                (useYn != null ? "useYn=" + useYn + ", " : "") +
                (crmCustomId != null ? "crmCustomId=" + crmCustomId + ", " : "") +
                (teamId != null ? "teamId=" + teamId + ", " : "") +
            "}";
    }

}
