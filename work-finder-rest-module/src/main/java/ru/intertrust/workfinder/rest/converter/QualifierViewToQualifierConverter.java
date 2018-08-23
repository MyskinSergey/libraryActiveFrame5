package ru.intertrust.workfinder.rest.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.intertrust.cm.contacts.model.Qualifier;
import ru.intertrust.cm.contacts.model.QualifierGroup;
import ru.intertrust.cm.core.business.api.dto.impl.RdbmsId;
import ru.intertrust.workfinder.rest.model.qualifier.QualifierView;
import ru.intertrust.workfinder.service.StoreDoService;

/**
 * Created by Vitaliy Orlov on 10.01.2017.
 */
@Component
public class QualifierViewToQualifierConverter extends  CommonModelConverter<QualifierView, Qualifier> {


    @Autowired
    private StoreDoService storeDoService;

    @Override
    protected void fillTargetBySource(Qualifier qualifier, QualifierView qualifierView) {
        qualifier.setId(qualifierView.getQualifierId() != null ? new RdbmsId(qualifierView.getQualifierId()) : null);
    }

    @Override
    protected void fillSourceFromTarget(QualifierView qualifierView, Qualifier qualifier) {
        qualifierView.setName(qualifier.getName());
        qualifierView.setcOrder(qualifier.getcOrder());
        qualifierView.setQualifierId(qualifier.getId().toStringRepresentation());
        if(qualifier.getQualifierGroup() != null){
            QualifierGroup group = storeDoService.loadDo(qualifier.getQualifierGroup(), QualifierGroup.class);
            qualifierView.setQualifierGroupName(group.getName());
        }
    }
}
