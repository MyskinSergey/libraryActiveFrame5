package ru.intertrust.workfinder.rest;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.springframework.beans.factory.annotation.Autowired;
import ru.intertrust.cm.contacts.model.profile.SimplePerson;
import ru.intertrust.cm.core.business.api.PersonService;
import ru.intertrust.cm.core.business.api.dto.UserCredentials;
import ru.intertrust.cm.core.business.api.dto.UserUidWithPassword;
import ru.intertrust.cm.core.gui.api.server.LoginService;
import ru.intertrust.workfinder.rest.converter.SimplePersonViewToProfileConverter;
import ru.intertrust.workfinder.rest.model.AuthRequest;
import ru.intertrust.workfinder.rest.model.StatusResponse;
import ru.intertrust.workfinder.service.StoreDoService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * Created by Vitaliy Orlov on 21.12.2016.
 */
@Path("/auth/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthService {

    @Context
    private MessageContext context;
    @Autowired
    private StoreDoService storeDoService;
    @Autowired
    private SimplePersonViewToProfileConverter simplePersonViewToProfileConverter;

    @Autowired
    private PersonService personService;

    @POST
    @Path("/login/")
    public StatusResponse login(AuthRequest auth) {

        UserCredentials userCredentials = new UserUidWithPassword(auth.getLogin(), auth.getPassword());

        LoginService loginService = new ru.intertrust.cm.core.gui.impl.server.LoginServiceImpl(); // todo - get rid
        loginService.login(context.getHttpServletRequest(), userCredentials);

        StatusResponse response = new StatusResponse();
        response.setSuccess(true);
        response.setResult("login");

        SimplePerson person = storeDoService.loadFirstDo(SimplePerson.class, "person", personService.getCurrentPerson().getId());
        response.setDataResponse(simplePersonViewToProfileConverter.convertFromTarget(person));
        return response;
    }


    @Path("/logout/")
    public StatusResponse logout() {
        LoginService loginService = new ru.intertrust.cm.core.gui.impl.server.LoginServiceImpl(); // todo - get rid
        loginService.logout(context.getHttpServletRequest());
        StatusResponse response = new StatusResponse();
        response.setSuccess(true);
        response.setResult("logout");
        return response;
    }


    @Path("/noAccess")
    public StatusResponse noAccess(){
        StatusResponse response = new StatusResponse();
        response.setSuccess(false);
        response.setResult("No Access");
        return response;
    }




}
