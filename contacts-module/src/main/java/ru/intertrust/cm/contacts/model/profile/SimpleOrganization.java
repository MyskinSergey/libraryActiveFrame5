package ru.intertrust.cm.contacts.model.profile;

import ru.intertrust.workfinder.annotation.DoField;
import ru.intertrust.workfinder.annotation.DoType;
import ru.intertrust.workfinder.model.BaseDomainObject;

/**
 * Created by Vitaliy Orlov on 28.12.2016.
 */
@DoType(doTypeName = "cont_organization")
public class SimpleOrganization extends BaseDomainObject {

    @DoField(name = "name", type = DoField.FieldType.STRING)
    private String name;

    @DoField(name = "fullname", type = DoField.FieldType.STRING)
    private String fullname;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
