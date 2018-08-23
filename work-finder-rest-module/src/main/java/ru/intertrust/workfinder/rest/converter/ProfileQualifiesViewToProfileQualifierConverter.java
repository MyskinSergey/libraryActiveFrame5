package ru.intertrust.workfinder.rest.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.intertrust.cm.contacts.model.Qualifier;
import ru.intertrust.cm.contacts.model.profile.ProfileQualifier;
import ru.intertrust.cm.core.business.api.dto.impl.RdbmsId;
import ru.intertrust.workfinder.model.BatchDomainObject;
import ru.intertrust.workfinder.rest.model.profile.ProfileQualifierView;
import ru.intertrust.workfinder.rest.model.qualifier.QualifierView;
import ru.intertrust.workfinder.service.StoreDoService;

import java.util.ArrayList;

/**
 * Created by Vitaliy Orlov on 12.01.2017.
 */
@Component
public class ProfileQualifiesViewToProfileQualifierConverter extends CommonModelConverter<ProfileQualifierView, BatchDomainObject<ProfileQualifier>> {

    @Autowired
    private StoreDoService storeDoService;

    @Autowired
    private QualifierViewToQualifierConverter qualifierViewToQualifierConverter;

    @Override
    protected void fillTargetBySource(BatchDomainObject<ProfileQualifier> profileQualifierBatchDomainObject, ProfileQualifierView profileQualifierView) {
        profileQualifierBatchDomainObject.setData(new ArrayList<ProfileQualifier>());
        if(profileQualifierView.getQualifierViews() != null){
            for(QualifierView qualifierView : profileQualifierView.getQualifierViews()){
                if(qualifierView.getQualifierId() != null){
                    ProfileQualifier pq = new ProfileQualifier();
                    pq.setQualifier(new RdbmsId(qualifierView.getQualifierId()));
                    pq.setProfile(profileQualifierView.getProfileId() != null ? new RdbmsId(profileQualifierView.getProfileId()) : null);
                    profileQualifierBatchDomainObject.getData().add(pq);
                }
            }
        }
    }

    @Override
    protected void fillSourceFromTarget(ProfileQualifierView profileQualifierView, BatchDomainObject<ProfileQualifier> profileQualifierBatchDomainObject) {
        if(profileQualifierBatchDomainObject.getData() == null){
            return;
        }
        for(ProfileQualifier profileQualifier : profileQualifierBatchDomainObject.getData()){

            if(profileQualifierView.getProfileId() == null){
                profileQualifierView.setProfileId(profileQualifier.getProfile().toStringRepresentation());
            }else if(!profileQualifier.getProfile().toStringRepresentation().equals(profileQualifierView.getProfileId())){
                throw new RuntimeException("Ошибка формирования представления набора ProfileQualifier для профиля. Набор ProfileQualifier был получен для разных профилей пользователей");
            }

            Qualifier qualifier = storeDoService.loadDo(profileQualifier.getQualifier(), Qualifier.class);
            if(profileQualifierView.getQualifierViews() == null){
                profileQualifierView.setQualifierViews(new ArrayList<QualifierView>());
            }

            profileQualifierView.getQualifierViews().add(qualifierViewToQualifierConverter.convertFromTarget(qualifier));
        }
    }


}

