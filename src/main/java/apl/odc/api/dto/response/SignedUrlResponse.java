package apl.odc.api.dto.response;

import java.net.URL;
import lombok.Builder;

@Builder
public record SignedUrlResponse(
        URL signedUrl
) {
    public static SignedUrlResponse of(URL signedUrl) {
        return SignedUrlResponse.builder().signedUrl(signedUrl).build();
    }
}
