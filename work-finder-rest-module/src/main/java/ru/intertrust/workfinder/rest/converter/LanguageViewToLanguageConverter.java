package ru.intertrust.workfinder.rest.converter;

import org.springframework.stereotype.Component;
import ru.intertrust.cm.contacts.model.profile.Language;
import ru.intertrust.cm.core.business.api.dto.impl.RdbmsId;
import ru.intertrust.workfinder.rest.model.profile.LanguageView;

/**
 * Created by Vitaliy Orlov on 10.01.2017.
 */
@Component
public class LanguageViewToLanguageConverter extends  CommonModelConverter<LanguageView, Language> {

    @Override
    protected void fillTargetBySource(Language language, LanguageView languageView) {
        language.setId(languageView.getId() != null ? new RdbmsId(languageView.getId()): null);
        language.setName(languageView.getName());
        language.setLangId(languageView.getLangId());
    }

    @Override
    protected void fillSourceFromTarget(LanguageView languageView, Language language) {
        languageView.setName(language.getName());
        languageView.setLangId(language.getLangId());
        languageView.setId(language.getId().toStringRepresentation());
    }
}
