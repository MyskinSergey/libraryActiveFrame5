package ru.intertrust.cm.contacts.model.profile;

import ru.intertrust.cm.core.business.api.dto.Id;
import ru.intertrust.workfinder.annotation.DoField;
import ru.intertrust.workfinder.annotation.DoType;
import ru.intertrust.workfinder.model.BaseDomainObject;

/**
 * Created by Vitaliy Orlov on 28.12.2016.
 */
@DoType(doTypeName = "cont_contact_person")
public class SimplePerson extends BaseDomainObject {

    @DoField(name = "name", type = DoField.FieldType.STRING)
    private String firstName;

    @DoField(name = "surname", type = DoField.FieldType.STRING)
    private String lastName;

    @DoField(name = "patronymic", type = DoField.FieldType.STRING)
    private String middleName;

    @DoField(name = "person", type = DoField.FieldType.REFERENCE_ID)
    private Id person;

    public Id getPerson() {
        return person;
    }

    public void setPerson(Id person) {
        this.person = person;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
