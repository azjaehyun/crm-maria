package co.kr.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

import co.kr.crm.domain.enumeration.Yn;

/**
 * A StockConsultingHis.
 */
@Entity
@Table(name = "stock_consulting_his")
public class StockConsultingHis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "consulting_memo")
    private String consultingMemo;

    @Column(name = "reg_dtm")
    private LocalDate regDtm;

    @Enumerated(EnumType.STRING)
    @Column(name = "use_yn")
    private Yn useYn;

    @ManyToOne
    @JsonIgnoreProperties(value = "stockConsultingHis", allowSetters = true)
    private CrmCustom crmCustom;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConsultingMemo() {
        return consultingMemo;
    }

    public StockConsultingHis consultingMemo(String consultingMemo) {
        this.consultingMemo = consultingMemo;
        return this;
    }

    public void setConsultingMemo(String consultingMemo) {
        this.consultingMemo = consultingMemo;
    }

    public LocalDate getRegDtm() {
        return regDtm;
    }

    public StockConsultingHis regDtm(LocalDate regDtm) {
        this.regDtm = regDtm;
        return this;
    }

    public void setRegDtm(LocalDate regDtm) {
        this.regDtm = regDtm;
    }

    public Yn getUseYn() {
        return useYn;
    }

    public StockConsultingHis useYn(Yn useYn) {
        this.useYn = useYn;
        return this;
    }

    public void setUseYn(Yn useYn) {
        this.useYn = useYn;
    }

    public CrmCustom getCrmCustom() {
        return crmCustom;
    }

    public StockConsultingHis crmCustom(CrmCustom crmCustom) {
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
        if (!(o instanceof StockConsultingHis)) {
            return false;
        }
        return id != null && id.equals(((StockConsultingHis) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StockConsultingHis{" +
            "id=" + getId() +
            ", consultingMemo='" + getConsultingMemo() + "'" +
            ", regDtm='" + getRegDtm() + "'" +
            ", useYn='" + getUseYn() + "'" +
            "}";
    }
}
