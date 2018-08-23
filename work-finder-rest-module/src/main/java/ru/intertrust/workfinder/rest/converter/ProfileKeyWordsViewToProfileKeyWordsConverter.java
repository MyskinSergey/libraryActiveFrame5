package ru.intertrust.workfinder.rest.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.intertrust.cm.contacts.model.Keyword;
import ru.intertrust.cm.contacts.model.profile.ProfileKeywords;
import ru.intertrust.cm.core.business.api.dto.impl.RdbmsId;
import ru.intertrust.workfinder.model.BatchDomainObject;
import ru.intertrust.workfinder.rest.model.KeyWordView;
import ru.intertrust.workfinder.rest.model.profile.ProfileKeywordsView;
import ru.intertrust.workfinder.service.StoreDoService;

import java.util.ArrayList;

/**
 * Created by Vitaliy Orlov on 12.01.2017.
 */
@Component
public class ProfileKeyWordsViewToProfileKeyWordsConverter extends CommonModelConverter<ProfileKeywordsView, BatchDomainObject<ProfileKeywords>> {

    @Autowired
    private StoreDoService storeDoService;

    @Autowired
    private KeyWordViewToKeyWordConverter keyWordViewToKeyWordConverter;

    @Override
    protected void fillTargetBySource(BatchDomainObject<ProfileKeywords> profileKeywordsBatchDomainObject, ProfileKeywordsView profileKeywordsView) {
        profileKeywordsBatchDomainObject.setData(new ArrayList<ProfileKeywords>());

        if(profileKeywordsView.getKeyWordViews() != null){

            for(KeyWordView view : profileKeywordsView.getKeyWordViews()){
                ProfileKeywords item = new ProfileKeywords();
                item.setProfile(profileKeywordsView.getProfileId() != null ?  new RdbmsId(profileKeywordsView.getProfileId()) : null);
                item.setKeyword(view.getId() != null ? new RdbmsId(view.getId()) : null);

                profileKeywordsBatchDomainObject.getData().add(item);
            }
        }
    }

    @Override
    protected void fillSourceFromTarget(ProfileKeywordsView profileKeywordsView, BatchDomainObject<ProfileKeywords> profileKeywordsBatchDomainObject) {
        for(ProfileKeywords keywords : profileKeywordsBatchDomainObject.getData()){

            if(profileKeywordsView.getProfileId() == null){
                profileKeywordsView.setProfileId(keywords.getProfile().toStringRepresentation());
            }else if(!profileKeywordsView.getProfileId().equals(keywords.getProfile().toStringRepresentation())){
                throw new RuntimeException("Ошибка формирования представления набора ProfileKeywords для профиля. Набор ProfileKeywords был получен для разных профилей пользователей");
            }
            Keyword keyword = storeDoService.loadDo(keywords.getKeyword(), Keyword.class);

            if(profileKeywordsView.getKeyWordViews() == null){
                profileKeywordsView.setKeyWordViews(new ArrayList<KeyWordView>());
            }

            profileKeywordsView.getKeyWordViews().add(  keyWordViewToKeyWordConverter.convertFromTarget(keyword));
        }
    }

}

