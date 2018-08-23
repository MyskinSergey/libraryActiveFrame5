package ru.intertrust.cm.contacts.model.profile;

import ru.intertrust.cm.core.business.api.dto.Id;
import ru.intertrust.workfinder.annotation.DoField;
import ru.intertrust.workfinder.annotation.DoType;
import ru.intertrust.workfinder.model.BaseDomainObject;

/**
 * Created by Vitaliy Orlov on 11.01.2017.
 */
@DoType(doTypeName = "profile_keywords")
public class ProfileKeywords extends BaseDomainObject {

    @DoField(name = "keyword", type = DoField.FieldType.REFERENCE_ID)
    private Id keyword;

    @DoField(name = "profile", type = DoField.FieldType.REFERENCE_ID)
    private Id profile;

    public Id getKeyword() {
        return keyword;
    }

    public void setKeyword(Id keyword) {
        this.keyword = keyword;
    }

    public Id getProfile() {
        return profile;
    }

    public void setProfile(Id profile) {
        this.profile = profile;
    }
}
