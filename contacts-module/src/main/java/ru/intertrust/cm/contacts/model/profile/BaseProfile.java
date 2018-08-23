package ru.intertrust.cm.contacts.model.profile;

import ru.intertrust.cm.core.business.api.dto.Id;
import ru.intertrust.workfinder.annotation.DoField;
import ru.intertrust.workfinder.annotation.DoType;
import ru.intertrust.workfinder.model.BaseDomainObject;

import java.util.Date;

/**
 * Created by Vitaliy Orlov on 28.12.2016.
 */
@DoType(doTypeName = "profile_base")
public class BaseProfile extends BaseDomainObject {

    @DoField(name = "name", type = DoField.FieldType.STRING)
    private String name;

    @DoField(name = "description", type = DoField.FieldType.STRING)
    private String description;

    @DoField(name = "availability", type = DoField.FieldType.LONG)
    private Long availability;

    @DoField(name = "expertise_level", type = DoField.FieldType.LONG)
    private Long expertiseLevel;

    @DoField(name = "employment_type", type = DoField.FieldType.LONG)
    private Long employmentType;

    @DoField(name = "employment_period", type = DoField.FieldType.LONG)
    private Long employmentPeriod;

    @DoField(name = "composition", type = DoField.FieldType.LONG)
    private Long composition;

    @DoField(name = "territory", type = DoField.FieldType.LONG)
    private Long territory;

    @DoField(name = "publication_date", type = DoField.FieldType.DATE)
    private Date publicationDate;

    @DoField(name = "cancellation_date", type = DoField.FieldType.DATE)
    private Date cancellationDate;

    @DoField(name = "region", type = DoField.FieldType.REFERENCE_ID)
    private Id region;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAvailability() {
        return availability;
    }

    public void setAvailability(Long availability) {
        this.availability = availability;
    }

    public Long getExpertiseLevel() {
        return expertiseLevel;
    }

    public void setExpertiseLevel(Long expertiseLevel) {
        this.expertiseLevel = expertiseLevel;
    }

    public Long getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(Long employmentType) {
        this.employmentType = employmentType;
    }

    public Long getEmploymentPeriod() {
        return employmentPeriod;
    }

    public void setEmploymentPeriod(Long employmentPeriod) {
        this.employmentPeriod = employmentPeriod;
    }

    public Long getComposition() {
        return composition;
    }

    public void setComposition(Long composition) {
        this.composition = composition;
    }

    public Long getTerritory() {
        return territory;
    }

    public void setTerritory(Long territory) {
        this.territory = territory;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Date getCancellationDate() {
        return cancellationDate;
    }

    public void setCancellationDate(Date cancellationDate) {
        this.cancellationDate = cancellationDate;
    }

    public Id getRegion() {
        return region;
    }

    public void setRegion(Id region) {
        this.region = region;
    }
}
