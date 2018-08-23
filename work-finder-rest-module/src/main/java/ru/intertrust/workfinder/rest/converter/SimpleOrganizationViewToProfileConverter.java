package ru.intertrust.workfinder.rest.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.intertrust.cm.contacts.model.profile.SimpleOrganization;
import ru.intertrust.cm.core.business.api.dto.impl.RdbmsId;
import ru.intertrust.workfinder.rest.model.profile.SimpleOrganizationView;
import ru.intertrust.workfinder.service.StoreDoService;

/**
 * Created by Vitaliy Orlov on 10.01.2017.
 */
@Component
public class SimpleOrganizationViewToProfileConverter extends CommonModelConverter<SimpleOrganizationView, SimpleOrganization> {

    @Autowired
    private StoreDoService storeDoService;

    @Override
    protected void fillTargetBySource(SimpleOrganization simpleOrg, SimpleOrganizationView simpleOrgView) {
        simpleOrg.setId(new RdbmsId(simpleOrgView.getId()));
        simpleOrg.setName(simpleOrgView.getName());
        simpleOrg.setFullname(simpleOrgView.getFullName());
    }

    @Override
    protected void fillSourceFromTarget(SimpleOrganizationView simpleOrgView, SimpleOrganization simpleOrg) {
        simpleOrgView.setId(simpleOrg.getId().toStringRepresentation());
        simpleOrgView.setName(simpleOrg.getName());
        simpleOrgView.setFullName(simpleOrg.getFullname());
    }
}
