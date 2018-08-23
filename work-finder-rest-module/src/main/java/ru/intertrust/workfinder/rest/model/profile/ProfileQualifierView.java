package ru.intertrust.workfinder.rest.model.profile;

import ru.intertrust.workfinder.rest.model.DataResponse;
import ru.intertrust.workfinder.rest.model.qualifier.QualifierView;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Vitaliy Orlov on 09.01.2017.
 */
@XmlRootElement(name = "profileQualifier")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ProfileQualifierView implements DataResponse {
    @XmlElement
    private String profileId;
    @XmlElement
    private List<QualifierView> qualifierViews;

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public List<QualifierView> getQualifierViews() {
        return qualifierViews;
    }

    public void setQualifierViews(List<QualifierView> qualifierViews) {
        this.qualifierViews = qualifierViews;
    }
}
