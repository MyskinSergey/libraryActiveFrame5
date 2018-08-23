package ru.intertrust.cm.contacts.gui.server.action;

import org.springframework.beans.factory.annotation.Autowired;

import ru.intertrust.cm.commons.DobjectsManager;
import ru.intertrust.cm.core.business.api.CrudService;
import ru.intertrust.cm.core.business.api.dto.DomainObject;
import ru.intertrust.cm.core.business.api.dto.Id;
import ru.intertrust.cm.core.gui.impl.server.action.SimpleActionHandler;
import ru.intertrust.cm.core.gui.model.ComponentName;
import ru.intertrust.cm.core.gui.model.action.SimpleActionContext;
import ru.intertrust.cm.core.gui.model.action.SimpleActionData;


/**
 * Created by Konstantin Gordeev on 14.10.2014.
 */
@ComponentName("deactivate.contact.name.action")
public class DeactivateContactNameHandler extends SimpleActionHandler {

    private static final String STATUS_DEACTIVATED = "Cancelled";

    @Autowired
    private CrudService crudService;

    @Autowired
    private DobjectsManager dobjectsManager;

    @Override
    public SimpleActionData executeAction(SimpleActionContext context) {
        Id rootObjectId = context.getRootObjectId();
        SimpleActionData actionData = new SimpleActionData();
        if (rootObjectId != null) {
            DomainObject rootDomainObject = crudService.find(rootObjectId);

            if (!dobjectsManager.getStatusById(rootDomainObject.getStatus()).equals(STATUS_DEACTIVATED)) {
                dobjectsManager.changeStatusForDo(rootDomainObject.getId(), STATUS_DEACTIVATED);
            }
        }
        return actionData;
    }
}