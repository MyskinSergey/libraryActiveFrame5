package ru.intertrust.cm.contacts.model;

import ru.intertrust.cm.core.business.api.dto.Id;
import ru.intertrust.workfinder.annotation.DoField;
import ru.intertrust.workfinder.annotation.DoType;
import ru.intertrust.workfinder.model.BaseDomainObject;

import java.util.Date;

/**
 * Created by Vitaliy Orlov on 28.12.2016.
 */
@DoType(doTypeName = "cont_cperson_org")
public class ContCPersonOrg extends BaseDomainObject {

    @DoField(name = "description", type = DoField.FieldType.STRING)
    private String description;


    @DoField(name = "position", type = DoField.FieldType.STRING)
    private String position;

    @DoField(name = "position1", type = DoField.FieldType.STRING)
    private String position1;

    @DoField(name = "type_accessory", type = DoField.FieldType.REFERENCE_ID)
    private Id typeAccessory;

    @DoField(name = "contact_person", type = DoField.FieldType.REFERENCE_ID)
    private Id contactPerson;

    @DoField(name = "organization", type = DoField.FieldType.REFERENCE_ID)
    private Id organization;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition1() {
        return position1;
    }

    public void setPosition1(String position1) {
        this.position1 = position1;
    }

    public Id getTypeAccessory() {
        return typeAccessory;
    }

    public void setTypeAccessory(Id typeAccessory) {
        this.typeAccessory = typeAccessory;
    }

    public Id getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(Id contactPerson) {
        this.contactPerson = contactPerson;
    }

    public Id getOrganization() {
        return organization;
    }

    public void setOrganization(Id organization) {
        this.organization = organization;
    }
}
