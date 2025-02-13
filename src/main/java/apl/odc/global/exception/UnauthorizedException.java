package apl.odc.global.exception;

import apl.odc.global.message.FailureMessage;

public class UnauthorizedException extends AplException {

    public UnauthorizedException(FailureMessage failureMessage) {
        super(failureMessage);
    }

    public static UnauthorizedException wrong() {
        return new UnauthorizedException(FailureMessage.INVALID_TOKEN);
    }

    public static UnauthorizedException expired() {
        return new UnauthorizedException(FailureMessage.EXPIRED_TOKEN);
    }

    public static UnauthorizedException empty() {
        return new UnauthorizedException(FailureMessage.EMPTY_TOKEN);
    }

}
