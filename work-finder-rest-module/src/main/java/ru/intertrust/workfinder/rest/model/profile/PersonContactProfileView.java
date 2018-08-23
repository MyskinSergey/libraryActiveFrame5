package ru.intertrust.workfinder.rest.model.profile;

import ru.intertrust.workfinder.rest.model.DataResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

/**
 * Created by Vitaliy Orlov on 09.01.2017.
 */
@XmlRootElement(name = "personContactProfile")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class PersonContactProfileView implements DataResponse {
    @XmlElement
    private String id;

    @XmlElement
    private String title;

    @XmlElement
    private String title1;

    @XmlElement
    private String surname;

    @XmlElement
    private String name;

    @XmlElement
    private String patronymic;

    @XmlElement
    private Date birthDate;

    @XmlElement
    private List<String> phoneList;

    @XmlElement
    private List<String> emailList;

    @XmlElement
    private SimplePersonView personView;

    public SimplePersonView getPersonView() {
        return personView;
    }

    public void setPersonView(SimplePersonView personView) {
        this.personView = personView;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public List<String> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<String> phoneList) {
        this.phoneList = phoneList;
    }

    public List<String> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<String> emailList) {
        this.emailList = emailList;
    }
}
