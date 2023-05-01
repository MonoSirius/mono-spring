package org.springframework.beans;

/**
 * mono-spring
 *
 * @author MonoSirius
 * @date 2023/4/30
 */
public class BeansException extends RuntimeException{
    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
