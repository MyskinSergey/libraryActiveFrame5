package ru.intertrust.cm.contacts.gui.server.action;

import org.springframework.beans.factory.annotation.Autowired;

import ru.intertrust.cm.commons.DobjectsManager;
import ru.intertrust.cm.core.business.api.CrudService;
import ru.intertrust.cm.core.business.api.dto.DomainObject;
import ru.intertrust.cm.core.business.api.dto.Id;
import ru.intertrust.cm.core.gui.api.server.action.ActionHandler;
import ru.intertrust.cm.core.gui.model.ComponentName;
import ru.intertrust.cm.core.gui.model.action.ActionContext;
import ru.intertrust.cm.core.gui.model.action.SimpleActionData;

/**
 * Created by Konstantin on 12.11.2014.
 */
@ComponentName("set.aside.envelope.action")
public class SetAsideEnvelopeHandler extends ActionHandler<ActionContext, SimpleActionData> {

    private static final String STATUS_READY = "Prepared";
    private static final String STATUS_ASIDE = "Отложен";

    @Autowired
    private CrudService crudService;

    @Autowired
    private DobjectsManager dobjectsManager;

    @Override
    public SimpleActionData executeAction(ActionContext context) {
        Id rootObjectId = context.getRootObjectId();
        SimpleActionData actionData = new SimpleActionData();
        if (rootObjectId != null) {
            DomainObject rootDomainObject = crudService.find(rootObjectId);

            if (dobjectsManager.getStatusById(rootDomainObject.getStatus()).equals(STATUS_READY)) {
                dobjectsManager.changeStatusForDo(rootDomainObject.getId(), STATUS_ASIDE);
            }
        }
        return actionData;
    }
}