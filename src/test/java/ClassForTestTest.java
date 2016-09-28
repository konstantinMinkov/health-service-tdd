import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by Kostiantyn_Minkov on 9/28/2016.
 */
public class ClassForTestTest {

    private static HealthService healthService;

    @BeforeClass
    public static void before() throws Exception {
        healthService = new HealthService();
    }

}