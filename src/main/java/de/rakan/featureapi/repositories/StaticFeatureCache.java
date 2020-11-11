package de.rakan.featureapi.repositories;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.rakan.featureapi.models.Feature;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
public class StaticFeatureCache implements FeatureCache {

    private final String sourceDataFileName;
    private final ObjectMapper objectMapper;
    private Map<String, Feature> cache;


    public StaticFeatureCache(final String sourceDataFileName) {
        this.sourceDataFileName = sourceDataFileName;
        this.objectMapper = new ObjectMapper();
        this.cache = new HashMap<>();
        init();
    }

    private void init() {
        log.info("Begin parsing features and filling up the feature cache ...");

        try {
            File file = ResourceUtils.getFile("classpath:" + sourceDataFileName);
            JsonNode jsonNode = objectMapper.readTree(file);
            cache = StreamSupport.stream(jsonNode.spliterator(), false)
                    .map(jsonFeatureNode -> {
                        JsonNode propertiesNode = jsonFeatureNode.get("features").get(0).get("properties");
                        String id = propertiesNode.get("id").asText();
                        long timestamp = propertiesNode.get("timestamp").asLong();
                        long beginViewingDate = propertiesNode.get("acquisition").get("beginViewingDate").asLong();
                        long endViewingDate = propertiesNode.get("acquisition").get("endViewingDate").asLong();
                        String missionName = propertiesNode.get("acquisition").get("missionName").asText();
                        String base64Image = "";
                        if (propertiesNode.has("quicklook")) {
                            base64Image = propertiesNode.get("quicklook").asText();
                        }
                        //did the decoding here to avoid any potential performance issues when serving the image.
                        return Feature
                                .builder()
                                .id(id)
                                .timestamp(timestamp)
                                .beginViewingDate(beginViewingDate)
                                .endViewingDate(endViewingDate)
                                .missionName(missionName)
                                .image(Base64.decodeBase64(base64Image))
                                .build();
                    }).collect(Collectors.toMap(Feature::getId, Function.identity()));

            log.info("Parsed {} features.", cache.size());

        } catch (IOException e) {
            log.error("Error while trying to read the data source file.", e);
        } catch (Exception e) {
            log.error("Error while trying to parse values from the json tree.", e);
        }
    }

    @Override
    public List<Feature> findAll() {
        return new ArrayList<>(cache.values());
    }

    @Override
    public Optional<Feature> findById(String id) {
        return Optional.ofNullable(cache.get(id));
    }

}
