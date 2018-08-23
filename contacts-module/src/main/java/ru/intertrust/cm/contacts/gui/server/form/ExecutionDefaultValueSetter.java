package ru.intertrust.cm.contacts.gui.server.form;

import org.springframework.beans.factory.annotation.Autowired;
import ru.intertrust.cm.core.business.api.CollectionsService;
import ru.intertrust.cm.core.business.api.PersonService;
import ru.intertrust.cm.core.business.api.dto.*;
import ru.intertrust.cm.core.gui.api.server.widget.FormDefaultValueSetter;
import ru.intertrust.cm.core.gui.model.ComponentName;
import ru.intertrust.cm.core.gui.model.form.FieldPath;
import ru.intertrust.cm.core.gui.model.form.FormObjects;
import ru.intertrust.cm.core.gui.model.form.FormState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Ravil Abdulkhairov
 * Date: 16.01.2017
 * Time: 9:57
 * To change this template use File | Settings | File and Code Templates.
 */
@ComponentName("execution.value.setter")
public class ExecutionDefaultValueSetter implements FormDefaultValueSetter {
    private static final String QUERY = "select " +
            "p.Id " +
            "from participant p " +
            "join project pr on pr.id=p.project " +
            "join task t on t.project=t.id and t.id = {0} " +
            "where p.person = {1}";
    @Autowired
    PersonService personService;

    @Autowired
    CollectionsService collectionsService;

    @Override
    public Value[] getDefaultValues(FormObjects formObjects, FieldPath fieldPath) {
        return new Value[0];
    }

    @Override
    public Value getDefaultValue(FormObjects formObjects, FieldPath fieldPath) {
        return null;
    }

    @Override
    public Value[] getDefaultValues(FormState formState, FieldPath fieldPath) {
        return new Value[0];
    }

    @Override
    public Value getDefaultValue(FormState formState, FieldPath fieldPath) {
        if("task".equalsIgnoreCase(fieldPath.getPath())) {
            return new ReferenceValue(formState.getParentId());
        }
        if("participant".equalsIgnoreCase(fieldPath.getPath())) {
            return new ReferenceValue(getParticipantByPerson(formState.getParentId()));
        }
        return null;
    }

    private Id getParticipantByPerson(Id taskId){
        DomainObject person = personService.getCurrentPerson();
        List<Value> params = new ArrayList();
        params.add(new ReferenceValue(taskId));
        params.add(new ReferenceValue(person.getId()));
        IdentifiableObjectCollection identifiableObjects = collectionsService.findCollectionByQuery(QUERY,params);
        if(identifiableObjects.size()>0){
            return identifiableObjects.get(0).getId();
        }
        return null;
    }

}
