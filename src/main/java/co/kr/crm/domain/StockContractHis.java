package co.kr.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

import co.kr.crm.domain.enumeration.Yn;

/**
 * A StockContractHis.
 */
@Entity
@Table(name = "stock_contract_his")
public class StockContractHis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_contract_dt")
    private LocalDate fromContractDt;

    @Column(name = "to_contract_dt")
    private LocalDate toContractDt;

    @Column(name = "contract_amount")
    private Integer contractAmount;

    @Column(name = "reg_dtm")
    private LocalDate regDtm;

    @Enumerated(EnumType.STRING)
    @Column(name = "use_yn")
    private Yn useYn;

    @ManyToOne
    @JsonIgnoreProperties(value = "stockContractHis", allowSetters = true)
    private CrmCustom crmCustom;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFromContractDt() {
        return fromContractDt;
    }

    public StockContractHis fromContractDt(LocalDate fromContractDt) {
        this.fromContractDt = fromContractDt;
        return this;
    }

    public void setFromContractDt(LocalDate fromContractDt) {
        this.fromContractDt = fromContractDt;
    }

    public LocalDate getToContractDt() {
        return toContractDt;
    }

    public StockContractHis toContractDt(LocalDate toContractDt) {
        this.toContractDt = toContractDt;
        return this;
    }

    public void setToContractDt(LocalDate toContractDt) {
        this.toContractDt = toContractDt;
    }

    public Integer getContractAmount() {
        return contractAmount;
    }

    public StockContractHis contractAmount(Integer contractAmount) {
        this.contractAmount = contractAmount;
        return this;
    }

    public void setContractAmount(Integer contractAmount) {
        this.contractAmount = contractAmount;
    }

    public LocalDate getRegDtm() {
        return regDtm;
    }

    public StockContractHis regDtm(LocalDate regDtm) {
        this.regDtm = regDtm;
        return this;
    }

    public void setRegDtm(LocalDate regDtm) {
        this.regDtm = regDtm;
    }

    public Yn getUseYn() {
        return useYn;
    }

    public StockContractHis useYn(Yn useYn) {
        this.useYn = useYn;
        return this;
    }

    public void setUseYn(Yn useYn) {
        this.useYn = useYn;
    }

    public CrmCustom getCrmCustom() {
        return crmCustom;
    }

    public StockContractHis crmCustom(CrmCustom crmCustom) {
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
        if (!(o instanceof StockContractHis)) {
            return false;
        }
        return id != null && id.equals(((StockContractHis) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StockContractHis{" +
            "id=" + getId() +
            ", fromContractDt='" + getFromContractDt() + "'" +
            ", toContractDt='" + getToContractDt() + "'" +
            ", contractAmount=" + getContractAmount() +
            ", regDtm='" + getRegDtm() + "'" +
            ", useYn='" + getUseYn() + "'" +
            "}";
    }
}
