package de.rakan.featureapi.models;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FeatureDto {
    String id;
    long timestamp;
    long beginViewingDate;
    long endViewingDate;
    String missionName;

    public static FeatureDto from(Feature featureModel) {
        return FeatureDto
                .builder()
                .id(featureModel.getId())
                .timestamp(featureModel.getTimestamp())
                .beginViewingDate(featureModel.getBeginViewingDate())
                .endViewingDate(featureModel.getEndViewingDate())
                .missionName(featureModel.getMissionName())
                .build();
    }
}
