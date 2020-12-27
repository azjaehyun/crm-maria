package co.kr.crm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.kr.crm.web.rest.TestUtil;

public class StockConsultingHisTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockConsultingHis.class);
        StockConsultingHis stockConsultingHis1 = new StockConsultingHis();
        stockConsultingHis1.setId(1L);
        StockConsultingHis stockConsultingHis2 = new StockConsultingHis();
        stockConsultingHis2.setId(stockConsultingHis1.getId());
        assertThat(stockConsultingHis1).isEqualTo(stockConsultingHis2);
        stockConsultingHis2.setId(2L);
        assertThat(stockConsultingHis1).isNotEqualTo(stockConsultingHis2);
        stockConsultingHis1.setId(null);
        assertThat(stockConsultingHis1).isNotEqualTo(stockConsultingHis2);
    }
}
