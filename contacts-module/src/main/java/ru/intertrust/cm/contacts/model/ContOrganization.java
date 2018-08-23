package ru.intertrust.cm.contacts.model;

import ru.intertrust.cm.core.business.api.dto.Id;
import ru.intertrust.workfinder.annotation.DoField;
import ru.intertrust.workfinder.annotation.DoType;
import ru.intertrust.workfinder.model.BaseDomainObject;

import java.util.Date;

/**
 * Created by Vitaliy Orlov on 28.12.2016.
 */
@DoType(doTypeName = "cont_organization")
public class ContOrganization extends BaseDomainObject {

    @DoField(name = "name", type = DoField.FieldType.STRING)
    private String name;

    @DoField(name = "fullname", type = DoField.FieldType.STRING)
    private String fullname;


    @DoField(name = "rank", type = DoField.FieldType.REFERENCE_ID)
    private Id rank;

    @DoField(name = "parent", type = DoField.FieldType.REFERENCE_ID)
    private Id parent;

    @DoField(name = "orgtype", type = DoField.FieldType.STRING)
    private String orgtype;

    @DoField(name = "tax_number", type = DoField.FieldType.STRING)
    private String taxNumber;

    @DoField(name = "ogrn_number", type = DoField.FieldType.STRING)
    private String ogrn;

    public String getOgrn() {
        return ogrn;
    }

    public void setOgrn(String ogrn) {
        this.ogrn = ogrn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Id getRank() {
        return rank;
    }

    public void setRank(Id rank) {
        this.rank = rank;
    }

    public Id getParent() {
        return parent;
    }

    public void setParent(Id parent) {
        this.parent = parent;
    }

    public String getOrgtype() {
        return orgtype;
    }

    public void setOrgtype(String orgtype) {
        this.orgtype = orgtype;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }
}
