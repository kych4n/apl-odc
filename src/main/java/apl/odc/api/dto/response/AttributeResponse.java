package apl.odc.api.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record AttributeResponse(
        List<String> attributes
) {
    public static AttributeResponse of(List<String> attributes) {
        return AttributeResponse
                .builder()
                .attributes(attributes)
                .build();
    }
}
