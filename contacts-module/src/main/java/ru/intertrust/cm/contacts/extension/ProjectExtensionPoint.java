package ru.intertrust.cm.contacts.extension;

import org.springframework.beans.factory.annotation.Autowired;
import ru.intertrust.cm.core.business.api.CollectionsService;
import ru.intertrust.cm.core.business.api.CrudService;
import ru.intertrust.cm.core.business.api.PersonService;
import ru.intertrust.cm.core.business.api.dto.*;
import ru.intertrust.cm.core.dao.api.extension.AfterSaveExtensionHandler;
import ru.intertrust.cm.core.dao.api.extension.BeforeSaveExtensionHandler;
import ru.intertrust.cm.core.dao.api.extension.ExtensionPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Developer: Ravil Abdulkhairov
 * Date: 12.01.2017
 * Time: 16:46
 * To change this template use File | Settings | File and Code Templates.
 */
@ExtensionPoint(filter = "project")
public class ProjectExtensionPoint implements BeforeSaveExtensionHandler,AfterSaveExtensionHandler {

    private static final String QUERY = "SELECT * FROM participant WHERE project = {0} AND role=3";
    private static final String FIELD_AUTHOR = "author";
    private static final String DO_PARTICIPANT = "participant";
    private static final String FIELD_ROLE = "role";
    private static final String FIELD_PROJECT = "project";
    private static final String FIELD_PERSON = "person";
    @Autowired
    PersonService personService;

    @Autowired
    CrudService crudService;

    @Autowired
    CollectionsService collectionsService;

    @Override
    public void onBeforeSave(DomainObject domainObject, List<FieldModification> list) {
        if(domainObject.isNew()){
            domainObject.setReference(FIELD_AUTHOR,personService.getCurrentPerson());
        }
    }

    @Override
    public void onAfterSave(DomainObject domainObject, List<FieldModification> list) {
        Id objectId = domainObject.getId();
        List<Value> params = new ArrayList<>();
        params.add(new ReferenceValue(objectId));
        IdentifiableObjectCollection curators = collectionsService.findCollectionByQuery(QUERY,params);
        if(curators.size()==0){
            DomainObject newCurator = crudService.createDomainObject(DO_PARTICIPANT);
            newCurator.setLong(FIELD_ROLE,3l);
            newCurator.setReference(FIELD_PROJECT,objectId);
            newCurator.setReference(FIELD_PERSON,personService.getCurrentPerson());
            crudService.save(newCurator);
        }
    }
}
