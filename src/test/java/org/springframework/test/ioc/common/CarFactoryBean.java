package org.springframework.test.ioc.common;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.test.ioc.bean.Car;

/**
 * @author MonoSirius
 * @date 2023/5/3
 */
public class CarFactoryBean implements FactoryBean<Car> {
    private String brand;
    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public Car getObject() throws Exception {
        Car car = new Car();
        car.setBrand(brand);
        return car;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
