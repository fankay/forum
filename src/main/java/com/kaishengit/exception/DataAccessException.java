package com.kaishengit.exception;

/**
 * 自定义异常：用来标记数据库访问出现的异常
 */
public class DataAccessException extends RuntimeException {

    private static final long serialVersionUID = 5227803783619562547L;

    public DataAccessException(){}

    public DataAccessException(Throwable th) {
        super(th);
    }

    public DataAccessException(Throwable th,String message) {
        super(message,th);
    }

}
