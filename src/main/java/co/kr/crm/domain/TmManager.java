package co.kr.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import co.kr.crm.domain.enumeration.Yn;

/**
 * TM 관리자 정보
 */
@ApiModel(description = "TM 관리자 정보")
@Entity
@Table(name = "tm_manager")
public class TmManager implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "corp_code")
    private String corpCode;

    @NotNull
    @Column(name = "tm_manager_name", nullable = false)
    private String tmManagerName;

    @Column(name = "tm_manager_phone_num")
    private String tmManagerPhoneNum;

    @Column(name = "team_code")
    private String teamCode;

    @Column(name = "crm_manage_cnt")
    private Integer crmManageCnt;

    @Enumerated(EnumType.STRING)
    @Column(name = "use_yn")
    private Yn useYn;

    @OneToMany(mappedBy = "tmManager")
    private Set<CrmCustom> crmCustoms = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "tmManagers", allowSetters = true)
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

    public TmManager corpCode(String corpCode) {
        this.corpCode = corpCode;
        return this;
    }

    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }

    public String getTmManagerName() {
        return tmManagerName;
    }

    public TmManager tmManagerName(String tmManagerName) {
        this.tmManagerName = tmManagerName;
        return this;
    }

    public void setTmManagerName(String tmManagerName) {
        this.tmManagerName = tmManagerName;
    }

    public String getTmManagerPhoneNum() {
        return tmManagerPhoneNum;
    }

    public TmManager tmManagerPhoneNum(String tmManagerPhoneNum) {
        this.tmManagerPhoneNum = tmManagerPhoneNum;
        return this;
    }

    public void setTmManagerPhoneNum(String tmManagerPhoneNum) {
        this.tmManagerPhoneNum = tmManagerPhoneNum;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public TmManager teamCode(String teamCode) {
        this.teamCode = teamCode;
        return this;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public Integer getCrmManageCnt() {
        return crmManageCnt;
    }

    public TmManager crmManageCnt(Integer crmManageCnt) {
        this.crmManageCnt = crmManageCnt;
        return this;
    }

    public void setCrmManageCnt(Integer crmManageCnt) {
        this.crmManageCnt = crmManageCnt;
    }

    public Yn getUseYn() {
        return useYn;
    }

    public TmManager useYn(Yn useYn) {
        this.useYn = useYn;
        return this;
    }

    public void setUseYn(Yn useYn) {
        this.useYn = useYn;
    }

    public Set<CrmCustom> getCrmCustoms() {
        return crmCustoms;
    }

    public TmManager crmCustoms(Set<CrmCustom> crmCustoms) {
        this.crmCustoms = crmCustoms;
        return this;
    }

    public TmManager addCrmCustom(CrmCustom crmCustom) {
        this.crmCustoms.add(crmCustom);
        crmCustom.setTmManager(this);
        return this;
    }

    public TmManager removeCrmCustom(CrmCustom crmCustom) {
        this.crmCustoms.remove(crmCustom);
        crmCustom.setTmManager(null);
        return this;
    }

    public void setCrmCustoms(Set<CrmCustom> crmCustoms) {
        this.crmCustoms = crmCustoms;
    }

    public TeamGrp getTeam() {
        return team;
    }

    public TmManager team(TeamGrp teamGrp) {
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
        if (!(o instanceof TmManager)) {
            return false;
        }
        return id != null && id.equals(((TmManager) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TmManager{" +
            "id=" + getId() +
            ", corpCode='" + getCorpCode() + "'" +
            ", tmManagerName='" + getTmManagerName() + "'" +
            ", tmManagerPhoneNum='" + getTmManagerPhoneNum() + "'" +
            ", teamCode='" + getTeamCode() + "'" +
            ", crmManageCnt=" + getCrmManageCnt() +
            ", useYn='" + getUseYn() + "'" +
            "}";
    }
}
