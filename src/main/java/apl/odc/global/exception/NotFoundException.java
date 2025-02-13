package apl.odc.global.exception;

import apl.odc.global.message.FailureMessage;

public class NotFoundException extends AplException {

    public NotFoundException(FailureMessage failureMessage) {
        super(failureMessage);
    }

    public static NotFoundException wrong() {
        return new NotFoundException(FailureMessage.NOT_FOUND);
    }

}
