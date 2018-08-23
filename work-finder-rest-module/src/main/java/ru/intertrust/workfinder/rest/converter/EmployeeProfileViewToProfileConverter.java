package ru.intertrust.workfinder.rest.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.intertrust.cm.contacts.model.profile.EmployeeProfile;
import ru.intertrust.cm.contacts.model.profile.SimplePerson;
import ru.intertrust.workfinder.rest.model.profile.EmployeeProfileView;
import ru.intertrust.workfinder.service.StoreDoService;

/**
 * Created by Vitaliy Orlov on 10.01.2017.
 */
@Component
public class EmployeeProfileViewToProfileConverter extends CommonModelConverter<EmployeeProfileView, EmployeeProfile> {

    @Autowired
    private StoreDoService storeDoService;

    @Autowired
    private BaseProfileViewToProfileConverter baseProfileViewToProfileConverter;

    @Autowired
    private SimplePersonViewToProfileConverter simplePersonViewToProfileConverter;

    @Override
    protected void fillTargetBySource(EmployeeProfile employeeProfile, EmployeeProfileView profileView) {
        baseProfileViewToProfileConverter.fillTargetBySource(employeeProfile, profileView);
       // employeeProfile.setPerson(profileView.getPerson() != null ? new RdbmsId(profileView.getPerson().getId()) : null);
    }

    @Override
    protected void fillSourceFromTarget(EmployeeProfileView profileView, EmployeeProfile employeeProfile) {
        baseProfileViewToProfileConverter.fillSourceFromTarget(profileView, employeeProfile);

        if(employeeProfile.getPerson() != null){
            SimplePerson person = storeDoService.loadFirstDo(SimplePerson.class, "person", employeeProfile.getPerson());
            profileView.setPerson(simplePersonViewToProfileConverter.convertFromTarget(person));
        }
    }
}
