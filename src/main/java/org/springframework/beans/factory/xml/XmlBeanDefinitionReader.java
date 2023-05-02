package org.springframework.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

/**
 * 读取配置在xml文件中的bean定义信息
 *
 * @author MonoSirius
 * @date 2023/5/1
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
    public static final String BEAN_ELEMENT = "bean";
    public static final String PROPERTY_ELEMENT = "property";
    public static final String ID_ATTRIBUTE = "id";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String REF_ATTRIBUTE = "ref";

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        Resource resource = getResourceLoader().getResource(location);
        loadBeanDefinitions(resource);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try {
            InputStream is = resource.getInputStream();
            try {
                doLoadBeanDefinitions(is);
            } finally {
                is.close();
            }
        } catch (IOException e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }
    }

    private void doLoadBeanDefinitions(InputStream is) {
        // 读取xml文件
        Document document = XmlUtil.readXML(is);

        Element root = document.getDocumentElement();
        NodeList childNodes = root.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); ++i) {

            if (!(childNodes.item(i) instanceof Element)) {
                continue;
            }

            if (BEAN_ELEMENT.equals(childNodes.item(i).getNodeName())) {
                // 1. 解析bean标签
                Element bean = (Element) childNodes.item(i);
                String id = bean.getAttribute(ID_ATTRIBUTE);
                String name = bean.getAttribute(NAME_ATTRIBUTE);
                String className = bean.getAttribute(CLASS_ATTRIBUTE);

                Class<?> clazz = null;

                // 2. 尝试加载类
                try {
                    clazz = Class.forName(className);
                } catch (ClassNotFoundException e) {
                    throw new BeansException("cannot find class [" + className + "]");
                }

                // 3. 获取beanName
                // id优先于name
                String beanName = StrUtil.isNotEmpty(id) ? id : name;
                if (StrUtil.isEmpty(beanName)) {
                    // id 和 name 都为空, 将类名第一个字母小写
                    beanName = StrUtil.lowerFirst(clazz.getSimpleName());
                }
                // 4. 初始化BeanDefinition
                BeanDefinition beanDefinition = new BeanDefinition(clazz);

                // 5. 填充属性
                for (int j = 0; j < bean.getChildNodes().getLength(); ++j) {
                    if (!(bean.getChildNodes().item(j) instanceof Element)) {
                        continue;
                    }
                    if (PROPERTY_ELEMENT.equals(bean.getChildNodes().item(j).getNodeName())) {
                        // 解析Property标签
                        Element property = (Element) bean.getChildNodes().item(j);
                        String nameAttribute = property.getAttribute(NAME_ATTRIBUTE);
                        String valueAttribute = property.getAttribute(VALUE_ATTRIBUTE);
                        String refAttribute = property.getAttribute(REF_ATTRIBUTE);

                        if (StrUtil.isEmpty(nameAttribute)) {
                            throw new BeansException("The name attribute cannot be null or empty");
                        }
                        Object value = valueAttribute;
                        //  引用类型
                        if (StrUtil.isNotEmpty(refAttribute)) {
                            value = new BeanReference(refAttribute);
                        }
                        PropertyValue propertyValue = new PropertyValue(nameAttribute, value);
                        beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
                    }
                }

                // 6.
                if (getRegistry().containsBeanDefinition(beanName)) {
                    //beanName不能重名
                    throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
                }

                // 7. 注册beanDefinition
                getRegistry().registerBeanDefinition(beanName, beanDefinition);
            }
        }
    }


}
