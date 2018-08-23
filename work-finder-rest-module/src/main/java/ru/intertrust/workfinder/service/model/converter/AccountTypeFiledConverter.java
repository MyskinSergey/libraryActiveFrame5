package ru.intertrust.workfinder.service.model.converter;


import ru.intertrust.workfinder.annotation.DoFieldValueConverter;
import ru.intertrust.workfinder.service.model.AccountType;

/**
 * Created by Vitaliy Orlov on 20.12.2016.
 */
public class AccountTypeFiledConverter implements DoFieldValueConverter<Long, AccountType> {
    @Override
    public Long convertToDomain(AccountType value) {
        return value == null ? -1 : new Long(value.ordinal());
    }

    @Override
    public AccountType convertToModel(Long value) {
        if(value == null || value == -1 || value >= AccountType.values().length){
            return null;
        }
        return AccountType.values()[value.intValue()];
    }
}