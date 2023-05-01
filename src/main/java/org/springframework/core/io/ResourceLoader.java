package org.springframework.core.io;

/**
 * 资源加载器接口
 *
 * @author MonoSirius
 * @date 2023/5/1
 */
public interface ResourceLoader {
    Resource getResource(String location);
}
