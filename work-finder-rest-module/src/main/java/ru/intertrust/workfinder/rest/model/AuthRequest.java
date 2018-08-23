package ru.intertrust.workfinder.rest.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Vitaliy Orlov on 22.12.2016.
 */
@XmlRootElement(name = "AuthRequest")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class AuthRequest {
    @XmlElement
    private String login;
    @XmlElement
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
