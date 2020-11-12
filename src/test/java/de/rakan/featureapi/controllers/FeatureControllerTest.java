package de.rakan.featureapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.rakan.featureapi.models.Feature;
import de.rakan.featureapi.models.dtos.FeatureDto;
import de.rakan.featureapi.repositories.FeatureCache;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
class FeatureControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FeatureCache featureCacheMock;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void setUp() {
    }

    @Test
    void getFeatureById_shouldReturnCorrectResponseWhenTheFeatureIdExistsInTheCache() throws Exception {
        String featureId = "39c2f29e-c0f8-4a39-a98b-deed547d6aea";
        Feature expectedFeature = Feature
                .builder()
                .id(featureId)
                .timestamp(1554831167697L)
                .beginViewingDate(1554831167697L)
                .endViewingDate(1554831202043L)
                .missionName("Sentinel-1B")
                .build();

        FeatureDto expectedFeatureDto = FeatureDto.from(expectedFeature);

        String expectedResponse = objectMapper.writeValueAsString(expectedFeatureDto);

        Mockito.when(featureCacheMock.findById(featureId)).thenReturn(Optional.of(expectedFeature));

        mvc.perform(MockMvcRequestBuilders.get("/features/39c2f29e-c0f8-4a39-a98b-deed547d6aea")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(expectedResponse)));
    }

    @Test
    void getFeatureById_shouldReturn404WhenTheFeatureIdDoesNotExistInTheCache() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/features/i-dont-exist")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllFeatures_shouldReturnCorrectlyAllFeaturesInTheCache() throws Exception {
        List<Feature> allFeaturesInTheCache = new ArrayList<>();
        Feature expectedFeature1 = Feature
                .builder()
                .id("39c2f29e-c0f8-4a39-a98b-deed547d6aea")
                .timestamp(1554831167697L)
                .beginViewingDate(1554831167697L)
                .endViewingDate(1554831202043L)
                .missionName("Sentinel-1B")
                .build();

        Feature expectedFeature2 = Feature
                .builder()
                .id("12d0b505-2c70-4420-855c-936d05c55669")
                .timestamp(1555477219508L)
                .beginViewingDate(1555477219508L)
                .endViewingDate(1555477244506L)
                .missionName("Sentinel-1A")
                .build();

        allFeaturesInTheCache.add(expectedFeature1);
        allFeaturesInTheCache.add(expectedFeature2);
        List<FeatureDto> expectedFeatureDtos = allFeaturesInTheCache
                .stream()
                .map(FeatureDto::from)
                .collect(Collectors.toList());

        String expectedResponse = objectMapper.writeValueAsString(expectedFeatureDtos);

        Mockito.when(featureCacheMock.findAll()).thenReturn(allFeaturesInTheCache);

        mvc.perform(MockMvcRequestBuilders.get("/features")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(expectedResponse)));
    }

    @Test
    void getFeatureImage_shouldReturnCorrectResponseWhenTheFeatureIdExistsInTheCache() throws Exception {
        String featureId = "39c2f29e-c0f8-4a39-a98b-deed547d6aea";
        String pixelAsBase64 = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUl" +
                "EQVR42mP81Cf8AgAGigJ9VsiE5QAAAABJRU5ErkJggg==";
        byte[] imageAsByte = Base64.decodeBase64(pixelAsBase64);

        Feature expectedFeature = Feature
                .builder()
                .id(featureId)
                .image(imageAsByte)
                .build();

        Mockito.when(featureCacheMock.findById(featureId)).thenReturn(Optional.of(expectedFeature));

        mvc.perform(MockMvcRequestBuilders.get("/features/39c2f29e-c0f8-4a39-a98b-deed547d6aea/quicklook")
                .accept(MediaType.IMAGE_PNG))
                .andExpect(status().isOk())
                .andExpect(content().bytes(imageAsByte));
    }

    @Test
    void getFeatureImage_shouldReturn404WhenTheFeatureIdDoesNotExistInTheCache() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/features/i-dont-exist/quicklook")
                .accept(MediaType.IMAGE_PNG))
                .andExpect(status().isNotFound());
    }
}