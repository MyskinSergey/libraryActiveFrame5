package ru.intertrust.cm.contacts.model.profile;

import ru.intertrust.cm.core.business.api.dto.Id;
import ru.intertrust.workfinder.annotation.DoField;
import ru.intertrust.workfinder.annotation.DoType;
import ru.intertrust.workfinder.model.BaseDomainObject;

/**
 * Created by Vitaliy Orlov on 11.01.2017.
 */
@DoType(doTypeName = "profile_qualifier")
public class ProfileQualifier extends BaseDomainObject {

    @DoField(name = "qualifier", type = DoField.FieldType.REFERENCE_ID)
    private Id qualifier;

    @DoField(name = "profile", type = DoField.FieldType.REFERENCE_ID)
    private Id profile;

    public Id getQualifier() {
        return qualifier;
    }

    public void setQualifier(Id qualifier) {
        this.qualifier = qualifier;
    }

    public Id getProfile() {
        return profile;
    }

    public void setProfile(Id profile) {
        this.profile = profile;
    }
}
