package ru.intertrust.cm.contacts.model.profile;

import ru.intertrust.cm.core.business.api.dto.Id;
import ru.intertrust.workfinder.annotation.DoField;
import ru.intertrust.workfinder.annotation.DoType;
import ru.intertrust.workfinder.model.BaseDomainObject;

import java.util.Date;

/**
 * Created by Vitaliy Orlov on 28.12.2016.
 */
@DoType(doTypeName = "regions")
public class Region extends BaseDomainObject {

    @DoField(name = "region_id", type = DoField.FieldType.LONG)
    private Long regionId;

    @DoField(name = "name", type = DoField.FieldType.STRING)
    private String name;

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
