package ru.intertrust.cm.contacts.model.profile;

import ru.intertrust.cm.core.business.api.dto.Id;
import ru.intertrust.workfinder.annotation.DoField;
import ru.intertrust.workfinder.annotation.DoType;
import ru.intertrust.workfinder.model.BaseDomainObject;

/**
 * Created by Vitaliy Orlov on 11.01.2017.
 */
@DoType(doTypeName = "language_skills")
public class LanguageSkills extends BaseDomainObject {

    @DoField(name = "skill_level", type = DoField.FieldType.LONG)
    private Long level;

    @DoField(name = "language", type = DoField.FieldType.REFERENCE_ID)
    private Id language;

    @DoField(name = "profile", type = DoField.FieldType.REFERENCE_ID)
    private Id profile;

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Id getLanguage() {
        return language;
    }

    public void setLanguage(Id language) {
        this.language = language;
    }

    public Id getProfile() {
        return profile;
    }

    public void setProfile(Id profile) {
        this.profile = profile;
    }
}
