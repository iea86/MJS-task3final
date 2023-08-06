package com.epam.ems.converter;

import com.epam.ems.dto.TagStatsEntity;
import com.epam.esm.entity.StatsEntity;


public class StatsConverter {

    public static TagStatsEntity convertToDto(StatsEntity statsEntity) {
        TagStatsEntity tagStatsEntity = new TagStatsEntity();
        tagStatsEntity.setTagName(statsEntity.getAttribute());
        tagStatsEntity.setFrequency(statsEntity.getValue());
        return tagStatsEntity;
    }
}
