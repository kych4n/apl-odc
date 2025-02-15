package apl.odc.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignInRequest(
        @NotBlank
        String username,
        @NotBlank
        String password,
        @NotNull
        Integer totp
) {
}
