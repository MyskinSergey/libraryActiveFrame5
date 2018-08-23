package ru.intertrust.workfinder.rest.model.profile;

import ru.intertrust.workfinder.rest.model.DataResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Vitaliy Orlov on 09.01.2017.
 */
@XmlRootElement(name = "LanguageSkill")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class LanguageSkillView implements DataResponse {

    @XmlElement
    private Long level;

    @XmlElement
    private LanguageView language;

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public LanguageView getLanguage() {
        return language;
    }

    public void setLanguage(LanguageView language) {
        this.language = language;
    }
}
