package at.htl.todo.model;

public class Vehicle {
    public Long id;
    public String brand;
    public String model;
    public Integer year;
    public Vehicle() {
    }

    public Vehicle(Long id, String brand, String model, Integer year) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
    }
}

