package ru.intertrust.workfinder.rest.model.profile;

import ru.intertrust.workfinder.rest.model.DataResponse;
import ru.intertrust.workfinder.rest.model.EnumPropertyValue;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by Vitaliy Orlov on 09.01.2017.
 */
@XmlRootElement(name = "BaseProfile")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class BaseProfileView implements DataResponse{

    @XmlElement
    private String id;

    /* Наименование (использовалось ранее для указание Имени пользователя)*/
    @XmlElement
    private String name;

    /* Информация */
    @XmlElement
    private String description;

    /* Доступность */
    @XmlElement
    private EnumPropertyValue availability;

    /* Описание */
    @XmlElement
    private EnumPropertyValue expertiseLevel;

    /* Тип занятости */
    @XmlElement
    private EnumPropertyValue employmentType;

    /* Сроки занятости */
    @XmlElement
    private EnumPropertyValue employmentPeriod;

    /* Состав */
    @XmlElement
    private EnumPropertyValue composition;

    /* Территориально */
    @XmlElement
    private EnumPropertyValue territory;

    /* Дата публикации */
    @XmlElement
    private Date publicationDate;

    /* Дата снятия */
    @XmlElement
    private Date cancellationDate;

    /* Регион */
    @XmlElement
    private RegionView regionView;


    public RegionView getRegionView() {
        return regionView;
    }

    public void setRegionView(RegionView regionView) {
        this.regionView = regionView;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EnumPropertyValue getAvailability() {
        return availability;
    }

    public void setAvailability(EnumPropertyValue availability) {
        this.availability = availability;
    }

    public EnumPropertyValue getExpertiseLevel() {
        return expertiseLevel;
    }

    public void setExpertiseLevel(EnumPropertyValue expertiseLevel) {
        this.expertiseLevel = expertiseLevel;
    }

    public EnumPropertyValue getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(EnumPropertyValue employmentType) {
        this.employmentType = employmentType;
    }

    public EnumPropertyValue getEmploymentPeriod() {
        return employmentPeriod;
    }

    public void setEmploymentPeriod(EnumPropertyValue employmentPeriod) {
        this.employmentPeriod = employmentPeriod;
    }

    public EnumPropertyValue getComposition() {
        return composition;
    }

    public void setComposition(EnumPropertyValue composition) {
        this.composition = composition;
    }

    public EnumPropertyValue getTerritory() {
        return territory;
    }

    public void setTerritory(EnumPropertyValue territory) {
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
}
