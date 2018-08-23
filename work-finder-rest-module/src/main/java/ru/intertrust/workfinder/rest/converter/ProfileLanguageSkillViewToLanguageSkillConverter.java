package ru.intertrust.workfinder.rest.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.intertrust.cm.contacts.model.profile.Language;
import ru.intertrust.cm.contacts.model.profile.LanguageSkills;
import ru.intertrust.cm.core.business.api.dto.impl.RdbmsId;
import ru.intertrust.workfinder.model.BatchDomainObject;
import ru.intertrust.workfinder.rest.model.profile.LanguageSkillView;
import ru.intertrust.workfinder.rest.model.profile.ProfileLanguageSkillsView;
import ru.intertrust.workfinder.service.StoreDoService;

import java.util.ArrayList;

/**
 * Created by Vitaliy Orlov on 12.01.2017.
 */
@Component
public class ProfileLanguageSkillViewToLanguageSkillConverter extends CommonModelConverter<ProfileLanguageSkillsView, BatchDomainObject<LanguageSkills>> {

    @Autowired
    private StoreDoService storeDoService;

    @Autowired
    private LanguageViewToLanguageConverter languageViewToLanguageConverter;

    @Override
    protected void fillSourceFromTarget(ProfileLanguageSkillsView profileLanguageSkillsView, BatchDomainObject<LanguageSkills> languageSkillsBatchDomainObject) {

        for(LanguageSkills skill : languageSkillsBatchDomainObject.getData()){

            if(profileLanguageSkillsView.getProfileId() == null){
                profileLanguageSkillsView.setProfileId(skill.getProfile().toStringRepresentation());
            }else if(!profileLanguageSkillsView.getProfileId().equals(skill.getProfile().toStringRepresentation())){
                throw new RuntimeException("Ошибка формирования представления набора LanguageSkills для профиля. Набор LanguageSkills был получен для разных профилей пользователей");
            }
            Language lang = storeDoService.loadDo(skill.getLanguage(), Language.class);


            LanguageSkillView skillView = new LanguageSkillView();
            skillView.setLanguage(languageViewToLanguageConverter.convertFromTarget(lang));
            skillView.setLevel(skill.getLevel());

            if(profileLanguageSkillsView.getLanguageSkills() == null){
                profileLanguageSkillsView.setLanguageSkills(new ArrayList<LanguageSkillView>());
            }
            profileLanguageSkillsView.getLanguageSkills().add(skillView);
        }
    }

    @Override
    protected void fillTargetBySource(BatchDomainObject<LanguageSkills> languageSkillsBatchDomainObject, ProfileLanguageSkillsView profileLanguageSkillsView) {
        if(profileLanguageSkillsView.getLanguageSkills() != null){
            for(LanguageSkillView skillView : profileLanguageSkillsView.getLanguageSkills()){
                LanguageSkills skill = new LanguageSkills();
                skill.setLevel(skillView.getLevel());
                skill.setLanguage(skillView.getLanguage().getId() != null ? new RdbmsId(skillView.getLanguage().getId()) : null);
                skill.setProfile(new RdbmsId(profileLanguageSkillsView.getProfileId()));
                if(languageSkillsBatchDomainObject.getData() == null){
                    languageSkillsBatchDomainObject.setData(new ArrayList<LanguageSkills>());
                }
                languageSkillsBatchDomainObject.getData().add(skill);
            }
        }
    }
}

