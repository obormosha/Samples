package com.javarush.task.task30.task3001;

import java.math.BigInteger;

/*
Конвертер систем счислений
*/
public class Solution {
    public static void main(String[] args) {
        Number number = new Number(NumerationSystemType._10, "6");
        Number result = convertNumberToOtherNumerationSystem(number, NumerationSystemType._2);
        System.out.println(result);    //expected 110

        number = new Number(NumerationSystemType._16, "6df");
        result = convertNumberToOtherNumerationSystem(number, NumerationSystemType._8);
        System.out.println(result);    //expected 3337
    }

    public static Number convertNumberToOtherNumerationSystem(Number number, NumerationSystem expectedNumerationSystem) {
        //напишите тут ваш код
        String val = number.getDigit();
        int radix = number.getNumerationSystem().getNumerationSystemIntValue();

        int expectedRadix = expectedNumerationSystem.getNumerationSystemIntValue();
        BigInteger b = new BigInteger(val, radix);
        return new Number(expectedNumerationSystem, b.toString(expectedRadix));

    }
}
