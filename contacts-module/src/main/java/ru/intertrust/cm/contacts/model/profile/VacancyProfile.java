package ru.intertrust.cm.contacts.model.profile;

import ru.intertrust.cm.core.business.api.dto.Id;
import ru.intertrust.workfinder.annotation.DoField;
import ru.intertrust.workfinder.annotation.DoType;

/**
 * Created by Vitaliy Orlov on 28.12.2016.
 */
@DoType(doTypeName = "vacancy")
public class VacancyProfile extends BaseProfile {

    @DoField(name = "person", type = DoField.FieldType.REFERENCE_ID)
    private Id person;

    @DoField(name = "organization", type = DoField.FieldType.REFERENCE_ID)
    private Id organization;

    public Id getPerson() {
        return person;
    }

    public void setPerson(Id person) {
        this.person = person;
    }

    public Id getOrganization() {
        return organization;
    }

    public void setOrganization(Id organization) {
        this.organization = organization;
    }
}
