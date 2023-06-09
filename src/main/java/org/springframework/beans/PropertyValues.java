package org.springframework.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * mono-spring
 *
 * @author MonoSirius
 * @date 2023/5/1
 */
public class PropertyValues {
    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    public void addPropertyValue(PropertyValue pv) {
        // 覆盖属性
        for (int i = 0; i < propertyValueList.size(); ++i) {
            PropertyValue currPv = propertyValueList.get(i);
            // 属性名相同,进行覆盖
            if (currPv.getName().equals(pv.getName())) {
                propertyValueList.set(i, pv);
                return;
            }
        }

        // 添加属性
        propertyValueList.add(pv);
    }

    public PropertyValue[] getPropertyValues() {
        return propertyValueList.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue pv : propertyValueList) {
            if (pv.getName().equals(propertyName)) {
                return pv;
            }
        }
        return null;
    }
}
