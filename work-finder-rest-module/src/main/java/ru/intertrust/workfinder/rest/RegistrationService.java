package ru.intertrust.workfinder.rest;

import org.springframework.beans.factory.annotation.Autowired;
import ru.intertrust.workfinder.rest.model.RegistrationAccountInfo;
import ru.intertrust.workfinder.rest.model.StatusResponse;
import ru.intertrust.workfinder.rest.model.status.RegAccountStatusFactory;
import ru.intertrust.workfinder.service.SystemServiceFacade;
import ru.intertrust.workfinder.service.model.AccountType;
import ru.intertrust.workfinder.service.model.RegisterInfo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

/**
 * Created by Vitaliy Orlov on 01.12.2016.
 */
@Path("/registration/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegistrationService {


    @Autowired
    private SystemServiceFacade systemServiceFacade;

    @POST
    @Path("/takeRegInfo/")
    public StatusResponse takeRegInfo(RegistrationAccountInfo request){
        RegisterInfo info = new RegisterInfo();
        info.setName(request.getName());
        info.setFullName(request.getFullName());
        info.setEmail(request.getEmail());
        info.setPhone(request.getPhone());
        info.setAddress(request.getAddress());
        info.setAccountType(request.isPrivateAccount() ? AccountType.PRIVATE : AccountType.ORGANIZATION);
        info.setAdditionalInfo(request.getAdditionalInfo());
        info.setInn(request.getInn());
        info.setOgrn(request.getOgrn());
        info.setPersonFirstName(request.getFirstName());
        info.setPersonLastName(request.getLastName());
        info.setPersonMiddleName(request.getMiddleName());
        info.setPosition(request.getPersonPosition());

        if(request.getPassword() == null || request.getPassword().trim().isEmpty()){
            info.setPassword(generateTempPassword());
        }else{
            info.setPassword(request.getPassword());
        }

        StatusResponse responce = new StatusResponse();

        try{
            String resultToken = systemServiceFacade.registerAccount(info);
            responce.setSuccess(resultToken != null);
            responce.setResult("OK");
            responce.setStatusCode(new RegAccountStatusFactory().success());
        }catch (Exception e){
            responce.setSuccess(false);
            responce.setStatusCode(new RegAccountStatusFactory().accountAlreadyExist());
        }
        return responce;
    }

    private String generateTempPassword() {
        return UUID.randomUUID().toString().substring(0,8);
    }

    @GET
    @Path("/test/{id}/")
    public StatusResponse searchAddress(@PathParam("id") String id){
        StatusResponse responce = new StatusResponse();
        responce.setResult("1212e12");
        return responce;
    }

}
