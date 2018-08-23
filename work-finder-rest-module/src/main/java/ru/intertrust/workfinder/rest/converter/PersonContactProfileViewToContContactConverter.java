package ru.intertrust.workfinder.rest.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.intertrust.cm.contacts.model.CPersonOtherAddress;
import ru.intertrust.cm.contacts.model.ContCPersonOtherAddressType;
import ru.intertrust.cm.contacts.model.ContContactPerson;
import ru.intertrust.cm.contacts.model.profile.ContactPersonProfile;
import ru.intertrust.cm.contacts.model.profile.SimplePerson;
import ru.intertrust.cm.core.business.api.dto.impl.RdbmsId;
import ru.intertrust.workfinder.rest.model.profile.PersonContactProfileView;
import ru.intertrust.workfinder.service.StoreDoService;

import java.util.ArrayList;

/**
 * Created by Vitaliy Orlov on 10.01.2017.
 */
@Component
public class PersonContactProfileViewToContContactConverter extends CommonModelConverter<PersonContactProfileView, ContactPersonProfile> {

    @Autowired
    private StoreDoService storeDoService;

    @Autowired
    private SimplePersonViewToProfileConverter simplePersonViewToProfileConverter;

    @Override
    protected void fillTargetBySource(ContactPersonProfile contactPersonProfile, PersonContactProfileView personContactProfileView) {
        ContContactPerson contContactPerson = new ContContactPerson();

        contContactPerson.setPatronymic(personContactProfileView.getPatronymic());
        contContactPerson.setSurname(personContactProfileView.getSurname());
        contContactPerson.setName(personContactProfileView.getName());
        contContactPerson.setBirthDate(personContactProfileView.getBirthDate());
        contContactPerson.setTitle(personContactProfileView.getTitle());
        contContactPerson.setTitle1(personContactProfileView.getTitle1());
        contContactPerson.setId(personContactProfileView.getId() != null ? new RdbmsId(personContactProfileView.getId()) : null);

        contactPersonProfile.setContactProfile(contContactPerson);
        contactPersonProfile.setAddresses(new ArrayList<CPersonOtherAddress>());
        if(personContactProfileView.getPhoneList() != null){
            for(String phone : personContactProfileView.getPhoneList()){
                CPersonOtherAddress phoneItem = new CPersonOtherAddress();
                phoneItem.setContactPerson(contContactPerson.getId());
                phoneItem.setValue(phone);
                phoneItem.setAddressType(ContCPersonOtherAddressType.PHONE);

                contactPersonProfile.getAddresses().add(phoneItem);
            }
        }

        if(personContactProfileView.getEmailList() != null){
            for(String email : personContactProfileView.getEmailList()){
                CPersonOtherAddress emailItem = new CPersonOtherAddress();
                emailItem.setContactPerson(contContactPerson.getId());
                emailItem.setValue(email);
                emailItem.setAddressType(ContCPersonOtherAddressType.EMAIL);

                contactPersonProfile.getAddresses().add(emailItem);
            }
        }

    }

    @Override
    protected void fillSourceFromTarget(PersonContactProfileView personContactProfileView, ContactPersonProfile contactPersonProfile) {

        ContContactPerson  contContactPerson = contactPersonProfile.getContactProfile();

        personContactProfileView.setId(contContactPerson.getId().toStringRepresentation());
        personContactProfileView.setTitle1(contContactPerson.getTitle1());
        personContactProfileView.setTitle(contContactPerson.getTitle());
        personContactProfileView.setName(contContactPerson.getName());
        personContactProfileView.setBirthDate(contContactPerson.getBirthDate());
        personContactProfileView.setPatronymic(contContactPerson.getPatronymic());
        personContactProfileView.setSurname(contContactPerson.getSurname());

        personContactProfileView.setPersonView(simplePersonViewToProfileConverter.convertFromTarget(storeDoService.loadFirstDo(SimplePerson.class, "person",contactPersonProfile.getContactProfile().getPerson())));

        if(contactPersonProfile.getAddresses() != null){
            for(CPersonOtherAddress address : contactPersonProfile.getAddresses()){

                if(ContCPersonOtherAddressType.EMAIL.equals(address.getAddressType())){
                    if(personContactProfileView.getEmailList() == null){
                        personContactProfileView.setEmailList(new ArrayList<String>());
                    }
                    personContactProfileView.getEmailList().add(address.getValue());
                }else if(ContCPersonOtherAddressType.PHONE.equals(address.getAddressType())){
                    if(personContactProfileView.getPhoneList() == null){
                        personContactProfileView.setPhoneList(new ArrayList<String>());
                    }

                    personContactProfileView.getPhoneList().add(address.getValue());
                }
            }
        }
    }
}
