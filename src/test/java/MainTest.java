import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class MainTest {
    @Test
    @Timeout(22)
    @Disabled
    void testMain_ShouldPass_WhenRunLess22Sec() throws Exception {
        Main.main(null);
    }
}