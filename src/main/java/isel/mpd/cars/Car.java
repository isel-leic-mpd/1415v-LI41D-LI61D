package isel.mpd.cars;

/**
 * Created by lfalcao on 04/03/2015.
 */
public class Car {
    private String brand;
    private String model;
    private String color;
    private int year;
    private int weight;

    public Car(String brand, String model, int year, String color) {
        this(brand, model, color, year, 1000);
    }

    public Car(String brand, String model, String color, int year, int weight) {
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.year = year;
        this.weight = weight;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", year=" + year +
                '}';
    }

    public int getWeight() {
        return weight;
    }
}
