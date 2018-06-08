package ru.intertrust.custommodule.actions;

import org.springframework.beans.factory.annotation.Autowired;
import ru.intertrust.cm.core.business.api.CrudService;
import ru.intertrust.cm.core.business.api.dto.DomainObject;
import ru.intertrust.cm.core.business.api.dto.Id;
import ru.intertrust.cm.core.config.gui.action.ActionConfig;
import ru.intertrust.cm.core.gui.api.server.action.ActionHandler;
import ru.intertrust.cm.core.gui.model.ComponentName;
import ru.intertrust.cm.core.gui.model.action.SimpleActionContext;
import ru.intertrust.cm.core.gui.model.action.SimpleActionData;

/**
 * Created by Myskin Sergey on 26.04.2018.
 */

@ComponentName("a1.server.action")
public class A1ServerAction extends ActionHandler<SimpleActionContext, SimpleActionData> {

    @Autowired
    private CrudService crudService;

    @Override
    public SimpleActionData executeAction(SimpleActionContext simpleActionContext) {
        final Id rootObjectId = simpleActionContext.getRootObjectId();
        if (rootObjectId != null) {
            final DomainObject domainObject = crudService.find(rootObjectId);

            final String typeName = domainObject.getTypeName();
            final String idStr = rootObjectId.toStringRepresentation();

            String format = "Server action for domain object of '%s' with id = '%s' was called";
            final String formattedStr = String.format(format, typeName, idStr);
            System.out.println(formattedStr);
        }
        return new SimpleActionData();
    }

    @Override
    public SimpleActionContext getActionContext(ActionConfig actionConfig) {
        return new SimpleActionContext(actionConfig);
    }

}
