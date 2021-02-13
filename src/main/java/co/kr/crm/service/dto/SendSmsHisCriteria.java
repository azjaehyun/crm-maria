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
 * Criteria class for the {@link co.kr.crm.domain.SendSmsHis} entity. This class is used
 * in {@link co.kr.crm.web.rest.SendSmsHisResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /send-sms-his?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SendSmsHisCriteria implements Serializable, Criteria {
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

    private LocalDateFilter sendDtm;

    private StringFilter fromPhoneNum;

    private StringFilter toPhoneNum;

    private YnFilter useYn;

    private LongFilter crmCustomId;

    public SendSmsHisCriteria() {
    }

    public SendSmsHisCriteria(SendSmsHisCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.sendDtm = other.sendDtm == null ? null : other.sendDtm.copy();
        this.fromPhoneNum = other.fromPhoneNum == null ? null : other.fromPhoneNum.copy();
        this.toPhoneNum = other.toPhoneNum == null ? null : other.toPhoneNum.copy();
        this.useYn = other.useYn == null ? null : other.useYn.copy();
        this.crmCustomId = other.crmCustomId == null ? null : other.crmCustomId.copy();
    }

    @Override
    public SendSmsHisCriteria copy() {
        return new SendSmsHisCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getSendDtm() {
        return sendDtm;
    }

    public void setSendDtm(LocalDateFilter sendDtm) {
        this.sendDtm = sendDtm;
    }

    public StringFilter getFromPhoneNum() {
        return fromPhoneNum;
    }

    public void setFromPhoneNum(StringFilter fromPhoneNum) {
        this.fromPhoneNum = fromPhoneNum;
    }

    public StringFilter getToPhoneNum() {
        return toPhoneNum;
    }

    public void setToPhoneNum(StringFilter toPhoneNum) {
        this.toPhoneNum = toPhoneNum;
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
        final SendSmsHisCriteria that = (SendSmsHisCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(sendDtm, that.sendDtm) &&
            Objects.equals(fromPhoneNum, that.fromPhoneNum) &&
            Objects.equals(toPhoneNum, that.toPhoneNum) &&
            Objects.equals(useYn, that.useYn) &&
            Objects.equals(crmCustomId, that.crmCustomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        sendDtm,
        fromPhoneNum,
        toPhoneNum,
        useYn,
        crmCustomId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SendSmsHisCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (sendDtm != null ? "sendDtm=" + sendDtm + ", " : "") +
                (fromPhoneNum != null ? "fromPhoneNum=" + fromPhoneNum + ", " : "") +
                (toPhoneNum != null ? "toPhoneNum=" + toPhoneNum + ", " : "") +
                (useYn != null ? "useYn=" + useYn + ", " : "") +
                (crmCustomId != null ? "crmCustomId=" + crmCustomId + ", " : "") +
            "}";
    }

}
