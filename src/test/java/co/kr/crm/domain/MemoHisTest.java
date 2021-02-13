package co.kr.crm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.kr.crm.web.rest.TestUtil;

public class MemoHisTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MemoHis.class);
        MemoHis memoHis1 = new MemoHis();
        memoHis1.setId(1L);
        MemoHis memoHis2 = new MemoHis();
        memoHis2.setId(memoHis1.getId());
        assertThat(memoHis1).isEqualTo(memoHis2);
        memoHis2.setId(2L);
        assertThat(memoHis1).isNotEqualTo(memoHis2);
        memoHis1.setId(null);
        assertThat(memoHis1).isNotEqualTo(memoHis2);
    }
}
