package apl.odc.global.util;

import lombok.Getter;

@Getter
public enum SavePath {
    ORIGINAL("/app/data/original_data.csv"),
    FILTERED("/app/data/filtered_data.csv"),
    ENCRYPTED("/app/data/encrypted_data.aes"),
    FRAME("/app/filtering/src/main/java/apl/filtering/frame"),
    RULE("/app/filtering/src/main/resources/rules"),
    JSON("/app/data/mappingInfo.json"),
    NEW_BUILD("/app/filtering");

    private final String path;

    SavePath(String path) {
        this.path = path;
    }
}
