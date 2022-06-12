import org.junit.Test;

import me.hp.meutils.utils.DecimalUtils;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        int a = 10160;
        int b = 10000;
        try {
            System.out.println(DecimalUtils.BigDecimal.div(a,b,0));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
