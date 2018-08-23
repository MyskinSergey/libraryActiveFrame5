package ru.intertrust.workfinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.intertrust.cm.contacts.model.CPersonOtherAddress;
import ru.intertrust.cm.contacts.model.ContContactPerson;
import ru.intertrust.cm.contacts.model.profile.*;
import ru.intertrust.cm.core.business.api.CollectionsService;
import ru.intertrust.cm.core.business.api.PersonService;
import ru.intertrust.cm.core.business.api.dto.Id;
import ru.intertrust.cm.core.business.api.dto.impl.RdbmsId;
import ru.intertrust.workfinder.model.BatchDomainObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vitaliy Orlov on 10.01.2017.
 */
@Component
public class BaseProfileService {


    @Autowired
    private StoreDoService storeDoService;

    @Autowired
    private CollectionsService collectionsService;

    @Autowired
    private PersonService personService;

    public void saveProfile(EmployeeProfile profile){
        EmployeeProfile currentProfile = loadCurrentPersonProfile();
        profile.setPerson(personService.getCurrentPerson().getId());

        if(currentProfile != null){
            profile.setId(currentProfile.getId());
            profile.setPublicationDate(currentProfile.getPublicationDate());
            profile.setCancellationDate(currentProfile.getCancellationDate());
        }

        BaseProfile result = storeDoService.saveDo(profile);
    }

    public EmployeeProfile loadCurrentPersonProfile(){
        List<EmployeeProfile> profileList = storeDoService.loadDoList(EmployeeProfile.class, "person", personService.getCurrentPerson().getId(), 0, 0 );
        if(profileList != null && !profileList.isEmpty()){
            return profileList.get(0);
        }
        return null;
    }

    public List<VacancyProfile> loadVacancyByCurrentReportedPerson(){
        return storeDoService.loadDoList(VacancyProfile.class, "person", personService.getCurrentPerson().getId(), 0, 0 );
    }


    public BatchDomainObject<LanguageSkills> loadProfileLanguageSkills(String profileId) {
        List<LanguageSkills> skills = storeDoService.loadDoList(LanguageSkills.class, "profile", new RdbmsId(profileId), 0,0);
        BatchDomainObject<LanguageSkills> result = new BatchDomainObject<>();
        result.setData(skills);
        return result;
    }

    public BatchDomainObject<LanguageSkills> saveProfileLanguageSkills(String profileId, BatchDomainObject<LanguageSkills> languageSkillsBatchDomainObject) {

        List<LanguageSkills> currentSkills = storeDoService.loadDoList(LanguageSkills.class, "profile", new RdbmsId(profileId), 0,0);
        storeDoService.deleteAll(currentSkills);
        return storeDoService.saveDoBatch(languageSkillsBatchDomainObject);
    }

    public BatchDomainObject<ProfileKeywords> loadProfileKeyWords(String profileId) {
        List<ProfileKeywords> keywords = storeDoService.loadDoList(ProfileKeywords.class, "profile", new RdbmsId(profileId), 0,0);
        BatchDomainObject<ProfileKeywords> result = new BatchDomainObject<>();
        result.setData(keywords);
        return result;

    }

    public BatchDomainObject<ProfileKeywords> saveProfileKeyWords(String profileId, BatchDomainObject<ProfileKeywords> profileKeywordsBatchDomainObject) {
        List<ProfileKeywords> currentKeywords = storeDoService.loadDoList(ProfileKeywords.class, "profile", new RdbmsId(profileId), 0,0);
        storeDoService.deleteAll(currentKeywords);
        return storeDoService.saveDoBatch(profileKeywordsBatchDomainObject);
    }

    public ContactPersonProfile loadCurrentPersonContactProfile() {
        ContactPersonProfile result = new ContactPersonProfile();
        result.setContactProfile(storeDoService.loadDoList(ContContactPerson.class, "person", personService.getCurrentPerson().getId(), 0,0).get(0));
        result.setAddresses(storeDoService.loadDoList(CPersonOtherAddress.class, "contactPerson", result.getContactProfile().getId(), 0,0));
        return result;
    }

    public ContactPersonProfile saveCurrentContactPersonProfile(ContactPersonProfile currentProfile, ContactPersonProfile editedProfile) {
        ContactPersonProfile result = new ContactPersonProfile();

        //update contact info

        ContContactPerson ccp = editedProfile.getContactProfile();
        ccp.setId(currentProfile.getContactProfile().getId());
        ccp.setPerson(currentProfile.getContactProfile().getPerson());

        result.setContactProfile(storeDoService.saveDo(ccp));


        // delete all addresses
        storeDoService.deleteAll(currentProfile.getAddresses());

        List<CPersonOtherAddress> newAddresses = new ArrayList<>();
        for(CPersonOtherAddress addr : editedProfile.getAddresses()){
            addr.setContactPerson(currentProfile.getContactProfile().getId());
            newAddresses.add(addr);
        }
        result.setAddresses(storeDoService.saveDoBatch(new BatchDomainObject<CPersonOtherAddress>(newAddresses)).getData());

        return result;
    }

    public BatchDomainObject<ProfileQualifier> loadProfileQualifier(String profileId) {
       return new BatchDomainObject<>(storeDoService.loadDoList(ProfileQualifier.class, "profile", new RdbmsId(profileId), 0,0));
    }

    public BatchDomainObject<ProfileQualifier> saveProfileQualifiers(String profileId, BatchDomainObject<ProfileQualifier> profileQualifierBatchDomainObject) {
        Id profileIdValue = new RdbmsId(profileId);
        List<ProfileQualifier> currentQualifierList = storeDoService.loadDoList(ProfileQualifier.class, "profile", profileIdValue, 0,0);
        storeDoService.deleteAll(currentQualifierList);

        if(profileQualifierBatchDomainObject.getData() != null){
            for(ProfileQualifier qualifier : profileQualifierBatchDomainObject.getData()){
                qualifier.setProfile(profileIdValue);
            }
        }

        return storeDoService.saveDoBatch(profileQualifierBatchDomainObject);
    }
}
