package ru.intertrust.cm.contacts.gui.server.form;

import org.springframework.beans.factory.annotation.Autowired;
import ru.intertrust.cm.core.business.api.CollectionsService;
import ru.intertrust.cm.core.business.api.PersonService;
import ru.intertrust.cm.core.business.api.dto.Id;
import ru.intertrust.cm.core.business.api.dto.IdentifiableObjectCollection;
import ru.intertrust.cm.core.business.api.dto.ReferenceValue;
import ru.intertrust.cm.core.business.api.dto.Value;
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
 * Time: 12:41
 * To change this template use File | Settings | File and Code Templates.
 */
@ComponentName("confirmation.value.setter")
public class ConfirmationDefaultValueSetter implements FormDefaultValueSetter {

    private static final String QUERY = "select " +
            "P.id " +
            "from participant P " +
            "join task T on T.project = P.project " +
            "join execution E on E.task = T.id and E.id = {0} " +
            "where P.role=3 AND P.person = {1}";

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
        if("execution".equalsIgnoreCase(fieldPath.getPath())) {
            return new ReferenceValue(formState.getParentId());
        }
        if("participant".equalsIgnoreCase(fieldPath.getPath())) {
            return new ReferenceValue(getParticipantByPerson(formState.getParentId()));
        }
        return null;
    }

    private Id getParticipantByPerson(Id executionId){
        List<Value> params = new ArrayList<>();
        params.add(new ReferenceValue(executionId));
        params.add(new ReferenceValue(personService.getCurrentPerson().getId()));
        IdentifiableObjectCollection participant = collectionsService.findCollectionByQuery(QUERY,params);
        if(participant.size()>0){
            return participant.get(0).getId();
        } else {
            return null;
        }
    }
}
