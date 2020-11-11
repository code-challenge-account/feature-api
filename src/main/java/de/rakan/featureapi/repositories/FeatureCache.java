package de.rakan.featureapi.repositories;

import de.rakan.featureapi.models.Feature;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeatureCache {
    List<Feature> findAll();
    Optional<Feature> findById(String id);
}
