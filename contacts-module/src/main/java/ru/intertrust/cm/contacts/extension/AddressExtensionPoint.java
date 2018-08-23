package ru.intertrust.cm.contacts.extension;

import org.springframework.beans.factory.annotation.Autowired;
import ru.intertrust.cm.core.business.api.CrudService;
import ru.intertrust.cm.core.business.api.dto.*;
import ru.intertrust.cm.core.dao.api.extension.BeforeSaveExtensionHandler;
import ru.intertrust.cm.core.dao.api.extension.ExtensionPoint;

import java.util.List;

/**
 * Created by Konstantin Gordeev on 18.02.2015.
 */
@ExtensionPoint(filter = "Addr_Address")
public class AddressExtensionPoint implements BeforeSaveExtensionHandler {

    @Autowired
    private CrudService crudService;

    @Override
    public void onBeforeSave(DomainObject domainObject, List<FieldModification> fieldModifications) {
        if (domainObject.getTypeName().equals("Addr_Other_Address")) {
            domainObject.setString("type", "Прочий адрес");
            domainObject.setString("title", domainObject.getString("Value"));
        } else if (domainObject.getTypeName().equals("Addr_Mailing_Address")) {
            domainObject.setString("type", "Почтовый адрес");
            domainObject.setString("title", getStringAddress(domainObject));
        }
    }

    public String getStringAddress(DomainObject address) {
        StringBuilder sb = new StringBuilder();
        Id strTypeId = address.getReference("Street_Type");
        if (strTypeId != null) {
            DomainObject strType = crudService.find(strTypeId);
            sb.append(strType.getString("Title"));
        }
        String street = address.getString("Street");
        if (street != null) {
            sb.append(" ").append(street);
        }
        String house = address.getString("House");
        if (house != null) {
            sb.append(", д. ").append(house);
        }

        String housing = address.getString("Housing");
        if (housing != null) {
            sb.append(", корп. ").append(housing);
        }

        String building = address.getString("Building");
        if (building != null) {
            sb.append(", строение ").append(building);
        }

        String room = address.getString("Room");
        if (room != null) {
            sb.append(", кв. ").append(room);
        }

        DomainObject settlement = crudService.find(address.getReference("Settlement"));
        DomainObject settlementType = crudService.find(settlement.getReference("Settlement_Type"));

        String settlType = settlementType.getString("Title");
        String settlementName = settlement.getString("Name");

        sb.append(", ").append(settlType).append(" ").append(settlementName);

        DomainObject region = crudService.find(settlement.getReference("Region"));
        String strRegion = region.getString("Title");

        sb.append(", ").append(strRegion);
        return sb.toString();
    }
}