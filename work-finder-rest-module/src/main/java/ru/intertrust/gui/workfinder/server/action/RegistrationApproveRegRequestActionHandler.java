package ru.intertrust.gui.workfinder.server.action;


import org.springframework.beans.factory.annotation.Autowired;
import ru.intertrust.cm.commons.DobjectsManager;
import ru.intertrust.cm.core.business.api.CrudService;
import ru.intertrust.cm.core.config.gui.action.ActionConfig;
import ru.intertrust.cm.core.gui.api.server.action.ActionHandler;
import ru.intertrust.cm.core.gui.impl.server.action.FormPluginHandlerStatusData;
import ru.intertrust.cm.core.gui.model.ComponentName;
import ru.intertrust.cm.core.gui.model.action.ActionContext;
import ru.intertrust.cm.core.gui.model.action.SaveActionContext;
import ru.intertrust.cm.core.gui.model.action.SimpleActionData;
import ru.intertrust.workfinder.service.RegistrationAccountService;


@ComponentName("reg.approve.reg.request.action")
public class RegistrationApproveRegRequestActionHandler extends ActionHandler<ActionContext, SimpleActionData>{

    @Autowired
    private CrudService crudService;

    @Autowired
    private RegistrationAccountService registrationAccountService;

    @Autowired
    private DobjectsManager dobjectsManager;

    @Override
    public SimpleActionData executeAction(ActionContext actionContext) {
        ActionConfig actionConfig = (ActionConfig)actionContext.getActionConfig();

        registrationAccountService.applyRegistration(actionContext.getRootObjectId());

        dobjectsManager.changeStatusForDo(actionContext.getRootObjectId(), "reg_status_apply");
        SimpleActionData result = new SimpleActionData();
        result.setSavedMainObjectId(actionContext.getRootObjectId());
        return result;
    }


    @Override
    public ActionContext getActionContext(ActionConfig actionConfig) {
        return new SaveActionContext(actionConfig);
    }

    @Override
    public HandlerStatusData getCheckStatusData() {
        return new FormPluginHandlerStatusData();
    }
}
