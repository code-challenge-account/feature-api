package de.rakan.featureapi.controllers;

import de.rakan.featureapi.models.Feature;
import de.rakan.featureapi.repositories.FeatureCache;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class FeatureController {

    private final FeatureCache featureCache;

    @GetMapping("/features/")
    public List<Feature> listAllFeatures() {
        return featureCache.findAll();
    }

    @GetMapping("/features/{id}")
    public Feature getFeatureById(@PathVariable String id) {
        return featureCache.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Feature Id " + id));
    }

}
