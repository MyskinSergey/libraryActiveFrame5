package ru.intertrust.workfinder.rest.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.intertrust.cm.contacts.model.profile.SimpleOrganization;
import ru.intertrust.cm.contacts.model.profile.SimplePerson;
import ru.intertrust.cm.contacts.model.profile.VacancyProfile;
import ru.intertrust.workfinder.rest.model.profile.VacancyProfileView;
import ru.intertrust.workfinder.service.StoreDoService;

/**
 * Created by Vitaliy Orlov on 10.01.2017.
 */
@Component
public class VacansyProfileViewToProfileConverter extends CommonModelConverter<VacancyProfileView, VacancyProfile> {

    @Autowired
    private StoreDoService storeDoService;

    @Autowired
    private BaseProfileViewToProfileConverter baseProfileViewToProfileConverter;

    @Autowired
    private SimplePersonViewToProfileConverter simplePersonViewToProfileConverter;

    @Autowired
    private SimpleOrganizationViewToProfileConverter simpleOrganizationViewToProfileConverter;



    @Override
    protected void fillTargetBySource(VacancyProfile vacancyProfile, VacancyProfileView profileView) {
        baseProfileViewToProfileConverter.fillTargetBySource(vacancyProfile, profileView);
        //vacancyProfile.setPerson(profileView.getPerson() != null ? new RdbmsId(profileView.getPerson().getId()) : null);
       // vacancyProfile.setOrganization(profileView.getOrganization() != null ? new RdbmsId(profileView.getOrganization().getId()) : null);
    }

    @Override
    protected void fillSourceFromTarget(VacancyProfileView profileView, VacancyProfile vacancyProfile) {
        baseProfileViewToProfileConverter.fillSourceFromTarget(profileView, vacancyProfile);

        if(vacancyProfile.getPerson() != null){
            SimplePerson person = storeDoService.loadDo(vacancyProfile.getPerson(), SimplePerson.class);
            profileView.setPerson(simplePersonViewToProfileConverter.convertFromTarget(person));
        }


        if(vacancyProfile.getOrganization() != null){
            SimpleOrganization organization = storeDoService.loadDo(vacancyProfile.getOrganization(), SimpleOrganization.class);
            profileView.setOrganization(simpleOrganizationViewToProfileConverter.convertFromTarget(organization));
        }

    }
}
