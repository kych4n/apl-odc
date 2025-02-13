package apl.odc.global.exception;

import apl.odc.global.message.FailureMessage;

public class BadRequestException extends AplException {

	public BadRequestException(FailureMessage failureMessage) {
		super(failureMessage);
	}

	public static BadRequestException wrong() {
		return new BadRequestException(FailureMessage.BAD_REQUEST);
	}

}