package co.kr.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import co.kr.crm.domain.enumeration.Yn;

/**
 * 매지너(영업자) 정보
 */
@ApiModel(description = "매지너(영업자) 정보")
@Entity
@Table(name = "manager")
public class Manager implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "corp_code")
    private String corpCode;

    @NotNull
    @Column(name = "manager_name", nullable = false)
    private String managerName;

    @Column(name = "manager_phone_num")
    private String managerPhoneNum;

    @Column(name = "team_code")
    private String teamCode;

    @Column(name = "total_sales_amount")
    private Integer totalSalesAmount;

    @Column(name = "enter_day")
    private LocalDate enterDay;

    @Column(name = "out_day")
    private LocalDate outDay;

    @Enumerated(EnumType.STRING)
    @Column(name = "use_yn")
    private Yn useYn;

    @OneToMany(mappedBy = "manager")
    private Set<CrmCustom> crmCustoms = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "managers", allowSetters = true)
    private TeamGrp team;

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

    public Manager corpCode(String corpCode) {
        this.corpCode = corpCode;
        return this;
    }

    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }

    public String getManagerName() {
        return managerName;
    }

    public Manager managerName(String managerName) {
        this.managerName = managerName;
        return this;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerPhoneNum() {
        return managerPhoneNum;
    }

    public Manager managerPhoneNum(String managerPhoneNum) {
        this.managerPhoneNum = managerPhoneNum;
        return this;
    }

    public void setManagerPhoneNum(String managerPhoneNum) {
        this.managerPhoneNum = managerPhoneNum;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public Manager teamCode(String teamCode) {
        this.teamCode = teamCode;
        return this;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public Integer getTotalSalesAmount() {
        return totalSalesAmount;
    }

    public Manager totalSalesAmount(Integer totalSalesAmount) {
        this.totalSalesAmount = totalSalesAmount;
        return this;
    }

    public void setTotalSalesAmount(Integer totalSalesAmount) {
        this.totalSalesAmount = totalSalesAmount;
    }

    public LocalDate getEnterDay() {
        return enterDay;
    }

    public Manager enterDay(LocalDate enterDay) {
        this.enterDay = enterDay;
        return this;
    }

    public void setEnterDay(LocalDate enterDay) {
        this.enterDay = enterDay;
    }

    public LocalDate getOutDay() {
        return outDay;
    }

    public Manager outDay(LocalDate outDay) {
        this.outDay = outDay;
        return this;
    }

    public void setOutDay(LocalDate outDay) {
        this.outDay = outDay;
    }

    public Yn getUseYn() {
        return useYn;
    }

    public Manager useYn(Yn useYn) {
        this.useYn = useYn;
        return this;
    }

    public void setUseYn(Yn useYn) {
        this.useYn = useYn;
    }

    public Set<CrmCustom> getCrmCustoms() {
        return crmCustoms;
    }

    public Manager crmCustoms(Set<CrmCustom> crmCustoms) {
        this.crmCustoms = crmCustoms;
        return this;
    }

    public Manager addCrmCustom(CrmCustom crmCustom) {
        this.crmCustoms.add(crmCustom);
        crmCustom.setManager(this);
        return this;
    }

    public Manager removeCrmCustom(CrmCustom crmCustom) {
        this.crmCustoms.remove(crmCustom);
        crmCustom.setManager(null);
        return this;
    }

    public void setCrmCustoms(Set<CrmCustom> crmCustoms) {
        this.crmCustoms = crmCustoms;
    }

    public TeamGrp getTeam() {
        return team;
    }

    public Manager team(TeamGrp teamGrp) {
        this.team = teamGrp;
        return this;
    }

    public void setTeam(TeamGrp teamGrp) {
        this.team = teamGrp;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Manager)) {
            return false;
        }
        return id != null && id.equals(((Manager) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Manager{" +
            "id=" + getId() +
            ", corpCode='" + getCorpCode() + "'" +
            ", managerName='" + getManagerName() + "'" +
            ", managerPhoneNum='" + getManagerPhoneNum() + "'" +
            ", teamCode='" + getTeamCode() + "'" +
            ", totalSalesAmount=" + getTotalSalesAmount() +
            ", enterDay='" + getEnterDay() + "'" +
            ", outDay='" + getOutDay() + "'" +
            ", useYn='" + getUseYn() + "'" +
            "}";
    }
}
