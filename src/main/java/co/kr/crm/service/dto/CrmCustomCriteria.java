package co.kr.crm.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import co.kr.crm.domain.enumeration.SalesStatus;
import co.kr.crm.domain.enumeration.SmsReceptionYn;
import co.kr.crm.domain.enumeration.CallStatus;
import co.kr.crm.domain.enumeration.CustomStatus;
import co.kr.crm.domain.enumeration.Yn;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link co.kr.crm.domain.CrmCustom} entity. This class is used
 * in {@link co.kr.crm.web.rest.CrmCustomResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /crm-customs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CrmCustomCriteria implements Serializable, Criteria {
    /**
     * Class for filtering SalesStatus
     */
    public static class SalesStatusFilter extends Filter<SalesStatus> {

        public SalesStatusFilter() {
        }

        public SalesStatusFilter(SalesStatusFilter filter) {
            super(filter);
        }

        @Override
        public SalesStatusFilter copy() {
            return new SalesStatusFilter(this);
        }

    }
    /**
     * Class for filtering SmsReceptionYn
     */
    public static class SmsReceptionYnFilter extends Filter<SmsReceptionYn> {

        public SmsReceptionYnFilter() {
        }

        public SmsReceptionYnFilter(SmsReceptionYnFilter filter) {
            super(filter);
        }

        @Override
        public SmsReceptionYnFilter copy() {
            return new SmsReceptionYnFilter(this);
        }

    }
    /**
     * Class for filtering CallStatus
     */
    public static class CallStatusFilter extends Filter<CallStatus> {

        public CallStatusFilter() {
        }

        public CallStatusFilter(CallStatusFilter filter) {
            super(filter);
        }

        @Override
        public CallStatusFilter copy() {
            return new CallStatusFilter(this);
        }

    }
    /**
     * Class for filtering CustomStatus
     */
    public static class CustomStatusFilter extends Filter<CustomStatus> {

        public CustomStatusFilter() {
        }

        public CustomStatusFilter(CustomStatusFilter filter) {
            super(filter);
        }

        @Override
        public CustomStatusFilter copy() {
            return new CustomStatusFilter(this);
        }

    }
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

    private StringFilter crmName;

    private StringFilter phoneNum;

    private StringFilter fiveDayfreeYn;

    private SalesStatusFilter salesStatus;

    private SmsReceptionYnFilter smsReceptionYn;

    private CallStatusFilter callStatus;

    private CustomStatusFilter customStatus;

    private StringFilter tempOneStatus;

    private StringFilter tempTwoStatus;

    private StringFilter dbInsertType;

    private YnFilter useYn;

    private LongFilter memoHisId;

    private LongFilter sendSmsHisId;

    private LongFilter stockContractHisId;

    private LongFilter stockConsultingHisId;

    private LongFilter managerId;

    private LongFilter tmManagerId;

    public CrmCustomCriteria() {
    }

    public CrmCustomCriteria(CrmCustomCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.corpCode = other.corpCode == null ? null : other.corpCode.copy();
        this.crmName = other.crmName == null ? null : other.crmName.copy();
        this.phoneNum = other.phoneNum == null ? null : other.phoneNum.copy();
        this.fiveDayfreeYn = other.fiveDayfreeYn == null ? null : other.fiveDayfreeYn.copy();
        this.salesStatus = other.salesStatus == null ? null : other.salesStatus.copy();
        this.smsReceptionYn = other.smsReceptionYn == null ? null : other.smsReceptionYn.copy();
        this.callStatus = other.callStatus == null ? null : other.callStatus.copy();
        this.customStatus = other.customStatus == null ? null : other.customStatus.copy();
        this.tempOneStatus = other.tempOneStatus == null ? null : other.tempOneStatus.copy();
        this.tempTwoStatus = other.tempTwoStatus == null ? null : other.tempTwoStatus.copy();
        this.dbInsertType = other.dbInsertType == null ? null : other.dbInsertType.copy();
        this.useYn = other.useYn == null ? null : other.useYn.copy();
        this.memoHisId = other.memoHisId == null ? null : other.memoHisId.copy();
        this.sendSmsHisId = other.sendSmsHisId == null ? null : other.sendSmsHisId.copy();
        this.stockContractHisId = other.stockContractHisId == null ? null : other.stockContractHisId.copy();
        this.stockConsultingHisId = other.stockConsultingHisId == null ? null : other.stockConsultingHisId.copy();
        this.managerId = other.managerId == null ? null : other.managerId.copy();
        this.tmManagerId = other.tmManagerId == null ? null : other.tmManagerId.copy();
    }

    @Override
    public CrmCustomCriteria copy() {
        return new CrmCustomCriteria(this);
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

    public StringFilter getCrmName() {
        return crmName;
    }

    public void setCrmName(StringFilter crmName) {
        this.crmName = crmName;
    }

    public StringFilter getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(StringFilter phoneNum) {
        this.phoneNum = phoneNum;
    }

    public StringFilter getFiveDayfreeYn() {
        return fiveDayfreeYn;
    }

    public void setFiveDayfreeYn(StringFilter fiveDayfreeYn) {
        this.fiveDayfreeYn = fiveDayfreeYn;
    }

    public SalesStatusFilter getSalesStatus() {
        return salesStatus;
    }

    public void setSalesStatus(SalesStatusFilter salesStatus) {
        this.salesStatus = salesStatus;
    }

    public SmsReceptionYnFilter getSmsReceptionYn() {
        return smsReceptionYn;
    }

    public void setSmsReceptionYn(SmsReceptionYnFilter smsReceptionYn) {
        this.smsReceptionYn = smsReceptionYn;
    }

    public CallStatusFilter getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(CallStatusFilter callStatus) {
        this.callStatus = callStatus;
    }

    public CustomStatusFilter getCustomStatus() {
        return customStatus;
    }

    public void setCustomStatus(CustomStatusFilter customStatus) {
        this.customStatus = customStatus;
    }

    public StringFilter getTempOneStatus() {
        return tempOneStatus;
    }

    public void setTempOneStatus(StringFilter tempOneStatus) {
        this.tempOneStatus = tempOneStatus;
    }

    public StringFilter getTempTwoStatus() {
        return tempTwoStatus;
    }

    public void setTempTwoStatus(StringFilter tempTwoStatus) {
        this.tempTwoStatus = tempTwoStatus;
    }

    public StringFilter getDbInsertType() {
        return dbInsertType;
    }

    public void setDbInsertType(StringFilter dbInsertType) {
        this.dbInsertType = dbInsertType;
    }

    public YnFilter getUseYn() {
        return useYn;
    }

    public void setUseYn(YnFilter useYn) {
        this.useYn = useYn;
    }

    public LongFilter getMemoHisId() {
        return memoHisId;
    }

    public void setMemoHisId(LongFilter memoHisId) {
        this.memoHisId = memoHisId;
    }

    public LongFilter getSendSmsHisId() {
        return sendSmsHisId;
    }

    public void setSendSmsHisId(LongFilter sendSmsHisId) {
        this.sendSmsHisId = sendSmsHisId;
    }

    public LongFilter getStockContractHisId() {
        return stockContractHisId;
    }

    public void setStockContractHisId(LongFilter stockContractHisId) {
        this.stockContractHisId = stockContractHisId;
    }

    public LongFilter getStockConsultingHisId() {
        return stockConsultingHisId;
    }

    public void setStockConsultingHisId(LongFilter stockConsultingHisId) {
        this.stockConsultingHisId = stockConsultingHisId;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CrmCustomCriteria that = (CrmCustomCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(corpCode, that.corpCode) &&
            Objects.equals(crmName, that.crmName) &&
            Objects.equals(phoneNum, that.phoneNum) &&
            Objects.equals(fiveDayfreeYn, that.fiveDayfreeYn) &&
            Objects.equals(salesStatus, that.salesStatus) &&
            Objects.equals(smsReceptionYn, that.smsReceptionYn) &&
            Objects.equals(callStatus, that.callStatus) &&
            Objects.equals(customStatus, that.customStatus) &&
            Objects.equals(tempOneStatus, that.tempOneStatus) &&
            Objects.equals(tempTwoStatus, that.tempTwoStatus) &&
            Objects.equals(dbInsertType, that.dbInsertType) &&
            Objects.equals(useYn, that.useYn) &&
            Objects.equals(memoHisId, that.memoHisId) &&
            Objects.equals(sendSmsHisId, that.sendSmsHisId) &&
            Objects.equals(stockContractHisId, that.stockContractHisId) &&
            Objects.equals(stockConsultingHisId, that.stockConsultingHisId) &&
            Objects.equals(managerId, that.managerId) &&
            Objects.equals(tmManagerId, that.tmManagerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        corpCode,
        crmName,
        phoneNum,
        fiveDayfreeYn,
        salesStatus,
        smsReceptionYn,
        callStatus,
        customStatus,
        tempOneStatus,
        tempTwoStatus,
        dbInsertType,
        useYn,
        memoHisId,
        sendSmsHisId,
        stockContractHisId,
        stockConsultingHisId,
        managerId,
        tmManagerId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CrmCustomCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (corpCode != null ? "corpCode=" + corpCode + ", " : "") +
                (crmName != null ? "crmName=" + crmName + ", " : "") +
                (phoneNum != null ? "phoneNum=" + phoneNum + ", " : "") +
                (fiveDayfreeYn != null ? "fiveDayfreeYn=" + fiveDayfreeYn + ", " : "") +
                (salesStatus != null ? "salesStatus=" + salesStatus + ", " : "") +
                (smsReceptionYn != null ? "smsReceptionYn=" + smsReceptionYn + ", " : "") +
                (callStatus != null ? "callStatus=" + callStatus + ", " : "") +
                (customStatus != null ? "customStatus=" + customStatus + ", " : "") +
                (tempOneStatus != null ? "tempOneStatus=" + tempOneStatus + ", " : "") +
                (tempTwoStatus != null ? "tempTwoStatus=" + tempTwoStatus + ", " : "") +
                (dbInsertType != null ? "dbInsertType=" + dbInsertType + ", " : "") +
                (useYn != null ? "useYn=" + useYn + ", " : "") +
                (memoHisId != null ? "memoHisId=" + memoHisId + ", " : "") +
                (sendSmsHisId != null ? "sendSmsHisId=" + sendSmsHisId + ", " : "") +
                (stockContractHisId != null ? "stockContractHisId=" + stockContractHisId + ", " : "") +
                (stockConsultingHisId != null ? "stockConsultingHisId=" + stockConsultingHisId + ", " : "") +
                (managerId != null ? "managerId=" + managerId + ", " : "") +
                (tmManagerId != null ? "tmManagerId=" + tmManagerId + ", " : "") +
            "}";
    }

}
