package ru.intertrust.cm.contacts.model;

import ru.intertrust.cm.core.business.api.dto.Id;
import ru.intertrust.workfinder.annotation.DoField;
import ru.intertrust.workfinder.annotation.DoType;
import ru.intertrust.workfinder.model.BaseDomainObject;

/**
 * Created by Vitaliy Orlov on 28.12.2016.
 */
@DoType(doTypeName = "Qual_Group_Qualifier")
public class QualifierGroup extends BaseDomainObject {

    @DoField(name = "name", type = DoField.FieldType.STRING)
    private String name;

    @DoField(name = "one_ofthe_groups", type = DoField.FieldType.BOOLEAN)
    private Boolean oneOfTheGroup;

    @DoField(name = "parent_id", type = DoField.FieldType.REFERENCE_ID)
    private Id parent;

    @DoField(name = "uid", type = DoField.FieldType.STRING)
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getOneOfTheGroup() {
        return oneOfTheGroup;
    }

    public void setOneOfTheGroup(Boolean oneOfTheGroup) {
        this.oneOfTheGroup = oneOfTheGroup;
    }

    public Id getParent() {
        return parent;
    }

    public void setParent(Id parent) {
        this.parent = parent;
    }
}
