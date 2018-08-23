package ru.intertrust.cm.contacts.model;

/**
 * Created by Vitaliy Orlov on 28.12.2016.
 */
public enum ContCPersonOtherAddressType {
    EMAIL(2),
    PHONE(1);

    long value;
    ContCPersonOtherAddressType(long value){
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
