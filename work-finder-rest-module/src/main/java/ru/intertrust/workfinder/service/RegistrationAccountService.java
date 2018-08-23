package ru.intertrust.workfinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.intertrust.cm.contacts.model.*;
import ru.intertrust.cm.contacts.model.profile.EmployeeProfile;
import ru.intertrust.cm.core.business.api.CollectionsService;
import ru.intertrust.cm.core.business.api.CrudService;
import ru.intertrust.cm.core.business.api.NotificationService;
import ru.intertrust.cm.core.business.api.dto.*;
import ru.intertrust.cm.core.business.api.dto.notification.NotificationAddressee;
import ru.intertrust.cm.core.business.api.dto.notification.NotificationAddresseePerson;
import ru.intertrust.cm.core.business.api.dto.notification.NotificationContext;
import ru.intertrust.cm.core.business.api.dto.notification.NotificationPriority;
import ru.intertrust.workfinder.service.model.AccountType;
import ru.intertrust.workfinder.service.model.RegisterInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by Vitaliy Orlov on 20.12.2016.
 */
@Component
public class RegistrationAccountService {

    private static final String NOTIFICATION_TYPE = "RegistrationCompleteNotificationType";
    public static final String SENDER_NAME = "Утверждение регис трации";


    @Autowired
    private StoreDoService storeDoService;

    @Autowired
    private CollectionsService collectionsService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private CrudService crudService;

    public String registerAccount(RegisterInfo info){
        List<RegisterInfo> reginfos = storeDoService.loadDoList(RegisterInfo.class, "email", info.getEmail(), 0,0);
        if(reginfos != null && !reginfos.isEmpty()){
            throw new  RuntimeException("Account with email {"+info.getEmail()+"} already exist.");
        }
        String token = UUID.randomUUID().toString();
        info.setToken(token);
        RegisterInfo result = storeDoService.saveDo(info);
        return result != null ? result.getToken() : null;
    }


    public boolean applyRegistration(Id id){
        return applyRegistration(storeDoService.loadDo(id,RegisterInfo.class));
    }


    public boolean applyRegistration(RegisterInfo regInfo){
        if(regInfo.getAccount() == null){
            DomainObject authInfo = crudService.createDomainObject("authentication_info");
            authInfo.setString("User_Uid", regInfo.getEmail());
            authInfo.setString("Password", regInfo.getPassword());
            authInfo = crudService.save(authInfo);
            regInfo.setAccount(authInfo.getId());
        }

        if(regInfo.getPerson() == null){
            DomainObject personProfile = crudService.createDomainObject("person_profile");
            personProfile.setString("name", regInfo.getEmail());
            personProfile = crudService.save(personProfile);

            createProfileValue("NOTIFICATION.*", "HIGH", personProfile.getId());
            createProfileValue("LOCALE", "RU", personProfile.getId());


            DomainObject personDo = crudService.createDomainObject("person");
            personDo.setString("login", regInfo.getEmail());
            personDo.setString("firstname", regInfo.getPersonFirstName());
            personDo.setString("lastname", regInfo.getPersonLastName());

            personDo.setString("email", regInfo.getEmail());
            personDo.setReference("profile", personProfile.getId());

            personDo = crudService.save(personDo);
            regInfo.setPerson(personDo.getId());

            EmployeeProfile employeeProfile = new EmployeeProfile();
            employeeProfile.setPerson(personDo.getId());

            storeDoService.saveDo(employeeProfile);

        }


        Id resultObjectId = null;
        if(AccountType.PRIVATE.equals(regInfo.getAccountType())){
            resultObjectId = createContContactPerson(regInfo);
        }else if(AccountType.ORGANIZATION.equals(regInfo.getAccountType())){
            resultObjectId = createOrganizationWithContactPerson(regInfo);
        }

        storeDoService.saveDo(regInfo);

        sendRegisterCompleteMessage(regInfo);
        return true;
    }


    public void createProfileValue(String key, String value, Id profile){
        DomainObject profileValue = crudService.createDomainObject("profile_value_string");
        profileValue.setString("key", key);
        profileValue.setString("value", value);
        profileValue.setReference("profile", profile);
        profileValue.setBoolean("readonly", false);

        profileValue = crudService.save(profileValue);
    }

    public boolean sendRegisterCompleteMessage(RegisterInfo info){
        try{
            NotificationContext notificationContext = new NotificationContext();
            notificationContext.addContextObject("info", crudService.find(info.getId()));
            List<NotificationAddressee> addresseeList = new ArrayList<>();
            addresseeList.add(new NotificationAddresseePerson( info.getPerson()));

             notificationService.sendOnTransactionSuccess(NOTIFICATION_TYPE, getSenderNotificationId(), addresseeList, NotificationPriority.HIGH, notificationContext);
            return true;
        }catch (Exception e){
           e.printStackTrace();
            return false;
        }
    }


    public Id getSenderNotificationId(){
        List<Value> params = new ArrayList<>();
        params.add(new StringValue("admin"));
        IdentifiableObjectCollection result = collectionsService.findCollectionByQuery("select id from person where login = {0}", params);
        return result != null && result.size() > 0 ? result.get(0).getId() : null;
    }


    public boolean sendConfirmMessage(RegisterInfo info){

        return true;
    }

    public boolean applyRegistration(String token){

        return true;
    }

    public boolean rejectRegistration(String token){

        return true;
    }


    private Id createOrganizationWithContactPerson(RegisterInfo regInfo) {
        Id contPerson = createContContactPerson(regInfo);

        ContOrganization org = new ContOrganization();
        org.setName(regInfo.getName());
        org.setFullname(regInfo.getFullName());
        org.setTaxNumber(regInfo.getInn());
        org.setOgrn(regInfo.getOgrn());

        org = storeDoService.saveDo(org);

        ContCPersonOrg contCPersonOrg = new ContCPersonOrg();
        contCPersonOrg.setContactPerson(contPerson);
        contCPersonOrg.setOrganization(org.getId());

        contCPersonOrg.setDescription(regInfo.getAdditionalInfo());
        contCPersonOrg.setPosition(regInfo.getPosition());
        //contCPersonOrg.se
        contCPersonOrg  = storeDoService.saveDo(contCPersonOrg);
        return org.getId();
    }

    public Id createContContactPerson(RegisterInfo person) {


        ContContactPerson cperson = new ContContactPerson();
        cperson.setName(person.getPersonFirstName());
        cperson.setSurname(person.getPersonLastName());
        cperson.setPatronymic(person.getPersonMiddleName());
        cperson.setPerson(person.getPerson());

        cperson = storeDoService.saveDo(cperson);

        CPersonOtherAddress email = new CPersonOtherAddress();
        email.setAddressType(ContCPersonOtherAddressType.EMAIL);
        email.setValue(person.getEmail());
        email.setContactPerson(cperson.getId());

        email = storeDoService.saveDo(email);

        if(person.getPhone() != null && !person.getPhone().trim().isEmpty()){
            CPersonOtherAddress phone = new CPersonOtherAddress();
            phone.setAddressType(ContCPersonOtherAddressType.PHONE);
            phone.setValue(person.getPhone());
            phone.setContactPerson(cperson.getId());
            phone = storeDoService.saveDo(phone);
        }

        return cperson.getId();
    }




}
