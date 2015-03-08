import java.util.ArrayList;

/**
 * Created by lfalcao on 04/03/2015.
 */
public class CarsAlgorithms {
    public Iterable<Car> getCarsAboveYear(Iterable<Car> cars, int minumalYear) {
        ArrayList<Car> filteredCars = new ArrayList<>();
        for (Car c : cars) {
            if (c.getYear() > minumalYear) {
                filteredCars.add(c);
            }
        }
        return filteredCars;
    }

    public Iterable<Car> getCarsAboveYear(Iterable<Car> cars, String color) {
        ArrayList<Car> filteredCars = new ArrayList<>();
        for (Car c : cars) {
            if (c.getColor().equals(color)) {
                filteredCars.add(c);
            }
        }
        return filteredCars;
    }
}

