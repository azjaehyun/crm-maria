package co.kr.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import co.kr.crm.domain.enumeration.SalesStatus;

import co.kr.crm.domain.enumeration.SmsReceptionYn;

import co.kr.crm.domain.enumeration.CallStatus;

import co.kr.crm.domain.enumeration.CustomStatus;

import co.kr.crm.domain.enumeration.Yn;

/**
 * 고객 정보
 */
@ApiModel(description = "고객 정보")
@Entity
@Table(name = "crm_custom")
public class CrmCustom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "corp_code", nullable = false)
    private String corpCode;

    @NotNull
    @Column(name = "crm_name", nullable = false)
    private String crmName;

    @Column(name = "phone_num")
    private String phoneNum;

    @NotNull
    @Column(name = "five_dayfree_yn", nullable = false)
    private String fiveDayfreeYn;

    @Enumerated(EnumType.STRING)
    @Column(name = "sales_status")
    private SalesStatus salesStatus;

    /**
     * PAY , FIVEDAYFREE , STANDBY ,  BLACKLIST , OUT
     */
    @ApiModelProperty(value = "PAY , FIVEDAYFREE , STANDBY ,  BLACKLIST , OUT")
    @Enumerated(EnumType.STRING)
    @Column(name = "sms_reception_yn")
    private SmsReceptionYn smsReceptionYn;

    @Enumerated(EnumType.STRING)
    @Column(name = "call_status")
    private CallStatus callStatus;

    /**
     * 가망  , 차단 , 거절 , 재통화 , 배팅 ,
     */
    @ApiModelProperty(value = "가망  , 차단 , 거절 , 재통화 , 배팅 ,")
    @Enumerated(EnumType.STRING)
    @Column(name = "custom_status")
    private CustomStatus customStatus;

    @Column(name = "temp_one_status")
    private String tempOneStatus;

    @Column(name = "temp_two_status")
    private String tempTwoStatus;

    @Column(name = "db_insert_type")
    private String dbInsertType;

    /**
     * dbInsertType
     */
    @ApiModelProperty(value = "dbInsertType")
    @Enumerated(EnumType.STRING)
    @Column(name = "use_yn")
    private Yn useYn;

    @OneToMany(mappedBy = "crmCustom")
    private Set<MemoHis> memoHis = new HashSet<>();

    @OneToMany(mappedBy = "crmCustom")
    private Set<SendSmsHis> sendSmsHis = new HashSet<>();

    @OneToMany(mappedBy = "crmCustom")
    private Set<StockContractHis> stockContractHis = new HashSet<>();

    @OneToMany(mappedBy = "crmCustom")
    private Set<StockConsultingHis> stockConsultingHis = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "crmCustoms", allowSetters = true)
    private Manager manager;

    @ManyToOne
    @JsonIgnoreProperties(value = "crmCustoms", allowSetters = true)
    private TmManager tmManager;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorpCode() {
        return corpCode;
    }

    public CrmCustom corpCode(String corpCode) {
        this.corpCode = corpCode;
        return this;
    }

    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }

    public String getCrmName() {
        return crmName;
    }

    public CrmCustom crmName(String crmName) {
        this.crmName = crmName;
        return this;
    }

    public void setCrmName(String crmName) {
        this.crmName = crmName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public CrmCustom phoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
        return this;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getFiveDayfreeYn() {
        return fiveDayfreeYn;
    }

    public CrmCustom fiveDayfreeYn(String fiveDayfreeYn) {
        this.fiveDayfreeYn = fiveDayfreeYn;
        return this;
    }

    public void setFiveDayfreeYn(String fiveDayfreeYn) {
        this.fiveDayfreeYn = fiveDayfreeYn;
    }

    public SalesStatus getSalesStatus() {
        return salesStatus;
    }

    public CrmCustom salesStatus(SalesStatus salesStatus) {
        this.salesStatus = salesStatus;
        return this;
    }

    public void setSalesStatus(SalesStatus salesStatus) {
        this.salesStatus = salesStatus;
    }

    public SmsReceptionYn getSmsReceptionYn() {
        return smsReceptionYn;
    }

    public CrmCustom smsReceptionYn(SmsReceptionYn smsReceptionYn) {
        this.smsReceptionYn = smsReceptionYn;
        return this;
    }

    public void setSmsReceptionYn(SmsReceptionYn smsReceptionYn) {
        this.smsReceptionYn = smsReceptionYn;
    }

    public CallStatus getCallStatus() {
        return callStatus;
    }

    public CrmCustom callStatus(CallStatus callStatus) {
        this.callStatus = callStatus;
        return this;
    }

    public void setCallStatus(CallStatus callStatus) {
        this.callStatus = callStatus;
    }

    public CustomStatus getCustomStatus() {
        return customStatus;
    }

    public CrmCustom customStatus(CustomStatus customStatus) {
        this.customStatus = customStatus;
        return this;
    }

    public void setCustomStatus(CustomStatus customStatus) {
        this.customStatus = customStatus;
    }

    public String getTempOneStatus() {
        return tempOneStatus;
    }

    public CrmCustom tempOneStatus(String tempOneStatus) {
        this.tempOneStatus = tempOneStatus;
        return this;
    }

    public void setTempOneStatus(String tempOneStatus) {
        this.tempOneStatus = tempOneStatus;
    }

    public String getTempTwoStatus() {
        return tempTwoStatus;
    }

    public CrmCustom tempTwoStatus(String tempTwoStatus) {
        this.tempTwoStatus = tempTwoStatus;
        return this;
    }

    public void setTempTwoStatus(String tempTwoStatus) {
        this.tempTwoStatus = tempTwoStatus;
    }

    public String getDbInsertType() {
        return dbInsertType;
    }

    public CrmCustom dbInsertType(String dbInsertType) {
        this.dbInsertType = dbInsertType;
        return this;
    }

    public void setDbInsertType(String dbInsertType) {
        this.dbInsertType = dbInsertType;
    }

    public Yn getUseYn() {
        return useYn;
    }

    public CrmCustom useYn(Yn useYn) {
        this.useYn = useYn;
        return this;
    }

    public void setUseYn(Yn useYn) {
        this.useYn = useYn;
    }

    public Set<MemoHis> getMemoHis() {
        return memoHis;
    }

    public CrmCustom memoHis(Set<MemoHis> memoHis) {
        this.memoHis = memoHis;
        return this;
    }

    public CrmCustom addMemoHis(MemoHis memoHis) {
        this.memoHis.add(memoHis);
        memoHis.setCrmCustom(this);
        return this;
    }

    public CrmCustom removeMemoHis(MemoHis memoHis) {
        this.memoHis.remove(memoHis);
        memoHis.setCrmCustom(null);
        return this;
    }

    public void setMemoHis(Set<MemoHis> memoHis) {
        this.memoHis = memoHis;
    }

    public Set<SendSmsHis> getSendSmsHis() {
        return sendSmsHis;
    }

    public CrmCustom sendSmsHis(Set<SendSmsHis> sendSmsHis) {
        this.sendSmsHis = sendSmsHis;
        return this;
    }

    public CrmCustom addSendSmsHis(SendSmsHis sendSmsHis) {
        this.sendSmsHis.add(sendSmsHis);
        sendSmsHis.setCrmCustom(this);
        return this;
    }

    public CrmCustom removeSendSmsHis(SendSmsHis sendSmsHis) {
        this.sendSmsHis.remove(sendSmsHis);
        sendSmsHis.setCrmCustom(null);
        return this;
    }

    public void setSendSmsHis(Set<SendSmsHis> sendSmsHis) {
        this.sendSmsHis = sendSmsHis;
    }

    public Set<StockContractHis> getStockContractHis() {
        return stockContractHis;
    }

    public CrmCustom stockContractHis(Set<StockContractHis> stockContractHis) {
        this.stockContractHis = stockContractHis;
        return this;
    }

    public CrmCustom addStockContractHis(StockContractHis stockContractHis) {
        this.stockContractHis.add(stockContractHis);
        stockContractHis.setCrmCustom(this);
        return this;
    }

    public CrmCustom removeStockContractHis(StockContractHis stockContractHis) {
        this.stockContractHis.remove(stockContractHis);
        stockContractHis.setCrmCustom(null);
        return this;
    }

    public void setStockContractHis(Set<StockContractHis> stockContractHis) {
        this.stockContractHis = stockContractHis;
    }

    public Set<StockConsultingHis> getStockConsultingHis() {
        return stockConsultingHis;
    }

    public CrmCustom stockConsultingHis(Set<StockConsultingHis> stockConsultingHis) {
        this.stockConsultingHis = stockConsultingHis;
        return this;
    }

    public CrmCustom addStockConsultingHis(StockConsultingHis stockConsultingHis) {
        this.stockConsultingHis.add(stockConsultingHis);
        stockConsultingHis.setCrmCustom(this);
        return this;
    }

    public CrmCustom removeStockConsultingHis(StockConsultingHis stockConsultingHis) {
        this.stockConsultingHis.remove(stockConsultingHis);
        stockConsultingHis.setCrmCustom(null);
        return this;
    }

    public void setStockConsultingHis(Set<StockConsultingHis> stockConsultingHis) {
        this.stockConsultingHis = stockConsultingHis;
    }

    public Manager getManager() {
        return manager;
    }

    public CrmCustom manager(Manager manager) {
        this.manager = manager;
        return this;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public TmManager getTmManager() {
        return tmManager;
    }

    public CrmCustom tmManager(TmManager tmManager) {
        this.tmManager = tmManager;
        return this;
    }

    public void setTmManager(TmManager tmManager) {
        this.tmManager = tmManager;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CrmCustom)) {
            return false;
        }
        return id != null && id.equals(((CrmCustom) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CrmCustom{" +
            "id=" + getId() +
            ", corpCode='" + getCorpCode() + "'" +
            ", crmName='" + getCrmName() + "'" +
            ", phoneNum='" + getPhoneNum() + "'" +
            ", fiveDayfreeYn='" + getFiveDayfreeYn() + "'" +
            ", salesStatus='" + getSalesStatus() + "'" +
            ", smsReceptionYn='" + getSmsReceptionYn() + "'" +
            ", callStatus='" + getCallStatus() + "'" +
            ", customStatus='" + getCustomStatus() + "'" +
            ", tempOneStatus='" + getTempOneStatus() + "'" +
            ", tempTwoStatus='" + getTempTwoStatus() + "'" +
            ", dbInsertType='" + getDbInsertType() + "'" +
            ", useYn='" + getUseYn() + "'" +
            "}";
    }
}
