package ru.intertrust.workfinder.rest;

import org.springframework.beans.factory.annotation.Autowired;
import ru.intertrust.cm.contacts.model.profile.*;
import ru.intertrust.cm.core.business.api.PersonService;
import ru.intertrust.cm.core.business.api.dto.DomainObject;
import ru.intertrust.workfinder.model.BatchDomainObject;
import ru.intertrust.workfinder.rest.converter.*;
import ru.intertrust.workfinder.rest.model.RegistrationAccountInfo;
import ru.intertrust.workfinder.rest.model.StatusResponse;
import ru.intertrust.workfinder.rest.model.profile.*;
import ru.intertrust.workfinder.rest.model.status.EmployeeProfileStatusFactory;
import ru.intertrust.workfinder.service.BaseProfileService;
import ru.intertrust.workfinder.service.StoreDoService;
import ru.intertrust.workfinder.service.model.AccountType;
import ru.intertrust.workfinder.service.model.RegisterInfo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Vitaliy Orlov on 21.12.2016.
 */
@Path("/profile/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfileService {

    @Autowired
    private PersonService personService;

    @Autowired
    private StoreDoService storeDoService;

    @Autowired
    private EmployeeProfileViewToProfileConverter employeeProfileViewToProfileConverter;

    @Autowired
    private ProfileLanguageSkillViewToLanguageSkillConverter profileLanguageSkillViewToLanguageSkill;

    @Autowired
    private ProfileKeyWordsViewToProfileKeyWordsConverter profileKeyWordsViewToProfileKeyWords;

    @Autowired
    private PersonContactProfileViewToContContactConverter personContactProfileViewToContContactConverter;

    @Autowired
    private ProfileQualifiesViewToProfileQualifierConverter profileQualifiesViewToProfileQualifierConverter;

    @Autowired
    private BaseProfileService baseProfileService;


    @POST
    @Path("/saveEmployeeProfile/")
    public StatusResponse saveProfile(EmployeeProfileView profileView) {
        StatusResponse response = new StatusResponse();
        try{
            EmployeeProfile profile = employeeProfileViewToProfileConverter.convertFromSource(profileView);
            baseProfileService.saveProfile(profile);
            response.setSuccess(true);
            response.setResult("OK");
            response.setStatusCode(new EmployeeProfileStatusFactory().success());
        }catch (Exception e){
            response.setSuccess(false);
            response.setStatusCode(new EmployeeProfileStatusFactory().getGeneralErrorCode("Ошибка при сохранении профиля :" + e.getMessage()));
        }
        return response;
    }

    @Path("/loadProfile/")
    @GET
    public StatusResponse loadCurrentProfile(){
        StatusResponse response = new StatusResponse();
        try{
            EmployeeProfile profile = baseProfileService.loadCurrentPersonProfile();
            EmployeeProfileView dataResponse = employeeProfileViewToProfileConverter.convertFromTarget(profile);
            response.setSuccess(true);
            response.setResult("OK");
            response.setDataResponse(dataResponse);
        }catch (Exception e){
            response.setSuccess(false);
            response.setStatusCode(new EmployeeProfileStatusFactory().getGeneralErrorCode("Ошибка при загрузке профиля :" + e.getMessage()));
        }
        return response;
    }

    @GET
    @Path("/getRegistrationInfo/")
    public StatusResponse getRegistrationInfo() {
        StatusResponse response = new StatusResponse();
        try{
            RegistrationAccountInfo result = new RegistrationAccountInfo();
            DomainObject currentPerson = personService.getCurrentPerson();
            List<RegisterInfo> regInfos  = storeDoService.loadDoList(RegisterInfo.class, "email", currentPerson.getString("login"), 0, 0);

            if(regInfos != null && !regInfos.isEmpty()){
                RegisterInfo info = regInfos.get(0);
                result.setName(info.getName());
                result.setFullName(info.getFullName());
                result.setAdditionalInfo(info.getAdditionalInfo());
                result.setAddress(info.getAddress());
                result.setEmail(info.getEmail());
                result.setInn(info.getInn());
                result.setOgrn(info.getOgrn());
                result.setPhone(info.getPhone());
                result.setPrivateAccount(AccountType.PRIVATE.equals(info.getAccountType()));

                result.setFirstName(info.getPersonFirstName());
                result.setLastName(info.getPersonLastName());
                result.setMiddleName(info.getPersonMiddleName());
                result.setPersonPosition(info.getPosition());
            }

            response.setDataResponse(result);
            response.setSuccess(true);
            response.setResult("OK");
        }catch (Exception e){
            response.setSuccess(false);
            response.setStatusCode(new EmployeeProfileStatusFactory().getGeneralErrorCode("Ошибка при загрузке профиля :" + e.getMessage()));
        }
        return response;
    }


    @GET
    @Path("/profileLanguageSkills/{profileId}")
    public StatusResponse getProfileLanguageSkills(@PathParam("profileId") String profileId){
        StatusResponse response = new StatusResponse();
        try{
            BatchDomainObject<LanguageSkills> languageSkills = baseProfileService.loadProfileLanguageSkills(profileId);
            ProfileLanguageSkillsView dataResponse = profileLanguageSkillViewToLanguageSkill.convertFromTarget(languageSkills);
            response.setSuccess(true);
            response.setResult("OK");
            response.setDataResponse(dataResponse);
        }catch (Exception e){
            response.setSuccess(false);
            response.setStatusCode(new EmployeeProfileStatusFactory().getGeneralErrorCode("Ошибка при загрузке профиля :" + e.getMessage()));
        }
        return response;
    }


    @POST
    @Path("/profileLanguageSkills/{profileId}")
    public StatusResponse getProfileLanguageSkills(@PathParam("profileId") String profileId, ProfileLanguageSkillsView profileLanguageSkillsView){
        StatusResponse response = new StatusResponse();
        try{
            BatchDomainObject<LanguageSkills> languageSkills = baseProfileService.saveProfileLanguageSkills(profileId, profileLanguageSkillViewToLanguageSkill.convertFromSource(profileLanguageSkillsView));
            ProfileLanguageSkillsView dataResponse = profileLanguageSkillViewToLanguageSkill.convertFromTarget(languageSkills);
            response.setSuccess(true);
            response.setResult("OK");
            response.setDataResponse(dataResponse);
        }catch (Exception e){
            response.setSuccess(false);
            response.setStatusCode(new EmployeeProfileStatusFactory().getGeneralErrorCode("Ошибка при загрузке профиля :" + e.getMessage()));
        }
        return response;
    }




    @GET
    @Path("/profileKeywords/{profileId}")
    public StatusResponse getProfileKeywords(@PathParam("profileId") String profileId){
        StatusResponse response = new StatusResponse();
        try{
            BatchDomainObject<ProfileKeywords> profilekeywords = baseProfileService.loadProfileKeyWords(profileId);
            ProfileKeywordsView dataResponse = profileKeyWordsViewToProfileKeyWords.convertFromTarget(profilekeywords);
            response.setSuccess(true);
            response.setResult("OK");
            response.setDataResponse(dataResponse);
        }catch (Exception e){
            response.setSuccess(false);
            response.setStatusCode(new EmployeeProfileStatusFactory().getGeneralErrorCode("Ошибка при загрузке профиля :" + e.getMessage()));
        }
        return response;
    }


    @POST
    @Path("/profileKeywords/{profileId}")
    public StatusResponse setProfileKeywords(@PathParam("profileId") String profileId, ProfileKeywordsView profilekeywordsView){
        StatusResponse response = new StatusResponse();
        try{
            BatchDomainObject<ProfileKeywords> keywords = baseProfileService.saveProfileKeyWords(profileId, profileKeyWordsViewToProfileKeyWords.convertFromSource(profilekeywordsView));
            ProfileKeywordsView dataResponse = profileKeyWordsViewToProfileKeyWords.convertFromTarget(keywords);
            response.setSuccess(true);
            response.setResult("OK");
            response.setDataResponse(dataResponse);
        }catch (Exception e){
            response.setSuccess(false);
            response.setStatusCode(new EmployeeProfileStatusFactory().getGeneralErrorCode("Ошибка при загрузке профиля :" + e.getMessage()));
        }
        return response;
    }

    @GET
    @Path("/contactCurrentProfile/")
    public StatusResponse getContactProfile(){
        StatusResponse response = new StatusResponse();
        try{
            ContactPersonProfile contactProfile = baseProfileService.loadCurrentPersonContactProfile();
            PersonContactProfileView dataResponse = personContactProfileViewToContContactConverter.convertFromTarget(contactProfile);
            response.setSuccess(true);
            response.setResult("OK");
            response.setDataResponse(dataResponse);
        }catch (Exception e){
            response.setSuccess(false);
            response.setStatusCode(new EmployeeProfileStatusFactory().getGeneralErrorCode("Ошибка при загрузке профиля :" + e.getMessage()));
        }
        return response;
    }

    @POST
    @Path("/contactCurrentProfile/")
    public StatusResponse setContactProfile(PersonContactProfileView profile){
        StatusResponse response = new StatusResponse();
        try{
            ContactPersonProfile currentProfile = baseProfileService.loadCurrentPersonContactProfile();

            ContactPersonProfile resultProfile = baseProfileService.saveCurrentContactPersonProfile(currentProfile, personContactProfileViewToContContactConverter.convertFromSource(profile));
            PersonContactProfileView dataResponse = personContactProfileViewToContContactConverter.convertFromTarget(resultProfile);

           response.setSuccess(true);
            response.setResult("OK");
            response.setDataResponse(dataResponse);
        }catch (Exception e){
            response.setSuccess(false);
            response.setStatusCode(new EmployeeProfileStatusFactory().getGeneralErrorCode("Ошибка при загрузке профиля :" + e.getMessage()));
        }
        return response;
    }



    @GET
    @Path("/profileQualifier/{profileId}")
    public StatusResponse getProfileQualifier(@PathParam("profileId") String profileId){
        StatusResponse response = new StatusResponse();
        try{
            BatchDomainObject<ProfileQualifier> profilekeywords = baseProfileService.loadProfileQualifier(profileId);
            ProfileQualifierView dataResponse = profileQualifiesViewToProfileQualifierConverter.convertFromTarget(profilekeywords);
            response.setSuccess(true);
            response.setResult("OK");
            response.setDataResponse(dataResponse);
        }catch (Exception e){
            response.setSuccess(false);
            response.setStatusCode(new EmployeeProfileStatusFactory().getGeneralErrorCode("Ошибка при загрузке профиля :" + e.getMessage()));
        }
        return response;
    }


    @POST
    @Path("/profileQualifier/{profileId}")
    public StatusResponse setProfileQualifier(@PathParam("profileId") String profileId, ProfileQualifierView profileQualifierView){
        StatusResponse response = new StatusResponse();
        try{
            BatchDomainObject<ProfileQualifier> qualifiers = baseProfileService.saveProfileQualifiers(profileId, profileQualifiesViewToProfileQualifierConverter.convertFromSource(profileQualifierView));
            ProfileQualifierView dataResponse = profileQualifiesViewToProfileQualifierConverter.convertFromTarget(qualifiers);
            response.setSuccess(true);
            response.setResult("OK");
            response.setDataResponse(dataResponse);
        }catch (Exception e){
            response.setSuccess(false);
            response.setStatusCode(new EmployeeProfileStatusFactory().getGeneralErrorCode("Ошибка при загрузке профиля :" + e.getMessage()));
        }
        return response;
    }


}
