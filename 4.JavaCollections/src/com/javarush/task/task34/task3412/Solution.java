package com.javarush.task.task34.task3412;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/* 
Добавление логирования в класс
*/

public class Solution {
    private static final Logger logger = LoggerFactory.getLogger(Solution.class);


    private int value1;
    private String value2;
    private Date value3;

    public Solution(int value1, String value2, Date value3) {
        logger.debug("change value");
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }

    public static void main(String[] args) {

    }

    public void calculateAndSetValue3(long value) {
        value -= 133;
        if (value > Integer.MAX_VALUE) {
            logger.trace("");
            value1 = (int) (value / Integer.MAX_VALUE);
            logger.debug("change value");
        } else {
            value1 = (int) value;
            logger.debug("change value");
        }
    }

    public void printString() {
        if (value2 != null) {
            logger.trace("");
            System.out.println(value2.length());
        }
    }

    public void printDateAsLong() {
        if (value3 != null) {
            logger.trace("");
            System.out.println(value3.getTime());
        }
    }

    public void divide(int number1, int number2) {
        try {
            logger.trace("");
            System.out.println(number1 / number2);
        } catch (ArithmeticException e) {
            logger.error("Err");
        }
    }

    public void setValue1(int value1) {
        this.value1 = value1;
        logger.debug("change value");
    }

    public void setValue2(String value2) {
        this.value2 = value2;
        logger.debug("change value");
    }

    public void setValue3(Date value3) {
        this.value3 = value3;
        logger.debug("change value");
    }
}
