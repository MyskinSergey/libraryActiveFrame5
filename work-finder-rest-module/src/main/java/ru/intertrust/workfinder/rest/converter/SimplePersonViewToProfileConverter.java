package ru.intertrust.workfinder.rest.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.intertrust.cm.contacts.model.profile.Person;
import ru.intertrust.cm.contacts.model.profile.SimplePerson;
import ru.intertrust.workfinder.rest.model.profile.SimplePersonView;
import ru.intertrust.workfinder.service.StoreDoService;

/**
 * Created by Vitaliy Orlov on 10.01.2017.
 */
@Component
public class SimplePersonViewToProfileConverter extends CommonModelConverter<SimplePersonView, SimplePerson> {

    @Autowired
    private StoreDoService storeDoService;

    @Override
    protected void fillTargetBySource(SimplePerson simplePerson, SimplePersonView simplePersonView) {
        simplePerson.setFirstName(simplePersonView.getFirstName());
        simplePerson.setLastName(simplePersonView.getLastName());
        simplePerson.setMiddleName(simplePersonView.getMiddleName());

    }

    @Override
    protected void fillSourceFromTarget(SimplePersonView simplePersonView, SimplePerson simplePerson) {
        Person person = storeDoService.loadDo(simplePerson.getPerson(), Person.class);
        simplePersonView.setEmail(person.getEmail());
        simplePersonView.setFirstName(simplePerson.getFirstName());
        simplePersonView.setLastName(simplePerson.getLastName());
        simplePersonView.setMiddleName(simplePerson.getMiddleName());
    }
}
