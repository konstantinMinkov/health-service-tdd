import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class HealthServiceTest {

    private static HealthService healthService;
    private static final int WATER_DAY_NORM = 2500;
    private static final int CALORIES_DAY_NORM = 2200;
    private static final int WALKING_MINUTES_DAY_NORM = 120;

    private int leftToDrink(int waterDrunk) {
        int waterLeft = WATER_DAY_NORM - waterDrunk;
        return waterLeft > 0 ? waterLeft : 0;
    }

    private int leftToEat(int eatenCalories) {
        int caloriesLeft = CALORIES_DAY_NORM - eatenCalories;
        return caloriesLeft > 0 ? caloriesLeft : 0;
    }

    private int leftToWalk(int minutesWalked) {
        int minutesLeft = WALKING_MINUTES_DAY_NORM - minutesWalked;
        return minutesLeft > 0 ? minutesLeft : 0;
    }

    @Before
    public void before() throws Exception {
        healthService = new HealthService(WATER_DAY_NORM, CALORIES_DAY_NORM,
                WALKING_MINUTES_DAY_NORM);
    }

    @Test
    public void drinkTest() throws Exception {
        int waterDrunk = 200;
        healthService.drink(LocalDateTime.now(), waterDrunk);
        assertThat(healthService.waterLeftToDrink(LocalDate.now()),
                is(leftToDrink(waterDrunk)));
    }

    @Test
    public void eatTest() throws Exception {
        int caloriesEaten = 500;
        healthService.eat(LocalDateTime.now(), caloriesEaten);
        assertThat(healthService.caloriesLeftToEat(LocalDate.now()),
                is(leftToEat(caloriesEaten)));
    }

    @Test
    public void walkTest() throws Exception {
        int minutesWalked = 20;
        healthService.walk(LocalDateTime.now(), minutesWalked);
        assertThat(healthService.minutesLeftToWalk(LocalDate.now()),
                is(leftToWalk(minutesWalked)));
    }

    @Test
    public void dayWalkingStatistics() {
        int minutesWalked = 2600;
        healthService.walk(LocalDateTime.now(), minutesWalked);
        assertThat(healthService.dayWalkingPercentageLeft(LocalDate.now()),
                is((double) leftToWalk(minutesWalked) / WALKING_MINUTES_DAY_NORM));
    }

    @Test
    public void dayCaloriesEatenStatistics() {
        int caloriesEaten = 500;
        healthService.eat(LocalDateTime.now(), caloriesEaten);
        assertThat(healthService.dayEatingPercentageLeft(LocalDate.now()),
                is((double) leftToEat(caloriesEaten) / CALORIES_DAY_NORM));
    }

    @Test
    public void dayWaterDrunkStatistics() {
        int waterDrunk = 200;
        healthService.drink(LocalDateTime.now(), waterDrunk);
        assertThat(healthService.dayDrinkingPercentageLeft(LocalDate.now()),
                is((double) leftToDrink(waterDrunk) / WATER_DAY_NORM));
    }

    @Test
    public void weekWalkingStatistics() {
        final int daysInWeek = 7;
        final int middleDayOfWeek = 4;
        final int hundredPercent = 1;
        List<Double> weekStatistics = new ArrayList<>(daysInWeek);
        Random random = new Random();
        LocalDateTime now = LocalDateTime.now();
        for (int i = 1; i <= daysInWeek; i++) {
            final int todayWalkingMinutes = random.nextInt(WALKING_MINUTES_DAY_NORM * 2);
            healthService.walk(now.plusDays(i), todayWalkingMinutes);
            weekStatistics.add(hundredPercent
                    - healthService.dayWalkingPercentageLeft(now.plusDays(i).toLocalDate()));
        }
        weekStatistics.sort(Comparator.naturalOrder());
        assertThat(healthService.lastWeekWalkingPercentage(now.plusWeeks(1).toLocalDate()),
                is(weekStatistics.get(middleDayOfWeek)));
    }

    @Test
    public void weekDrinkingStatistics() {
        final int daysInWeek = 7;
        final int middleDayOfWeek = 4;
        final int hundredPercent = 1;
        List<Double> weekStatistics = new ArrayList<>(daysInWeek);
        Random random = new Random();
        LocalDateTime now = LocalDateTime.now();
        for (int i = 1; i <= daysInWeek; i++) {
            final int todayDrinkingTime = random.nextInt(WATER_DAY_NORM * 2);
            healthService.drink(now.plusDays(i), todayDrinkingTime);
            weekStatistics.add(hundredPercent
                    - healthService.dayDrinkingPercentageLeft(now.plusDays(i).toLocalDate()));
        }
        weekStatistics.sort(Comparator.naturalOrder());
        assertThat(healthService.lastWeekDrinkingPercentage(now.plusWeeks(1).toLocalDate()),
                is(weekStatistics.get(middleDayOfWeek)));
    }

    @Test
    public void weekEatingStatistics() {
        final int daysInWeek = 7;
        final int middleDayOfWeek = 4;
        final int hundredPercent = 1;
        List<Double> weekStatistics = new ArrayList<>(daysInWeek);
        Random random = new Random();
        LocalDateTime now = LocalDateTime.now();
        for (int i = 1; i <= daysInWeek; i++) {
            final int todayAteCalories = random.nextInt(WATER_DAY_NORM * 2);
            healthService.drink(now.plusDays(i), todayAteCalories);
            weekStatistics.add(hundredPercent
                    - healthService.dayEatingPercentageLeft(now.plusDays(i).toLocalDate()));
        }
        weekStatistics.sort(Comparator.naturalOrder());
        assertThat(healthService.lastWeekEatingPercentage(now.plusWeeks(1).toLocalDate()),
                is(weekStatistics.get(middleDayOfWeek)));
    }
}