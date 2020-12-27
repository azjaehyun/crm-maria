package co.kr.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

import co.kr.crm.domain.enumeration.Yn;

/**
 * 메모 정보
 */
@ApiModel(description = "메모 정보")
@Entity
@Table(name = "memo_his")
public class MemoHis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "memo_content")
    private String memoContent;

    @Column(name = "reg_dtm")
    private LocalDate regDtm;

    @Enumerated(EnumType.STRING)
    @Column(name = "use_yn")
    private Yn useYn;

    @ManyToOne
    @JsonIgnoreProperties(value = "memoHis", allowSetters = true)
    private CrmCustom crmCustom;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemoContent() {
        return memoContent;
    }

    public MemoHis memoContent(String memoContent) {
        this.memoContent = memoContent;
        return this;
    }

    public void setMemoContent(String memoContent) {
        this.memoContent = memoContent;
    }

    public LocalDate getRegDtm() {
        return regDtm;
    }

    public MemoHis regDtm(LocalDate regDtm) {
        this.regDtm = regDtm;
        return this;
    }

    public void setRegDtm(LocalDate regDtm) {
        this.regDtm = regDtm;
    }

    public Yn getUseYn() {
        return useYn;
    }

    public MemoHis useYn(Yn useYn) {
        this.useYn = useYn;
        return this;
    }

    public void setUseYn(Yn useYn) {
        this.useYn = useYn;
    }

    public CrmCustom getCrmCustom() {
        return crmCustom;
    }

    public MemoHis crmCustom(CrmCustom crmCustom) {
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
        if (!(o instanceof MemoHis)) {
            return false;
        }
        return id != null && id.equals(((MemoHis) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MemoHis{" +
            "id=" + getId() +
            ", memoContent='" + getMemoContent() + "'" +
            ", regDtm='" + getRegDtm() + "'" +
            ", useYn='" + getUseYn() + "'" +
            "}";
    }
}
