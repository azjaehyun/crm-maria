package co.kr.crm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.kr.crm.web.rest.TestUtil;

public class SendSmsHisTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SendSmsHis.class);
        SendSmsHis sendSmsHis1 = new SendSmsHis();
        sendSmsHis1.setId(1L);
        SendSmsHis sendSmsHis2 = new SendSmsHis();
        sendSmsHis2.setId(sendSmsHis1.getId());
        assertThat(sendSmsHis1).isEqualTo(sendSmsHis2);
        sendSmsHis2.setId(2L);
        assertThat(sendSmsHis1).isNotEqualTo(sendSmsHis2);
        sendSmsHis1.setId(null);
        assertThat(sendSmsHis1).isNotEqualTo(sendSmsHis2);
    }
}
