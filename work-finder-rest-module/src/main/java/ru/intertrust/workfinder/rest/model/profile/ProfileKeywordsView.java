package ru.intertrust.workfinder.rest.model.profile;

import ru.intertrust.workfinder.rest.model.DataResponse;
import ru.intertrust.workfinder.rest.model.KeyWordView;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Vitaliy Orlov on 09.01.2017.
 */
@XmlRootElement(name = "profileKeyWords")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ProfileKeywordsView implements DataResponse {

    @XmlElement
    private String profileId;

    @XmlElement
    private List<KeyWordView> keyWordViews;

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public List<KeyWordView> getKeyWordViews() {
        return keyWordViews;
    }

    public void setKeyWordViews(List<KeyWordView> keyWordViews) {
        this.keyWordViews = keyWordViews;
    }
}
