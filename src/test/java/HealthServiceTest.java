import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class ClassForTestTest {

    private static HealthService healthService;
    private static final double WATER_DAY_NORM = 2.5;
    private static final int CALORIES_DAY_NORM = 2200;

    @BeforeClass
    public static void before() throws Exception {
        healthService = new HealthService(WATER_DAY_NORM, CALORIES_DAY_NORM);
    }

    @Test
    public void drinkTest() throws Exception {
        double drunkAmount = .200;
        double waterLeft = WATER_DAY_NORM - drunkAmount;
        healthService.drink(drunkAmount);
        assertThat(healthService.waterLeftToDrink(), is(waterLeft));
    }

    @Test
    public void eatTest() throws Exception {
        int caloriesAmount = 500;
        int caloriesLeft = CALORIES_DAY_NORM - caloriesAmount;
        healthService.eat(caloriesAmount);
        assertThat(healthService.caloriesLeftToEat(), is(caloriesLeft));
    }
}