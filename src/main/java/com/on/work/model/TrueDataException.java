package com.on.work.model;

/**
 * Created by jzhou on 8/16/15.
 */
public class TrueDataException extends RuntimeException {
    public final DataError error;
    public TrueDataException(DataError e, Exception ex) {
        super(ex);
        this.error = e;
    }
    public TrueDataException(DataError e) {
        this.error = e;
    }
}
