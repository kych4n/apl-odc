package apl.odc.api.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record MappingInfoDTO(
        @NotNull
        List<String> odcAttributes,
        @NotNull
        List<String> udcAttributes
) {
}
