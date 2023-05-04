package org.springframework.test.service.impl;

import org.springframework.test.service.WorldService;

/**
 * @author MonoSirius
 * @date 2023/5/4
 */
public class WorldServiceImpl implements WorldService {
    @Override
    public void explode() {
        System.out.println("The Earth is going to explode");
    }
}
