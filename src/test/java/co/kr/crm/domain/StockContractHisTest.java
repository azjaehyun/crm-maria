package co.kr.crm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.kr.crm.web.rest.TestUtil;

public class StockContractHisTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockContractHis.class);
        StockContractHis stockContractHis1 = new StockContractHis();
        stockContractHis1.setId(1L);
        StockContractHis stockContractHis2 = new StockContractHis();
        stockContractHis2.setId(stockContractHis1.getId());
        assertThat(stockContractHis1).isEqualTo(stockContractHis2);
        stockContractHis2.setId(2L);
        assertThat(stockContractHis1).isNotEqualTo(stockContractHis2);
        stockContractHis1.setId(null);
        assertThat(stockContractHis1).isNotEqualTo(stockContractHis2);
    }
}
