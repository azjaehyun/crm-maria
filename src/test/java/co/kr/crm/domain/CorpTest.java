package co.kr.crm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.kr.crm.web.rest.TestUtil;

public class CorpTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Corp.class);
        Corp corp1 = new Corp();
        corp1.setId(1L);
        Corp corp2 = new Corp();
        corp2.setId(corp1.getId());
        assertThat(corp1).isEqualTo(corp2);
        corp2.setId(2L);
        assertThat(corp1).isNotEqualTo(corp2);
        corp1.setId(null);
        assertThat(corp1).isNotEqualTo(corp2);
    }
}
