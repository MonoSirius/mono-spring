package org.springframework.test.ioc.bean;

/**
 * mono-spring
 * <p>
 * created by https://github.com/MonoSirius 11:28
 * copyright (c) USTC. All rights reserved.
 * 2023/5/1
 */
public class Car {
    private String brand;

    public Car() {
    }

    public Car(String brand) {
        this.brand = brand;
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
                '}';
    }
}
