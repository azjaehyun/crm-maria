package co.kr.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

import co.kr.crm.domain.enumeration.Yn;

/**
 * SMS 이력 정보
 */
@ApiModel(description = "SMS 이력 정보")
@Entity
@Table(name = "send_sms_his")
public class SendSmsHis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "send_dtm")
    private LocalDate sendDtm;

    @Column(name = "from_phone_num")
    private String fromPhoneNum;

    @Column(name = "to_phone_num")
    private String toPhoneNum;

    @Enumerated(EnumType.STRING)
    @Column(name = "use_yn")
    private Yn useYn;

    @ManyToOne
    @JsonIgnoreProperties(value = "sendSmsHis", allowSetters = true)
    private CrmCustom crmCustom;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getSendDtm() {
        return sendDtm;
    }

    public SendSmsHis sendDtm(LocalDate sendDtm) {
        this.sendDtm = sendDtm;
        return this;
    }

    public void setSendDtm(LocalDate sendDtm) {
        this.sendDtm = sendDtm;
    }

    public String getFromPhoneNum() {
        return fromPhoneNum;
    }

    public SendSmsHis fromPhoneNum(String fromPhoneNum) {
        this.fromPhoneNum = fromPhoneNum;
        return this;
    }

    public void setFromPhoneNum(String fromPhoneNum) {
        this.fromPhoneNum = fromPhoneNum;
    }

    public String getToPhoneNum() {
        return toPhoneNum;
    }

    public SendSmsHis toPhoneNum(String toPhoneNum) {
        this.toPhoneNum = toPhoneNum;
        return this;
    }

    public void setToPhoneNum(String toPhoneNum) {
        this.toPhoneNum = toPhoneNum;
    }

    public Yn getUseYn() {
        return useYn;
    }

    public SendSmsHis useYn(Yn useYn) {
        this.useYn = useYn;
        return this;
    }

    public void setUseYn(Yn useYn) {
        this.useYn = useYn;
    }

    public CrmCustom getCrmCustom() {
        return crmCustom;
    }

    public SendSmsHis crmCustom(CrmCustom crmCustom) {
        this.crmCustom = crmCustom;
        return this;
    }

    public void setCrmCustom(CrmCustom crmCustom) {
        this.crmCustom = crmCustom;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SendSmsHis)) {
            return false;
        }
        return id != null && id.equals(((SendSmsHis) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SendSmsHis{" +
            "id=" + getId() +
            ", sendDtm='" + getSendDtm() + "'" +
            ", fromPhoneNum='" + getFromPhoneNum() + "'" +
            ", toPhoneNum='" + getToPhoneNum() + "'" +
            ", useYn='" + getUseYn() + "'" +
            "}";
    }
}
