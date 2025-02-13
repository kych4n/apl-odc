package apl.odc.global.exception;

import apl.odc.global.message.FailureMessage;
import lombok.Getter;

@Getter
public class AplException extends RuntimeException {

	private final FailureMessage failureMessage;

	public AplException(FailureMessage failureMessage) {
		super(failureMessage.getMessage());
		this.failureMessage = failureMessage;
	}

}
