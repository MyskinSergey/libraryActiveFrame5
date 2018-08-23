package ru.intertrust.cm.contacts.extension;

import ru.intertrust.cm.core.business.api.dto.DomainObject;
import ru.intertrust.cm.core.business.api.dto.FieldModification;
import ru.intertrust.cm.core.dao.api.extension.BeforeSaveExtensionHandler;
import ru.intertrust.cm.core.dao.api.extension.ExtensionPoint;

import java.util.List;

/**
 * Created by Ravil Abdulkhairov on 20.10.2014.
 */
@ExtensionPoint(filter = "Occa_Occasion_Floated")
public class OccasionFloatedBeforeSaveExtensionPoint implements BeforeSaveExtensionHandler  {
    private static String FIELD_TYPE = "Occasion_Type";
    private static Long FIRST_TYPE = 3l;

    @Override
    public void onBeforeSave(DomainObject domainObject, List<FieldModification> fieldModifications) {
        domainObject.setLong(FIELD_TYPE, FIRST_TYPE);

    }
}
