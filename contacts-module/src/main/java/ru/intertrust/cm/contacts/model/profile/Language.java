package ru.intertrust.cm.contacts.model.profile;

import ru.intertrust.workfinder.annotation.DoField;
import ru.intertrust.workfinder.annotation.DoType;
import ru.intertrust.workfinder.model.BaseDomainObject;

/**
 * Created by Vitaliy Orlov on 28.12.2016.
 */
@DoType(doTypeName = "languages")
public class Language extends BaseDomainObject {

    @DoField(name = "lang_id", type = DoField.FieldType.LONG)
    private Long langId;

    @DoField(name = "lang_name", type = DoField.FieldType.STRING)
    private String name;

    public Long getLangId() {
        return langId;
    }

    public void setLangId(Long langId) {
        this.langId = langId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
