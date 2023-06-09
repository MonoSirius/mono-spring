package org.springframework.test.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * mono-spring
 *
 * @author MonoSirius
 * @date 2023/5/1
 */
public class Person implements DisposableBean, InitializingBean {
    private String name;
    private Integer age;

    private Car car;
    public Person() {
    }

    public Person(String name, Integer age, Car car) {
        this.name = name;
        this.age = age;
        this.car = car;
    }

    public void customInitMethod() {
        System.out.println("I was born in the method named customInitMethod");
    }

    public void customDestroyMethod() {
        System.out.println("I died in the method named customDestroyMethod");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("I died in the method named destroy");
    }

    @Override
    public void afterPropertiesSet() throws BeansException {
        System.out.println("I was born in the method named afterPropertiesSet");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", car=" + car +
                '}';
    }
}
