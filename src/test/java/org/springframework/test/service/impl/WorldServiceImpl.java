package org.springframework.test.service.impl;

import org.springframework.test.service.WorldService;

/**
 * @author MonoSirius
 * @date 2023/5/4
 */
public class WorldServiceImpl implements WorldService {
    private String name;
    @Override
    public void explode() {
        System.out.println("The " + name + " is going to explode");
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }
}
