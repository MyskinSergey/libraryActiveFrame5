package ru.intertrust.workfinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;
import ru.intertrust.cm.contacts.model.Keyword;
import ru.intertrust.cm.contacts.model.Qualifier;
import ru.intertrust.cm.contacts.model.QualifierGroup;
import ru.intertrust.cm.contacts.model.profile.Language;
import ru.intertrust.cm.contacts.model.profile.Region;
import ru.intertrust.workfinder.service.model.RegisterInfo;

import javax.annotation.security.RunAs;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import java.util.List;

/**
 * Created by Vitaliy Orlov on 21.12.2016.
 */
@Stateless(name = "systemServiceFacade")
@Local(SystemServiceFacade.class)
@Interceptors(SpringBeanAutowiringInterceptor.class)
@RunAs("system")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SystemServiceFacadeImpl implements SystemServiceFacade {

    @Autowired
    private RegistrationAccountService registrationAccountService;

    @Autowired
    private StoreDoService storeDoService;

    @Override
    public String registerAccount(RegisterInfo info){
        return registrationAccountService.registerAccount(info);
    }
    @Override
    public boolean sendRegistrationConfirmMessage(RegisterInfo info){
        return registrationAccountService.sendConfirmMessage(info);
    }
    @Override
    public boolean applyRegistration(String token){
        return registrationAccountService.applyRegistration(token);
    }
    @Override
    public boolean rejectRegistration(String token){
        return registrationAccountService.rejectRegistration(token);
    }

    @Override
    public List<Keyword> loadKeywords(String key) {
        String resKey = key != null ? key.replaceAll("/[^A-Za-z0-9 ]/", "") : null;
        return storeDoService.loadDoList(Keyword.class, "word", "%" + resKey + "%"  , StoreDoService.CompareType.LIKE, 0, 10 );
    }

    @Override
    public List<Region> loadRegions(String key) {
        String resKey = key != null ? key.replaceAll("/[^A-Za-z0-9 ]/", "") : null;
        return storeDoService.loadDoList(Region.class, "name", "%" + resKey + "%"  , StoreDoService.CompareType.LIKE, 0, 10 );
    }

    @Override
    public List<Language> loadLanguages(String key) {
        String resKey = key != null ? key.replaceAll("/[^A-Za-z0-9 ]/", "") : null;
        return storeDoService.loadDoList(Language.class, "name", "%" + resKey + "%" , StoreDoService.CompareType.LIKE, 0, 10 );
    }

    @Override
    public List<Qualifier> loadCompetenceQualifiers() {
        QualifierGroup qualifierGroup = storeDoService.loadFirstDo(QualifierGroup.class, "uid", "102");
        if(qualifierGroup == null){
            throw new RuntimeException("Группа классификаторов {Компетенция} не найдена");
        }
        return storeDoService.loadDoList(Qualifier.class, "qualifierGroup", qualifierGroup.getId(), 0,0);
    }

    @Override
    public List<Qualifier> loadProfessionalAreaQualifiers() {
        QualifierGroup qualifierGroup = storeDoService.loadFirstDo(QualifierGroup.class, "uid", "103");
        if(qualifierGroup == null){
            throw new RuntimeException("Группа классификаторов {Профессиональная область} не найдена");
        }
        return storeDoService.loadDoList(Qualifier.class, "qualifierGroup", qualifierGroup.getId(), 0,0);
    }
}
