package ru.intertrust.workfinder.rest.model;

import ru.intertrust.cm.core.business.api.dto.LongValue;
import ru.intertrust.cm.core.business.api.dto.Value;
import ru.intertrust.cm.core.gui.impl.server.widget.EnumerationMapProvider;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Vitaliy Orlov on 09.01.2017.
 */
@XmlRootElement(name = "EnumValue")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class EnumPropertyValue implements DataResponse {
    @XmlElement
    private Long key;
    @XmlElement
    private String value;

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public static Long getKeyFromValue(EnumPropertyValue value){
        return value != null ? value.getKey() : null;
    }

    public static List<EnumPropertyValue> loadFromMapProvider(EnumerationMapProvider mapProvider){
        List<EnumPropertyValue> result = new ArrayList<>();
        Map<String, Value> properties =  mapProvider.getMap(null);
        if(properties != null){
            for(Map.Entry<String, Value> entry : properties.entrySet()){
                if(entry.getValue() instanceof LongValue){
                    EnumPropertyValue value = new EnumPropertyValue();
                    value.setValue(entry.getKey());
                    value.setKey(((LongValue)entry.getValue()).get());
                    result.add(value);
                }
            }
        }
        return result;
    }

    public static EnumPropertyValue loadFromMapProvider(EnumerationMapProvider mapProvider, Long enumKeyValue){
        if(enumKeyValue == null){
            return null;
        }
        Map<String, Value> properties =  mapProvider.getMap(null);
        if(properties != null){
            for(Map.Entry<String, Value> entry : properties.entrySet()){
                if(entry.getValue() instanceof LongValue && enumKeyValue.equals(((LongValue)entry.getValue()).get())){

                    EnumPropertyValue value = new EnumPropertyValue();
                    value.setValue(entry.getKey());
                    value.setKey(((LongValue)entry.getValue()).get());
                    return value;
                }
            }
        }
        return null;
    }

}
