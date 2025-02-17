package apl.odc.global.util;

import lombok.Getter;

@Getter
public enum SavePath {
    ORIGINAL("src/main/resources/data/original_data.csv"),
    FILTERED("src/main/resources/data/filtered_data.csv");

    private final String path;

    SavePath(String path) {
        this.path = path;
    }
}
