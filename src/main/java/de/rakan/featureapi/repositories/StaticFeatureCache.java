package de.rakan.featureapi.repositories;

import de.rakan.featureapi.models.Feature;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class StaticFeatureCache implements FeatureCache {

    @Override
    public List<Feature> findAll() {
        return null;
    }

    @Override
    public Optional<Feature> findById(String id) {
        return Optional.empty();
    }

}
