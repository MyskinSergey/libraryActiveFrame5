package ru.intertrust.cm.contacts.model;

import ru.intertrust.cm.core.business.api.dto.Id;
import ru.intertrust.workfinder.annotation.DoField;
import ru.intertrust.workfinder.annotation.DoType;
import ru.intertrust.workfinder.model.BaseDomainObject;

import java.util.Date;

/**
 * Created by Vitaliy Orlov on 28.12.2016.
 */
@DoType(doTypeName = "cont_contact_person")
public class ContContactPerson extends BaseDomainObject {

    @DoField(name = "title", type = DoField.FieldType.STRING)
    private String title;

    @DoField(name = "title1", type = DoField.FieldType.STRING)
    private String title1;

    @DoField(name = "surname", type = DoField.FieldType.STRING)
    private String surname;

    @DoField(name = "name", type = DoField.FieldType.STRING)
    private String name;

    @DoField(name = "patronymic", type = DoField.FieldType.STRING)
    private String patronymic;

    @DoField(name = "birth_date", type = DoField.FieldType.DATE)
    private Date birthDate;

    @DoField(name = "person", type = DoField.FieldType.REFERENCE_ID)
    private Id person;

    public Id getPerson() {
        return person;
    }

    public void setPerson(Id person) {
        this.person = person;
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
}
