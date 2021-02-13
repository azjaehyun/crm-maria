package co.kr.crm.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import co.kr.crm.domain.enumeration.Yn;

/**
 * 회사 정보
 */
@ApiModel(description = "회사 정보")
@Entity
@Table(name = "corp")
public class Corp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "corp_code", nullable = false)
    private String corpCode;

    @NotNull
    @Column(name = "corp_name", nullable = false)
    private String corpName;

    @Enumerated(EnumType.STRING)
    @Column(name = "use_yn")
    private Yn useYn;

    /**
     * Owner (1) -----> (*) Car Person{drivedCar} to Car{driver}
     */
    @ApiModelProperty(value = "Owner (1) -----> (*) Car Person{drivedCar} to Car{driver}")
    @OneToMany(mappedBy = "corp")
    private Set<TeamGrp> teamGrps = new HashSet<>();

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

    public Corp corpCode(String corpCode) {
        this.corpCode = corpCode;
        return this;
    }

    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }

    public String getCorpName() {
        return corpName;
    }

    public Corp corpName(String corpName) {
        this.corpName = corpName;
        return this;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public Yn getUseYn() {
        return useYn;
    }

    public Corp useYn(Yn useYn) {
        this.useYn = useYn;
        return this;
    }

    public void setUseYn(Yn useYn) {
        this.useYn = useYn;
    }

    public Set<TeamGrp> getTeamGrps() {
        return teamGrps;
    }

    public Corp teamGrps(Set<TeamGrp> teamGrps) {
        this.teamGrps = teamGrps;
        return this;
    }

    public Corp addTeamGrp(TeamGrp teamGrp) {
        this.teamGrps.add(teamGrp);
        teamGrp.setCorp(this);
        return this;
    }

    public Corp removeTeamGrp(TeamGrp teamGrp) {
        this.teamGrps.remove(teamGrp);
        teamGrp.setCorp(null);
        return this;
    }

    public void setTeamGrps(Set<TeamGrp> teamGrps) {
        this.teamGrps = teamGrps;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Corp)) {
            return false;
        }
        return id != null && id.equals(((Corp) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Corp{" +
            "id=" + getId() +
            ", corpCode='" + getCorpCode() + "'" +
            ", corpName='" + getCorpName() + "'" +
            ", useYn='" + getUseYn() + "'" +
            "}";
    }
}
