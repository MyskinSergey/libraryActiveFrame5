package ru.intertrust.workfinder.rest.model.profile;

import ru.intertrust.workfinder.rest.model.DataResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Vitaliy Orlov on 10.01.2017.
 */
@XmlRootElement(name = "SimpleOrganization")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class SimpleOrganizationView implements DataResponse {
    @XmlElement
    private String id;
    @XmlElement
    private String name;
    @XmlElement
    private String fullName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
