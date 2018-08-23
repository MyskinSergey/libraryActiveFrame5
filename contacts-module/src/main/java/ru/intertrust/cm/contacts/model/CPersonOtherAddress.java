package ru.intertrust.cm.contacts.model;

/**
 * Created by Vitaliy Orlov on 28.12.2016.
 */

import ru.intertrust.cm.contacts.model.converter.CPersonOtherAddressFiledConverter;
import ru.intertrust.cm.core.business.api.dto.Id;
import ru.intertrust.workfinder.annotation.DoField;
import ru.intertrust.workfinder.annotation.DoType;
import ru.intertrust.workfinder.model.BaseDomainObject;


@DoType(doTypeName = "Addr_Other_Address")
public class CPersonOtherAddress extends BaseDomainObject {

    @DoField(name = "Value", type = DoField.FieldType.STRING)
    private String value;


    @DoField(name = "Type_Value", type = DoField.FieldType.LONG, classConverter = CPersonOtherAddressFiledConverter.class)
    private ContCPersonOtherAddressType addressType;

    @DoField(name = "Contact_Person", type = DoField.FieldType.REFERENCE_ID)
    private Id contactPerson;

    @DoField(name = "title", type = DoField.FieldType.STRING)
    private String title;


    @DoField(name = "type", type = DoField.FieldType.STRING)
    private String type;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ContCPersonOtherAddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(ContCPersonOtherAddressType addressType) {
        this.addressType = addressType;
    }

    public Id getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(Id contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
