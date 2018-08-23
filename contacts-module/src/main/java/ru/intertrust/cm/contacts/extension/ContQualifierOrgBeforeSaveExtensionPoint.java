package ru.intertrust.cm.contacts.extension;

import org.springframework.beans.factory.annotation.Autowired;

import ru.intertrust.cm.commons.DaoUtil;
import ru.intertrust.cm.core.business.api.CollectionsService;
import ru.intertrust.cm.core.business.api.dto.DomainObject;
import ru.intertrust.cm.core.business.api.dto.FieldModification;
import ru.intertrust.cm.core.dao.api.extension.BeforeSaveExtensionHandler;
import ru.intertrust.cm.core.dao.api.extension.ExtensionPoint;



import java.util.List;

/**
 * Created by Ravil Abdulkhairov on 31.10.2014.
 */
@ExtensionPoint(filter = "Cont_Qualifier_Org")
public class ContQualifierOrgBeforeSaveExtensionPoint implements BeforeSaveExtensionHandler {

    private static String FIELD_QUALIFIER = "classifier";
    private static String FIELD_GROUP_QUALIFIER = "Group_Qualifier";
    private static String FIELD_NAME = "Name";
    private static String FIELD_UID = "UID";
    private static String VALUE_UID = "101";
    private static String FIELD_ORG_TYPE = "orgtype";
    private static String FIELD_ORGANIZATION = "organization";

    @Autowired
    private DaoUtil daoUtil;

    @Autowired
    private CollectionsService collectionsService;

    @Override
    public void onBeforeSave(DomainObject domainObject, List<FieldModification> fieldModifications) {
        DomainObject qualifier;
        DomainObject groupQualifier;
        DomainObject organization;
        String groupName;


        if (domainObject != null) {
            organization = daoUtil.find(domainObject.getReference(FIELD_ORGANIZATION));
            qualifier = daoUtil.find(domainObject.getReference(FIELD_QUALIFIER));
            if (qualifier != null) {
                groupQualifier = daoUtil.find(qualifier.getReference(FIELD_GROUP_QUALIFIER));
                if (groupQualifier != null) {
                    if (groupQualifier.getString(FIELD_UID) != null && groupQualifier.getString(FIELD_UID).trim().equals(VALUE_UID)) {
                        groupName = groupQualifier.getString(FIELD_NAME);
                        if (groupName != null && organization != null) {
                            organization.setString(FIELD_ORG_TYPE, qualifier.getString(FIELD_NAME));
                            daoUtil.save(organization);
                        }
                    }
                }
            }
        }

    }
}
