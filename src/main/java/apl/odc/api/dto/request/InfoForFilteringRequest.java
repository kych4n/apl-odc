package apl.odc.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InfoForFilteringRequest(
        @NotBlank
        String ruleUrl,
        @NotBlank
        String frameUrl,
        @NotNull
        MappingInfoDTO mappingInfo
) {
}
