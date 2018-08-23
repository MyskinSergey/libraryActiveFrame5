package ru.intertrust.workfinder.rest;

import org.springframework.beans.factory.annotation.Autowired;
import ru.intertrust.cm.contacts.gui.server.provider.*;
import ru.intertrust.cm.core.business.api.PersonService;
import ru.intertrust.workfinder.rest.converter.*;
import ru.intertrust.workfinder.rest.model.EnumPropertyValue;
import ru.intertrust.workfinder.rest.model.ListDataResponse;
import ru.intertrust.workfinder.rest.model.StatusResponse;
import ru.intertrust.workfinder.rest.model.status.DictionaryStatusFactory;
import ru.intertrust.workfinder.service.BaseProfileService;
import ru.intertrust.workfinder.service.StoreDoService;
import ru.intertrust.workfinder.service.SystemServiceFacade;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Vitaliy Orlov on 21.12.2016.
 */
@Path("/dictionary/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DictionaryService {

    @Autowired
    private PersonService personService;

    @Autowired
    private StoreDoService storeDoService;

    @Autowired
    private EmployeeProfileViewToProfileConverter employeeProfileViewToProfileConverter;

    @Autowired
    private BaseProfileService baseProfileService;

    @Autowired
    private SystemServiceFacade systemServiceFacade;

    @Autowired
    private KeyWordViewToKeyWordConverter keyWordViewToKeyWordConverter;

    @Autowired
    private RegionViewToRegionConverter regionViewToRegionConverter;

    @Autowired
    private LanguageViewToLanguageConverter languageViewToLanguageConverter;

    @Autowired
    private QualifierViewToQualifierConverter qualifierViewToQualifierConverter;


    @GET
    @Path("/profile-availability/")
    public StatusResponse loadProfileAvailabilityList() {
        StatusResponse response = new StatusResponse();
        try{
            List<EnumPropertyValue> propertyValue = EnumPropertyValue.loadFromMapProvider(new AvailabilityListProvider());
            response.setSuccess(true);
            response.setResult("OK");
            response.setStatusCode(new DictionaryStatusFactory().success());
            response.setDataResponse(new ListDataResponse(propertyValue));
        }catch (Exception e){
            response.setSuccess(false);
            response.setStatusCode(new DictionaryStatusFactory().getGeneralErrorCode("Ошибка при закрузке словаря :" + e.getMessage()));
        }
        return response;
    }

    @GET
    @Path("/profile-composition/")
    public StatusResponse loadProfileCompositionList() {
        StatusResponse response = new StatusResponse();
        try{
            List<EnumPropertyValue> propertyValue = EnumPropertyValue.loadFromMapProvider(new CompositionProvider());
            response.setSuccess(true);
            response.setResult("OK");
            response.setStatusCode(new DictionaryStatusFactory().success());
            response.setDataResponse(new ListDataResponse(propertyValue));
        }catch (Exception e){
            response.setSuccess(false);
            response.setStatusCode(new DictionaryStatusFactory().getGeneralErrorCode("Ошибка при закрузке словаря :" + e.getMessage()));
        }
        return response;
    }

    @GET
    @Path("/profile-employment-period/")
    public StatusResponse loadProfileEmploymentPeriodList() {
        StatusResponse response = new StatusResponse();
        try{
            List<EnumPropertyValue> propertyValue = EnumPropertyValue.loadFromMapProvider(new EmploymentPeriodProvider());
            response.setSuccess(true);
            response.setResult("OK");
            response.setStatusCode(new DictionaryStatusFactory().success());
            response.setDataResponse(new ListDataResponse(propertyValue));
        }catch (Exception e){
            response.setSuccess(false);
            response.setStatusCode(new DictionaryStatusFactory().getGeneralErrorCode("Ошибка при закрузке словаря :" + e.getMessage()));
        }
        return response;
    }


    @GET
    @Path("/profile-employment-types/")
    public StatusResponse loadProfileEmploymentTypeList() {
        StatusResponse response = new StatusResponse();
        try{
            List<EnumPropertyValue> propertyValue = EnumPropertyValue.loadFromMapProvider(new EmploymentTypesProvider());
            response.setSuccess(true);
            response.setResult("OK");
            response.setStatusCode(new DictionaryStatusFactory().success());
            response.setDataResponse(new ListDataResponse(propertyValue));
        }catch (Exception e){
            response.setSuccess(false);
            response.setStatusCode(new DictionaryStatusFactory().getGeneralErrorCode("Ошибка при закрузке словаря :" + e.getMessage()));
        }
        return response;
    }

    @GET
    @Path("/profile-expert-levels/")
    public StatusResponse loadProfileExpertLevelsList() {
        StatusResponse response = new StatusResponse();
        try{
            List<EnumPropertyValue> propertyValue = EnumPropertyValue.loadFromMapProvider(new ExpertLevelsProvider());
            response.setSuccess(true);
            response.setResult("OK");
            response.setStatusCode(new DictionaryStatusFactory().success());
            response.setDataResponse(new ListDataResponse(propertyValue));
        }catch (Exception e){
            response.setSuccess(false);
            response.setStatusCode(new DictionaryStatusFactory().getGeneralErrorCode("Ошибка при закрузке словаря :" + e.getMessage()));
        }
        return response;
    }

    @GET
    @Path("/profile-territory/")
    public StatusResponse loadProfileTerritoryList() {
        StatusResponse response = new StatusResponse();
        try{
            List<EnumPropertyValue> propertyValue = EnumPropertyValue.loadFromMapProvider(new TerritoryProvider());
            response.setSuccess(true);
            response.setResult("OK");
            response.setStatusCode(new DictionaryStatusFactory().success());
            response.setDataResponse(new ListDataResponse(propertyValue));
        }catch (Exception e){
            response.setSuccess(false);
            response.setStatusCode(new DictionaryStatusFactory().getGeneralErrorCode("Ошибка при закрузке словаря :" + e.getMessage()));
        }
        return response;
    }

    @GET
    @Path("/keywords/{key:.*}")
    public StatusResponse loadKeywords(@PathParam("key") String key ){
        StatusResponse response = new StatusResponse();
        try{
            response.setDataResponse(new ListDataResponse(keyWordViewToKeyWordConverter.convertFromListTarget(systemServiceFacade.loadKeywords(key))));
            response.setSuccess(true);
            response.setResult("OK");
            response.setStatusCode(new DictionaryStatusFactory().success());
        }catch (Exception e){
            response.setSuccess(false);
            response.setStatusCode(new DictionaryStatusFactory().getGeneralErrorCode("Ошибка при закрузке словаря :" + e.getMessage()));
        }
        return response;
    }

    @GET
    @Path("/region/{key:.*}")
    public StatusResponse loadRegions(@PathParam("key") String key ){
        StatusResponse response = new StatusResponse();
        try{
            response.setDataResponse(new ListDataResponse(regionViewToRegionConverter.convertFromListTarget(systemServiceFacade.loadRegions(key))));
            response.setSuccess(true);
            response.setResult("OK");
            response.setStatusCode(new DictionaryStatusFactory().success());
        }catch (Exception e){
            response.setSuccess(false);
            response.setStatusCode(new DictionaryStatusFactory().getGeneralErrorCode("Ошибка при закрузке словаря :" + e.getMessage()));
        }
        return response;
    }

    @GET
    @Path("/language/{key:.*}")
    public StatusResponse loadLanguages(@PathParam("key") String key ){
        StatusResponse response = new StatusResponse();
        try{
            response.setDataResponse(new ListDataResponse(languageViewToLanguageConverter.convertFromListTarget(systemServiceFacade.loadLanguages(key))));
            response.setSuccess(true);
            response.setResult("OK");
            response.setStatusCode(new DictionaryStatusFactory().success());
        }catch (Exception e){
            response.setSuccess(false);
            response.setStatusCode(new DictionaryStatusFactory().getGeneralErrorCode("Ошибка при закрузке словаря :" + e.getMessage()));
        }
        return response;
    }

    @GET
    @Path("/profile-competence-qualifiers/")
    public StatusResponse loadCompetenceQualifiers(){
        StatusResponse response = new StatusResponse();
        try{
            response.setDataResponse(new ListDataResponse(qualifierViewToQualifierConverter.convertFromListTarget(systemServiceFacade.loadCompetenceQualifiers())));
            response.setSuccess(true);
            response.setResult("OK");
            response.setStatusCode(new DictionaryStatusFactory().success());
        }catch (Exception e){
            response.setSuccess(false);
            response.setStatusCode(new DictionaryStatusFactory().getGeneralErrorCode("Ошибка при закрузке словаря :" + e.getMessage()));
        }
        return response;
    }

    @GET
    @Path("/profile-professional-area-qualifiers/")
    public StatusResponse loadProfessionalAreaQualifiers(){
        StatusResponse response = new StatusResponse();
        try{
            response.setDataResponse(new ListDataResponse(qualifierViewToQualifierConverter.convertFromListTarget(systemServiceFacade.loadProfessionalAreaQualifiers())));
            response.setSuccess(true);
            response.setResult("OK");
            response.setStatusCode(new DictionaryStatusFactory().success());
        }catch (Exception e){
            response.setSuccess(false);
            response.setStatusCode(new DictionaryStatusFactory().getGeneralErrorCode("Ошибка при закрузке словаря :" + e.getMessage()));
        }
        return response;
    }


}
