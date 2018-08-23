package ru.intertrust.workfinder.rest.converter;

import org.springframework.stereotype.Component;
import ru.intertrust.cm.contacts.model.Keyword;
import ru.intertrust.cm.core.business.api.dto.impl.RdbmsId;
import ru.intertrust.workfinder.rest.model.KeyWordView;

/**
 * Created by Vitaliy Orlov on 10.01.2017.
 */
@Component
public class KeyWordViewToKeyWordConverter extends  CommonModelConverter<KeyWordView, Keyword> {

    @Override
    protected void fillTargetBySource(Keyword keyWord, KeyWordView keyWordView) {
        keyWord.setId(keyWordView.getId() != null ? new RdbmsId(keyWordView.getId()) : null);
        keyWord.setWord(keyWordView.getWord());
    }

    @Override
    protected void fillSourceFromTarget(KeyWordView keyWordView, Keyword keyWord) {
        keyWordView.setWord(keyWord.getWord());
        keyWordView.setId(keyWord.getId().toStringRepresentation());
    }
}
