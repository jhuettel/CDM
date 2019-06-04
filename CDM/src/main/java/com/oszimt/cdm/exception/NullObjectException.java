package com.oszimt.cdm.exception;

/**
 * Exception that should be thrown when a lookup in the database returns null instead of an empty
 * list when no entries were present.
 * @author alex.eggers
 */
public class NullObjectException extends RuntimeException {

    private static final long serialVersionUID = 20171123L;

    public NullObjectException() {
        super();
    }

    public NullObjectException(String s) {
        super(s);
    }
}
