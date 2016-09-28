

public class HealthService {

    private final double WATER_DAY_NORM;
    private final int CALORIES_DAY_NORM;
    private double waterAmountDrunk;
    private int caloriesAmountAte;

    public HealthService(double waterDayNorm, int caloriesDayNorm) {
        WATER_DAY_NORM = waterDayNorm;
        CALORIES_DAY_NORM = caloriesDayNorm;
    }

    public void drink(double amount) {
        waterAmountDrunk += amount;
    }

    public double waterLeftToDrink() {
        return WATER_DAY_NORM - waterAmountDrunk;
    }

    public void eat(int calories) {
        caloriesAmountAte += calories;
    }

    public int caloriesLeftToEat() {
        return CALORIES_DAY_NORM - caloriesAmountAte;
    }
}
