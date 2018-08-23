package ru.intertrust.workfinder.rest.model.profile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Vitaliy Orlov on 09.01.2017.
 */
@XmlRootElement(name = "VacancyProfile")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class VacancyProfileView extends BaseProfileView {
     @XmlElement
     private SimplePersonView person;
     @XmlElement
     private SimpleOrganizationView organization;

     public SimplePersonView getPerson() {
          return person;
     }

     public void setPerson(SimplePersonView person) {
          this.person = person;
     }

     public SimpleOrganizationView getOrganization() {
          return organization;
     }

     public void setOrganization(SimpleOrganizationView organization) {
          this.organization = organization;
     }
}
