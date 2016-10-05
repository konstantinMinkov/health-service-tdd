import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

public class HealthService {

    private final int WATER_DAY_NORM;
    private final int CALORIES_DAY_NORM;
    private final int WALKING_MINUTES_DAY_NORM;
    private Map<LocalDate, Integer> waterDrunkEachDay;
    private Map<LocalDate, Integer> caloriesEatenEachDay;
    private Map<LocalDate, Integer> minutesWalkedEachDay;

    public HealthService(int waterDayNorm, int caloriesDayNorm,
                         int walkingMinutesDayNorm) {
        WATER_DAY_NORM = waterDayNorm;
        CALORIES_DAY_NORM = caloriesDayNorm;
        WALKING_MINUTES_DAY_NORM = walkingMinutesDayNorm;
        caloriesEatenEachDay = new HashMap<>();
        waterDrunkEachDay = new HashMap<>();
        minutesWalkedEachDay = new HashMap<>();
    }

    public void eat(LocalDateTime dateTime, int calories) {
        final LocalDate date = dateTime.toLocalDate();
        caloriesEatenEachDay.merge(date, calories, Integer::sum);
    }

    public void drink(LocalDateTime dateTime, int waterDrunk) {
        final LocalDate date = dateTime.toLocalDate();
        waterDrunkEachDay.merge(date, waterDrunk, Integer::sum);
    }

    public void walk(LocalDateTime dateTime, int minutesWalked) {
        final LocalDate date = dateTime.toLocalDate();
        minutesWalkedEachDay.merge(date, minutesWalked, Integer::sum);
    }

    public int waterLeftToDrink(LocalDate date) {
        final Integer waterDrunkThatDay = waterDrunkEachDay.get(date);
        int waterLeft = WATER_DAY_NORM
                - Optional.ofNullable(waterDrunkThatDay)
                .orElse(0);
        return (waterLeft > 0) ? waterLeft : 0;
    }

    public int caloriesLeftToEat(LocalDate date) {
        int caloriesLeft = CALORIES_DAY_NORM
                - Optional.ofNullable(caloriesEatenEachDay.get(date))
                .orElse(0);
        return caloriesLeft > 0 ? caloriesLeft : 0;
    }

    public int minutesLeftToWalk(LocalDate date) {
        int minutesLeft = WALKING_MINUTES_DAY_NORM
                - Optional.ofNullable(minutesWalkedEachDay.get(date))
                .orElse(0);
        return minutesLeft > 0 ? minutesLeft : 0;
    }

    public double dayWalkingPercentageLeft(LocalDate date) {
        return (double) minutesLeftToWalk(date) / WALKING_MINUTES_DAY_NORM;
    }

    public double dayEatingPercentageLeft(LocalDate date) {
        return (double) caloriesLeftToEat(date) / CALORIES_DAY_NORM;
    }

    public double dayDrinkingPercentageLeft(LocalDate date) {
        return (double) waterLeftToDrink(date) / WATER_DAY_NORM;
    }

    public double lastWeekDrinkingMedian(LocalDate date) {
        return lastWeekMedian(date, this::dayDrinkingPercentageLeft);
    }

    public double lastWeekWalkingMedian(LocalDate date) {
        return lastWeekMedian(date, this::dayWalkingPercentageLeft);
    }

    public double lastWeekEatingMedian(LocalDate date) {
        return lastWeekMedian(date, this::dayEatingPercentageLeft);
    }

    private double lastWeekMedian(LocalDate date, Function<LocalDate, Double> leftForToday) {
        final int middleOfWeek = 4;
        final int hundredPercents = 1;
        LocalDate dateWeekAgo = date.minusWeeks(1);
        List<Double> weekPercentage = new ArrayList<>();
        while (dateWeekAgo.isBefore(date)) {
            weekPercentage.add(hundredPercents - leftForToday.apply(date));
            date = date.minusDays(1);
        }
        weekPercentage.sort(Comparator.naturalOrder());
        return weekPercentage.get(middleOfWeek);
    }
}
