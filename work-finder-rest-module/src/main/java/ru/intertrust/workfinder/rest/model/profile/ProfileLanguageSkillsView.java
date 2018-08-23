package ru.intertrust.workfinder.rest.model.profile;

import ru.intertrust.workfinder.rest.model.DataResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Vitaliy Orlov on 09.01.2017.
 */
@XmlRootElement(name = "ProfileLanguageSkill")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ProfileLanguageSkillsView implements DataResponse {
    @XmlElement
    private String profileId;

    @XmlElement
    private List<LanguageSkillView> languageSkills;

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public List<LanguageSkillView> getLanguageSkills() {
        return languageSkills;
    }

    public void setLanguageSkills(List<LanguageSkillView> languageSkills) {
        this.languageSkills = languageSkills;
    }
}
