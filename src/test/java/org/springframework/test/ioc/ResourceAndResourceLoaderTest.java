package org.springframework.test.ioc;

import cn.hutool.core.io.IoUtil;
import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author MonoSirius
 * @date 2023/5/1
 */
public class ResourceAndResourceLoaderTest {

    @Test
    public void testResourceAndResourceLoaderTest() throws IOException {
        DefaultResourceLoader resourceLoader =  new DefaultResourceLoader();

        // 加载classpath下的资源
        Resource resource = resourceLoader.getResource("classpath:hello.txt");
        InputStream is = resource.getInputStream();
        String content = IoUtil.readUtf8(is);
        System.out.println(content);

        // 加载文件资源
        resource = resourceLoader.getResource("src/test/resources/hello.txt");
        is = resource.getInputStream();
        content = IoUtil.readUtf8(is);
        System.out.println(content);

        // url
        resource = resourceLoader.getResource("https://www.baidu.com");
        is = resource.getInputStream();
        content = IoUtil.readUtf8(is);
        System.out.println(content);
    }


}
