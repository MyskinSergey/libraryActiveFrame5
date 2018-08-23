package ru.intertrust.workfinder.rest.model.qualifier;

import ru.intertrust.workfinder.rest.model.DataResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Vitaliy Orlov on 09.01.2017.
 */
@XmlRootElement(name = "QualifierView")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class QualifierView implements DataResponse {
     @XmlElement
     private String qualifierId;

     @XmlElement
     private String name;

     @XmlElement
     private Long cOrder;

     @XmlElement
     private String qualifierGroupName;

     public String getQualifierId() {
          return qualifierId;
     }

     public void setQualifierId(String qualifierId) {
          this.qualifierId = qualifierId;
     }

     public String getName() {
          return name;
     }

     public void setName(String name) {
          this.name = name;
     }

     public Long getcOrder() {
          return cOrder;
     }

     public void setcOrder(Long cOrder) {
          this.cOrder = cOrder;
     }

     public String getQualifierGroupName() {
          return qualifierGroupName;
     }

     public void setQualifierGroupName(String qualifierGroupName) {
          this.qualifierGroupName = qualifierGroupName;
     }
}
