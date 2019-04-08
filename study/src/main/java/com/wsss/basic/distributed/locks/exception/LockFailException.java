package com.wsss.basic.distributed.locks.exception;

public class LockFailException extends RuntimeException {
	private static final long serialVersionUID = 1618004170805095133L;

	public LockFailException() {
        super();
    }

    public LockFailException(String message) {
        super(message);
    }

    public LockFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public LockFailException(Throwable cause) {
        super(cause);
    }

    protected LockFailException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
