package co.kr.crm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.kr.crm.web.rest.TestUtil;

public class TeamGrpTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeamGrp.class);
        TeamGrp teamGrp1 = new TeamGrp();
        teamGrp1.setId(1L);
        TeamGrp teamGrp2 = new TeamGrp();
        teamGrp2.setId(teamGrp1.getId());
        assertThat(teamGrp1).isEqualTo(teamGrp2);
        teamGrp2.setId(2L);
        assertThat(teamGrp1).isNotEqualTo(teamGrp2);
        teamGrp1.setId(null);
        assertThat(teamGrp1).isNotEqualTo(teamGrp2);
    }
}
