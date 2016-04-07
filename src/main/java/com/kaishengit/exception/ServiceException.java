package com.kaishengit.exception;

/**
 * 自定义异常：用来标记业务异常
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 5227803783619562547L;

    public ServiceException(){}

    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(Throwable th) {
        super(th);
    }

    public ServiceException(Throwable th, String message) {
        super(message,th);
    }

}
