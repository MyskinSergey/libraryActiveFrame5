package ru.intertrust.workfinder.model;

import ru.intertrust.cm.core.business.api.dto.Id;

/**
 * Created by Vitaliy Orlov on 20.12.2016.
 */
public class BaseDomainObject {

    private Id id;

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }
}
