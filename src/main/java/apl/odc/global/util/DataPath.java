package apl.odc.global.util;

import lombok.Getter;

@Getter
public enum DataPath {
    ORIGINAL("src/main/resources/data/original_data.csv"),
    FILTERED("src/main/resources/data/filtered_data.csv");

    private final String path;

    DataPath(String path) {
        this.path = path;
    }
}
