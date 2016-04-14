package com.kaishengit;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {

    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(Test.class);

        //级别
        logger.debug("debug {} message{}","aa","zz");
        logger.info("info message");
        logger.warn("warn message");
        logger.error("error message");


    }

}
