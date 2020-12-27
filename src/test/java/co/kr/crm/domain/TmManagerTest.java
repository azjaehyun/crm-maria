package co.kr.crm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.kr.crm.web.rest.TestUtil;

public class TmManagerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TmManager.class);
        TmManager tmManager1 = new TmManager();
        tmManager1.setId(1L);
        TmManager tmManager2 = new TmManager();
        tmManager2.setId(tmManager1.getId());
        assertThat(tmManager1).isEqualTo(tmManager2);
        tmManager2.setId(2L);
        assertThat(tmManager1).isNotEqualTo(tmManager2);
        tmManager1.setId(null);
        assertThat(tmManager1).isNotEqualTo(tmManager2);
    }
}
