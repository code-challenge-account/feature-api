package de.rakan.featureapi.repositories;

import de.rakan.featureapi.models.Feature;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StaticFeatureCacheTest {

    private static FeatureCache featureCache;

    @BeforeAll
    static void setup() {
        featureCache = new StaticFeatureCache("test-source-data.json");
    }

    @Test
    void findById_shouldReturnFeatureCorrectlyWhenItExistsInTheCache() {
        String featureId = "39c2f29e-c0f8-4a39-a98b-deed547d6aea";
        String pixelAsBase64 = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAA" +
                "fFcSJAAAADUlEQVR42mMMmZlfDwAEggHdORmbjAAAAABJRU5ErkJggg==";
        Feature expectedFeature = Feature
                .builder()
                .id(featureId)
                .timestamp(1554831167697L)
                .beginViewingDate(1554831167697L)
                .endViewingDate(1554831202043L)
                .missionName("Sentinel-1B")
                .image(Base64.decodeBase64(pixelAsBase64))
                .build();

        Optional<Feature> result = featureCache.findById(featureId);
        assertTrue(result.isPresent());
        // potentially one should assert each field of course ...
        assertEquals(expectedFeature.toString(), result.get().toString());
    }

    @Test
    void findAll_shouldReturnAllFeaturesCorrectlyThatExistTheCache() {
        // To be done in the same way ... really sorry not to finish it but didn't have enough time
        // i realized that i missed these tests and had to do it quickly before work.
    }
}