package ru.intertrust.workfinder.service;

import ru.intertrust.cm.contacts.model.Keyword;
import ru.intertrust.cm.contacts.model.Qualifier;
import ru.intertrust.cm.contacts.model.profile.Language;
import ru.intertrust.cm.contacts.model.profile.Region;
import ru.intertrust.workfinder.service.model.RegisterInfo;

import java.util.List;

/**
 * Created by Vitaliy Orlov on 21.12.2016.
 */
public interface SystemServiceFacade {

    public String registerAccount(RegisterInfo info);
    public boolean applyRegistration(String token);
    public boolean rejectRegistration(String token);
    public boolean sendRegistrationConfirmMessage(RegisterInfo info);
    public List<Keyword> loadKeywords(String key);
    public List<Region> loadRegions(String key);
    public List<Language> loadLanguages(String key);
    public List<Qualifier> loadCompetenceQualifiers();
    public List<Qualifier> loadProfessionalAreaQualifiers();

}
