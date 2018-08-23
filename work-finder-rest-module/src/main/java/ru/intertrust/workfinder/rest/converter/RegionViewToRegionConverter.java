package ru.intertrust.workfinder.rest.converter;

import org.springframework.stereotype.Component;
import ru.intertrust.cm.contacts.model.profile.Region;
import ru.intertrust.cm.core.business.api.dto.impl.RdbmsId;
import ru.intertrust.workfinder.rest.model.profile.RegionView;

/**
 * Created by Vitaliy Orlov on 10.01.2017.
 */
@Component
public class RegionViewToRegionConverter extends  CommonModelConverter<RegionView, Region> {

    @Override
    protected void fillTargetBySource(Region region, RegionView regionView) {
       region.setName(regionView.getName());
       region.setRegionId(regionView.getRegionId());
       region.setId(regionView.getId() != null ? new RdbmsId(regionView.getId()) : null);
    }

    @Override
    protected void fillSourceFromTarget(RegionView regionView, Region region) {
        regionView.setName(region.getName());
        regionView.setRegionId(region.getRegionId());
        regionView.setId(region.getId().toStringRepresentation());
    }
}
