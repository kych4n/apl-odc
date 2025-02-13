package apl.odc.global.common;

import apl.odc.global.message.FailureMessage;
import apl.odc.global.message.SuccessMessage;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;

@Getter
public class BaseResponse<T> {
    private final int status;
    private final String message;
    @JsonInclude(value = Include.NON_EMPTY)
    private final T data;

    private BaseResponse(Builder<T> builder) {
        this.status = builder.status;
        this.message = builder.message;
        this.data = builder.data;
    }

    public static BaseResponse<?> of(SuccessMessage successMessage) {
        return builder()
                .status(successMessage.getHttpStatus().value())
                .message(successMessage.getMessage())
                .build();
    }

    public static <T> BaseResponse<?> of(SuccessMessage successMessage, T data) {
        return builder()
                .status(successMessage.getHttpStatus().value())
                .message(successMessage.getMessage())
                .data(data)
                .build();
    }

    public static BaseResponse<?> of(FailureMessage failureMessage) {
        return builder()
                .status(failureMessage.getHttpStatus().value())
                .message(failureMessage.getMessage())
                .build();
    }

    public static <T> BaseResponse<?> of(FailureMessage failureMessage, T data) {
        return builder()
                .status(failureMessage.getHttpStatus().value())
                .message(failureMessage.getMessage())
                .data(data)
                .build();
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<T> {
        private int status;
        private String message;
        private T data;

        public Builder<T> status(int status) {
            this.status = status;
            return this;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public BaseResponse<T> build() {
            return new BaseResponse<T>(this);
        }
    }
}
