package org.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 资源的抽象和访问接口
 *
 * @author MonoSirius
 * @date 2023/5/1
 */
public interface Resource {
    InputStream getInputStream() throws IOException;
}
