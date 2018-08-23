package ru.intertrust.workfinder.rest.model.status;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Vitaliy Orlov on 30.12.2016.
 */
@XmlRootElement(name =  "ErrorStatusCode")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatusCode {
    @XmlElement
    private int section;
    @XmlElement
    private int code;
    @XmlElement
    private String message;

    public StatusCode() {
    }

    public StatusCode(int section, int code, String message) {
        this.section = section;
        this.code = code;
        this.message = message;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
