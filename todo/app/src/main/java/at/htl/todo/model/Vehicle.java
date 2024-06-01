package at.htl.todo.model;

import org.jetbrains.annotations.NotNull;

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

    @NotNull
    public Vehicle copy(String brand, String model, String year) {
        this.brand = brand;
        this.model = model;
        this.year = Integer.parseInt(year);

        return this;
    }
}

