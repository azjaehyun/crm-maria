package co.kr.crm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.kr.crm.web.rest.TestUtil;

public class CrmCustomTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CrmCustom.class);
        CrmCustom crmCustom1 = new CrmCustom();
        crmCustom1.setId(1L);
        CrmCustom crmCustom2 = new CrmCustom();
        crmCustom2.setId(crmCustom1.getId());
        assertThat(crmCustom1).isEqualTo(crmCustom2);
        crmCustom2.setId(2L);
        assertThat(crmCustom1).isNotEqualTo(crmCustom2);
        crmCustom1.setId(null);
        assertThat(crmCustom1).isNotEqualTo(crmCustom2);
    }
}
