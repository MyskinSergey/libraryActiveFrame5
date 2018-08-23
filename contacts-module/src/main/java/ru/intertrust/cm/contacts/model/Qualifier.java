package ru.intertrust.cm.contacts.model;

import ru.intertrust.cm.core.business.api.dto.Id;
import ru.intertrust.workfinder.annotation.DoField;
import ru.intertrust.workfinder.annotation.DoType;
import ru.intertrust.workfinder.model.BaseDomainObject;

/**
 * Created by Vitaliy Orlov on 28.12.2016.
 */
@DoType(doTypeName = "Qualifier")
public class Qualifier extends BaseDomainObject {

    @DoField(name = "name", type = DoField.FieldType.STRING)
    private String name;

    @DoField(name = "group_qualifier", type = DoField.FieldType.REFERENCE_ID)
    private Id qualifierGroup;

    @DoField(name = "corder", type = DoField.FieldType.LONG)
    private Long cOrder;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Id getQualifierGroup() {
        return qualifierGroup;
    }

    public void setQualifierGroup(Id qualifierGroup) {
        this.qualifierGroup = qualifierGroup;
    }

    public Long getcOrder() {
        return cOrder;
    }

    public void setcOrder(Long cOrder) {
        this.cOrder = cOrder;
    }
}
