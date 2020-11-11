package de.rakan.featureapi.models;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Feature {
    String id;
    long timestamp;
    long beginViewingDate;
    long endViewingDate;
    String missionName;
    byte[] image;
}
