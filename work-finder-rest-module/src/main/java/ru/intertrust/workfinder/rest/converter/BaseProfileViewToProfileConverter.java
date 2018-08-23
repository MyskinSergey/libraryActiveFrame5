package ru.intertrust.workfinder.rest.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.intertrust.cm.contacts.gui.server.provider.*;
import ru.intertrust.cm.contacts.model.profile.BaseProfile;
import ru.intertrust.cm.contacts.model.profile.Region;
import ru.intertrust.cm.core.business.api.dto.impl.RdbmsId;
import ru.intertrust.workfinder.rest.model.EnumPropertyValue;
import ru.intertrust.workfinder.rest.model.profile.BaseProfileView;
import ru.intertrust.workfinder.service.StoreDoService;

/**
 * Created by Vitaliy Orlov on 10.01.2017.
 */
@Component
public class BaseProfileViewToProfileConverter extends CommonModelConverter<BaseProfileView, BaseProfile> {

    @Autowired
    private RegionViewToRegionConverter regionViewToRegionConverter;

    @Autowired
    private StoreDoService storeDoService;


    @Override
    protected void fillTargetBySource(BaseProfile baseProfile, BaseProfileView profileView) {
        baseProfile.setDescription(profileView.getDescription());
        baseProfile.setName(profileView.getName());
        baseProfile.setAvailability(EnumPropertyValue.getKeyFromValue(profileView.getAvailability()));
        baseProfile.setEmploymentPeriod(EnumPropertyValue.getKeyFromValue(profileView.getEmploymentPeriod()));
        baseProfile.setEmploymentType(EnumPropertyValue.getKeyFromValue(profileView.getEmploymentType()));
        baseProfile.setComposition(EnumPropertyValue.getKeyFromValue(profileView.getComposition()));
        baseProfile.setCancellationDate(profileView.getCancellationDate());
        baseProfile.setExpertiseLevel(EnumPropertyValue.getKeyFromValue(profileView.getExpertiseLevel()));
        baseProfile.setPublicationDate(profileView.getPublicationDate());

        Region region = regionViewToRegionConverter.convertFromSource(profileView.getRegionView());
        baseProfile.setRegion(region != null ? region.getId() : null);
        baseProfile.setTerritory(EnumPropertyValue.getKeyFromValue(profileView.getTerritory()));
        baseProfile.setId(profileView.getId() != null ? new RdbmsId(profileView.getId()) : null);
    }

    @Override
    protected void fillSourceFromTarget(BaseProfileView view, BaseProfile source) {
        view.setId(source.getId().toStringRepresentation());

        view.setDescription(source.getDescription());
        view.setName(source.getName());

        view.setAvailability(EnumPropertyValue.loadFromMapProvider(new AvailabilityListProvider(), source.getAvailability()));
        view.setEmploymentPeriod(EnumPropertyValue.loadFromMapProvider( new EmploymentPeriodProvider(),source.getEmploymentPeriod()));
        view.setEmploymentType(EnumPropertyValue.loadFromMapProvider( new EmploymentTypesProvider(), source.getEmploymentType()));
        view.setComposition(EnumPropertyValue.loadFromMapProvider( new CompositionProvider(), source.getComposition()));
        view.setCancellationDate(source.getCancellationDate());
        view.setExpertiseLevel(EnumPropertyValue.loadFromMapProvider( new ExpertLevelsProvider(), source.getExpertiseLevel()));
        view.setTerritory(EnumPropertyValue.loadFromMapProvider( new TerritoryProvider(),  source.getTerritory()));
        view.setPublicationDate(source.getPublicationDate());

        if(source.getRegion() != null){
            Region region = storeDoService.loadDo(source.getRegion(), Region.class);
            view.setRegionView(regionViewToRegionConverter.convertFromTarget(region));
        }
    }


}
