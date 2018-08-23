package ru.intertrust.cm.contacts.model.converter;


import ru.intertrust.cm.contacts.model.ContCPersonOtherAddressType;
import ru.intertrust.workfinder.annotation.DoFieldValueConverter;

/**
 * Created by Vitaliy Orlov on 20.12.2016.
 */
public class CPersonOtherAddressFiledConverter implements DoFieldValueConverter<Long, ContCPersonOtherAddressType> {
    @Override
    public Long convertToDomain(ContCPersonOtherAddressType value) {
        return value == null ? -1 : new Long(value.getValue());
    }

    @Override
    public ContCPersonOtherAddressType convertToModel(Long value) {
        for(ContCPersonOtherAddressType type : ContCPersonOtherAddressType.values()){
            if(type.getValue() == value){
                return type;
            }
        }
      return null;
    }
}