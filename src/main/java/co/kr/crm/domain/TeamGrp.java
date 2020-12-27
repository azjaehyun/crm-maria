package co.kr.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import co.kr.crm.domain.enumeration.Yn;

/**
 * 영업자 팀명
 */
@ApiModel(description = "영업자 팀명")
@Entity
@Table(name = "team_grp")
public class TeamGrp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "team_code")
    private String teamCode;

    @Column(name = "team_name")
    private String teamName;

    @Enumerated(EnumType.STRING)
    @Column(name = "use_yn")
    private Yn useYn;

    @OneToMany(mappedBy = "team")
    private Set<Manager> managers = new HashSet<>();

    @OneToMany(mappedBy = "team")
    private Set<TmManager> tmManagers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "teamGrps", allowSetters = true)
    private Corp corp;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public TeamGrp teamCode(String teamCode) {
        this.teamCode = teamCode;
        return this;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public String getTeamName() {
        return teamName;
    }

    public TeamGrp teamName(String teamName) {
        this.teamName = teamName;
        return this;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Yn getUseYn() {
        return useYn;
    }

    public TeamGrp useYn(Yn useYn) {
        this.useYn = useYn;
        return this;
    }

    public void setUseYn(Yn useYn) {
        this.useYn = useYn;
    }

    public Set<Manager> getManagers() {
        return managers;
    }

    public TeamGrp managers(Set<Manager> managers) {
        this.managers = managers;
        return this;
    }

    public TeamGrp addManager(Manager manager) {
        this.managers.add(manager);
        manager.setTeam(this);
        return this;
    }

    public TeamGrp removeManager(Manager manager) {
        this.managers.remove(manager);
        manager.setTeam(null);
        return this;
    }

    public void setManagers(Set<Manager> managers) {
        this.managers = managers;
    }

    public Set<TmManager> getTmManagers() {
        return tmManagers;
    }

    public TeamGrp tmManagers(Set<TmManager> tmManagers) {
        this.tmManagers = tmManagers;
        return this;
    }

    public TeamGrp addTmManager(TmManager tmManager) {
        this.tmManagers.add(tmManager);
        tmManager.setTeam(this);
        return this;
    }

    public TeamGrp removeTmManager(TmManager tmManager) {
        this.tmManagers.remove(tmManager);
        tmManager.setTeam(null);
        return this;
    }

    public void setTmManagers(Set<TmManager> tmManagers) {
        this.tmManagers = tmManagers;
    }

    public Corp getCorp() {
        return corp;
    }

    public TeamGrp corp(Corp corp) {
        this.corp = corp;
        return this;
    }

    public void setCorp(Corp corp) {
        this.corp = corp;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeamGrp)) {
            return false;
        }
        return id != null && id.equals(((TeamGrp) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeamGrp{" +
            "id=" + getId() +
            ", teamCode='" + getTeamCode() + "'" +
            ", teamName='" + getTeamName() + "'" +
            ", useYn='" + getUseYn() + "'" +
            "}";
    }
}
