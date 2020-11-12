package de.rakan.featureapi.controllers;

import de.rakan.featureapi.models.FeatureDto;
import de.rakan.featureapi.repositories.FeatureCache;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@AllArgsConstructor
public class FeatureController {

    private final FeatureCache featureCache;

    @GetMapping(value = "/features", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FeatureDto> getAllFeatures() {
        return featureCache
                .findAll()
                .stream()
                .map(FeatureDto::from)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/features/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FeatureDto getFeatureById(@PathVariable String id) {
        return featureCache
                .findById(id)
                .map(FeatureDto::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Feature Id " + id));
    }


}
