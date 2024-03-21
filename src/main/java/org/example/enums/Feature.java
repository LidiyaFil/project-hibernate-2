package org.example.enums;

import static java.util.Objects.isNull;

public enum Feature {
    TRAILERS("Trailers"),
    COMMENTARIES("Commentaries"),
    DELETED_SCENES("Deleted Scenes"),
    BEHIND_THE_SCENES("Behind the Scenes");

    private final String value;

    Feature(String value) {
        this.value = value;
    }

    public static Feature getFeatureByValue(String value) {
        if (isNull(value) || value.isEmpty()) {
            return null;
        }
        Feature[] values = Feature.values();
        for (Feature feature : values) {
            if (feature.value.equals(value)) {
                return feature;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }
}
