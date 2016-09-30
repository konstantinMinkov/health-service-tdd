import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class HealthServiceTest {

    private static HealthService healthService;
    private static final double WATER_DAY_NORM = 2.5;
    private static final int CALORIES_DAY_NORM = 2200;
    private static final int WALKING_MINUTES_DAY_NORM = 120;

    @BeforeClass
    public static void before() throws Exception {
        healthService = new HealthService(WATER_DAY_NORM, CALORIES_DAY_NORM,
                WALKING_MINUTES_DAY_NORM);
    }

    @Test
    public void drinkTest() throws Exception {
        double drunkAmount = .200;
        double waterLeft = WATER_DAY_NORM - drunkAmount;
        healthService.drink(LocalDateTime.now(), drunkAmount);
        assertThat(healthService.waterLeftToDrink(LocalDateTime.now()), is(waterLeft));
    }

    @Test
    public void eatTest() throws Exception {
        int caloriesAmount = 500;
        int caloriesLeft = CALORIES_DAY_NORM - caloriesAmount;
        healthService.eat(LocalDateTime.now(), caloriesAmount);
        assertThat(healthService.caloriesLeftToEat(LocalDateTime.now()), is(caloriesLeft));
    }

    @Test
    public void walkTest() throws Exception {
        int minutesWalked = 20;
        int minutesToWalk = WALKING_MINUTES_DAY_NORM - minutesWalked;
        healthService.walk(LocalDateTime.now(), minutesWalked);
        assertThat(healthService.minutesLeftToWalk(LocalDateTime.now()), is(minutesToWalk));
    }
}