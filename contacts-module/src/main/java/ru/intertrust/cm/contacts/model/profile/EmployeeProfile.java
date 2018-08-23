package ru.intertrust.cm.contacts.model.profile;

import ru.intertrust.cm.core.business.api.dto.Id;
import ru.intertrust.workfinder.annotation.DoField;
import ru.intertrust.workfinder.annotation.DoType;
import ru.intertrust.workfinder.model.BaseDomainObject;

import java.util.Date;

/**
 * Created by Vitaliy Orlov on 28.12.2016.
 */
@DoType(doTypeName = "employee_profile")
public class EmployeeProfile extends BaseProfile {

    @DoField(name = "person", type = DoField.FieldType.REFERENCE_ID)
    private Id person;

    public Id getPerson() {
        return person;
    }

    public void setPerson(Id person) {
        this.person = person;
    }
}
