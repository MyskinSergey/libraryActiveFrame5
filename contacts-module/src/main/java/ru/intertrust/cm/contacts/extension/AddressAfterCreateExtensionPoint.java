package ru.intertrust.cm.contacts.extension;

import org.springframework.beans.factory.annotation.Autowired;
import ru.intertrust.cm.core.business.api.CollectionsService;
import ru.intertrust.cm.core.business.api.dto.DomainObject;
import ru.intertrust.cm.core.business.api.dto.Id;
import ru.intertrust.cm.core.business.api.dto.IdentifiableObjectCollection;
import ru.intertrust.cm.core.dao.api.extension.AfterCreateExtentionHandler;
import ru.intertrust.cm.core.dao.api.extension.ExtensionPoint;

/**
 * Created by Konstantin Gordeev on 13.10.2014.
 */
@ExtensionPoint(filter = "Addr_Mailing_Address")
public class AddressAfterCreateExtensionPoint implements AfterCreateExtentionHandler {

    private static final String QUERY_FOR_DEFAULT_STREET_TYPE = "SELECT st.id FROM\n" +
            "Addr_Street_Type st \n" +
            "WHERE st.uid = '729'";

    private static final String QUERY_FOR_DEFAULT_ASSIGNING = "SELECT a.id FROM\n" +
            "Addr_Addresses_Assigning a\n" +
            "WHERE a.uid = '1'";

    @Autowired
    protected CollectionsService collectionsService;

    @Override
    public void onAfterCreate(DomainObject createdDomainObject) {
        Id strType = getDefaultStreetType();
        if (strType != null) {
            createdDomainObject.setReference("Street_Type", strType);
        }

        Id assign = getDefaultAssigning();
        if (assign != null) {
            createdDomainObject.setReference("Addresses_Assigning", assign);
        }
    }

    private Id getDefaultStreetType() {
        IdentifiableObjectCollection types = collectionsService.findCollectionByQuery(QUERY_FOR_DEFAULT_STREET_TYPE);

        if (types != null && types.size() != 0) {
            return types.getId(0);
        }
        return null;
    }

    private Id getDefaultAssigning() {
        IdentifiableObjectCollection assignings = collectionsService.findCollectionByQuery(QUERY_FOR_DEFAULT_ASSIGNING);

        if (assignings != null && assignings.size() != 0) {
            return assignings.getId(0);
        }
        return null;
    }
}