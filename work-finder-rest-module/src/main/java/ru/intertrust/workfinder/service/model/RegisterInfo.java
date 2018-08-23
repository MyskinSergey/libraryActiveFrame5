package ru.intertrust.workfinder.service.model;

import ru.intertrust.cm.core.business.api.dto.Id;
import ru.intertrust.workfinder.annotation.DoField;
import ru.intertrust.workfinder.annotation.DoType;
import ru.intertrust.workfinder.model.BaseDomainObject;
import ru.intertrust.workfinder.service.model.converter.AccountTypeFiledConverter;

/**
 * Created by Vitaliy Orlov on 20.12.2016.
 */
@DoType(doTypeName = "reg_info")
public class RegisterInfo extends BaseDomainObject {

    @DoField(name = "token", type = DoField.FieldType.STRING)
    private String token;

    @DoField(name = "name", type = DoField.FieldType.STRING)
    private String name;

    @DoField(name = "fullName", type = DoField.FieldType.STRING)
    private String fullName;

    @DoField(name = "INN", type = DoField.FieldType.STRING)
    private String inn;

    @DoField(name = "OGRN", type = DoField.FieldType.STRING)
    private String ogrn;

    @DoField(name = "person_first_name", type = DoField.FieldType.STRING)
    private String personFirstName;

    @DoField(name = "person_last_name", type = DoField.FieldType.STRING)
    private String personLastName;

    @DoField(name = "person_position", type = DoField.FieldType.STRING)
    private String position;

    @DoField(name = "person_middle_name", type = DoField.FieldType.STRING)
    private String personMiddleName;

    @DoField(name = "phone", type = DoField.FieldType.STRING)
    private String phone;

    @DoField(name = "email", type = DoField.FieldType.STRING)
    private String email;

    @DoField(name = "address", type = DoField.FieldType.STRING)
    private String address;

    @DoField(name = "add_info", type = DoField.FieldType.STRING)
    private String additionalInfo;

    @DoField(name = "account_type", type = DoField.FieldType.LONG, classConverter= AccountTypeFiledConverter.class)
    private AccountType accountType;

    @DoField(name = "password", type = DoField.FieldType.STRING)
    private String password;

    @DoField(name = "resultObjectId", type = DoField.FieldType.STRING)
    private String resultObjectId;

    @DoField(name = "account_inf", type = DoField.FieldType.REFERENCE_ID)
    private Id account;

    @DoField(name = "person", type = DoField.FieldType.REFERENCE_ID)
    private Id person;


    public Id getPerson() {
        return person;
    }

    public void setPerson(Id person) {
        this.person = person;
    }

    public String getResultObjectId() {
        return resultObjectId;
    }

    public void setResultObjectId(String resultObjectId) {
        this.resultObjectId = resultObjectId;
    }

    public Id getAccount() {
        return account;
    }

    public void setAccount(Id account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getOgrn() {
        return ogrn;
    }

    public void setOgrn(String ogrn) {
        this.ogrn = ogrn;
    }

    public String getPersonFirstName() {
        return personFirstName;
    }

    public void setPersonFirstName(String personFirstName) {
        this.personFirstName = personFirstName;
    }

    public String getPersonLastName() {
        return personLastName;
    }

    public void setPersonLastName(String personLastName) {
        this.personLastName = personLastName;
    }

    public String getPersonMiddleName() {
        return personMiddleName;
    }

    public void setPersonMiddleName(String personMiddleName) {
        this.personMiddleName = personMiddleName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
}
