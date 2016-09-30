import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HealthService {

    private final double WATER_DAY_NORM;
    private final int CALORIES_DAY_NORM;
    private final int WALKING_MINUTES_DAY_NORM;
    private Map<LocalDate, Optional<Double>> waterAmountDrunk;
    private Map<LocalDate, Optional<Integer>> caloriesAmountAte;
    private Map<LocalDate, Optional<Integer>> minutesWalked;

    public HealthService(double waterDayNorm, int caloriesDayNorm,
                         int walkingMinutesDayNorm) {
        WATER_DAY_NORM = waterDayNorm;
        CALORIES_DAY_NORM = caloriesDayNorm;
        WALKING_MINUTES_DAY_NORM = walkingMinutesDayNorm;
        caloriesAmountAte = new HashMap<>();
        waterAmountDrunk = new HashMap<>();
        minutesWalked = new HashMap<>();
    }

    public void eat(LocalDateTime dateTime, int calories) {
        int totalCalories = calories;
        final LocalDate date = dateTime.toLocalDate();
        if (caloriesAmountAte.containsKey(date)) {
            totalCalories += caloriesAmountAte.get(date).orElse(0);
        }
        caloriesAmountAte.put(date, Optional.ofNullable(totalCalories));
    }

    public void drink(LocalDateTime dateTime, double amount) {
        double totalAmount = amount;
        final LocalDate date = dateTime.toLocalDate();
        if (waterAmountDrunk.containsKey(date)) {
            totalAmount += waterAmountDrunk.get(date).orElse(.0);
        }
        waterAmountDrunk.put(date, Optional.ofNullable(totalAmount));
    }

    public void walk(LocalDateTime dateTime, int minutesToWalk) {
        int totalMinutes = minutesToWalk;
        final LocalDate date = dateTime.toLocalDate();
        if (minutesWalked.containsKey(date)) {
            totalMinutes += minutesWalked.get(date).orElse(0);
        }
        minutesWalked.put(date, Optional.ofNullable(totalMinutes));
    }

    public double waterLeftToDrink(LocalDateTime dateTime) {
        final LocalDate date = dateTime.toLocalDate();
        return WATER_DAY_NORM - waterAmountDrunk.get(date).orElse(.0);
    }

    public int caloriesLeftToEat(LocalDateTime dateTime) {
        final LocalDate date = dateTime.toLocalDate();
        return CALORIES_DAY_NORM - caloriesAmountAte.get(date).orElse(0);
    }

    public int minutesLeftToWalk(LocalDateTime dateTime) {
        final LocalDate date = dateTime.toLocalDate();
        return WALKING_MINUTES_DAY_NORM - minutesWalked.get(date).orElse(0);
    }


}
