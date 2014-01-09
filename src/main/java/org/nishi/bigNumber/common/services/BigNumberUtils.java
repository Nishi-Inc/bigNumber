package org.nishi.bigNumber.common.services;

import org.nishi.bigNumber.models.BigNumber;

/**
 * @author aloks
 * Date: 21/11/13
 * Time: 3:35 PM
 * @since 2.0.0
 */
public final class BigNumberUtils {
    // TODO #33 : BigNumberUtil will implement StaticMethods while BigNumber will implement NonStaticMethods which extends Serializable and Comparable<BigNumber>
    // TODO Make necessary changes in Tests
    // TODO Set default mathContext in BigNUmber constructor

    // Singleton
    private BigNumberUtils(){}

    /*==========================================================
	 * 						Public Static Methods
	 * =========================================================
	 */

    /**
     * Same as sum()
     * @param numbers BigNumber numbers
     * @return A BigNumber type variable containing result of arithmetic addition of the provided numbers
     */
    public static BigNumber add(BigNumber... numbers) {
        int total  = numbers.length;

        BigNumber result = new BigNumber(numbers[0]);

        for(int i=1; i<total; i++) {
            result = BigNumberUtils.addTwo(result, numbers[i]);
        }
        return result;
    }

    /**
     * @param firstNumber
     * @param secondNumber
     * @return
     */
    private static BigNumber addTwo(BigNumber firstNumber, BigNumber secondNumber) {
        return (new BigNumber(firstNumber.add(secondNumber))).consolidate();
    }

    /**
     * Same as sub()
     * @param firstNumber
     * @param secondNumber
     * @return A BigNumber type variable containing firstNumber - secondNumber
     * @author Nishi Inc.
     * @since v1.0.0
     */
    public static BigNumber subtract(BigNumber firstNumber, BigNumber secondNumber) {
        return (new BigNumber(firstNumber.subtract(secondNumber))).consolidate();
    }

    /**
     * @param numbers BigNumber numbers
     * @return A BigNumber number containing product of the numbers provided
     * @author Nishi Inc.
     * @since v1.1.0
     */
    public static BigNumber multiply(BigNumber... numbers) {
        BigNumber result = new BigNumber(numbers[0]);
        for(int i=1; i<numbers.length; i++) {
            result = BigNumberUtils.multiplyTwo(result, numbers[i]);
        }
        return result;
    }

    /**
     * @author Nishi Inc.
     * @since v1.1.0
     * @param firstNumber
     * @param secondNumber
     * @return
     */
    private static BigNumber multiplyTwo(BigNumber firstNumber, BigNumber secondNumber) {
        return (new BigNumber(firstNumber.multiply(secondNumber))).consolidate();
    }

    /**
     * @author Nishi Inc.
     * @since v1.0.0
     * @throws ArithmeticException When denominator is zero or when division is giving endless number
     * @param numerator or dividend
     * @param denominator or divisor
     * @return A BigNumber type variable containing result of arithmetic division of the provided numerator by the provided denominator
     */
    public static BigNumber divide(BigNumber numerator, BigNumber denominator) throws ArithmeticException {
        return new BigNumber(numerator.divide(denominator)).consolidate();
    }

    /**
     * Returns absolute value of the given number
     * @author Nishi Inc.
     * @since v1.1.0
     * @param number
     * @return A BigNumber type variable containing absolute value of the given BigNumber number
     */
    public static BigNumber absolute(BigNumber number) {
        return new BigNumber(number.absolute());
    }

    /*==========================================================
	 * 						Public Static Methods
	 * =========================================================
	 */

    /**
     * Returns remainder
     * @param numerator
     * @param denominator
     * @return A BigNumber type variable containing remainder of arithmetic division of the provided numerator by the provided denominator
     * @author Nishi Inc.
     * @since v1.1.0
     */
    public static BigNumber modulus(BigNumber numerator, BigNumber denominator) {
        return new BigNumber(numerator.divideAndRemainder(denominator)[1]).consolidate();
    }

    /**
     * Same as modulus()
     * @param numerator
     * @param denominator
     * @return A BigNumber type variable containing remainder of arithmetic division of the provided numerator by the provided denominator
     * @author Nishi Inc.
     * @since v1.1.0
     */
    public static BigNumber mod(BigNumber numerator, BigNumber denominator) {
        return BigNumberUtils.modulus(numerator, denominator);
    }

    /**
     * Same as add() method
     * @author Nishi Inc.
     * @since v1.1.0
     * @param numbers BigNumber numbers
     * @return A BigNumber type variable containing result of arithmetic addition of the provided numbers
     */
    public static BigNumber sum(BigNumber... numbers) {
        return BigNumberUtils.add(numbers);
    }

    /**
     * Same as subtract()
     * @author Nishi Inc.
     * @since v1.1.0
     * @param firstNumber
     * @param secondNumber
     * @return A BigNumber type variable containing firstNumber - secondNumber
     */
    public static BigNumber sub(BigNumber firstNumber, BigNumber secondNumber) {
        return BigNumberUtils.subtract(firstNumber, secondNumber);
    }

    /**
     * Same as absolute()
     * @param number
     * @return A BigNumber type variable containing absolute value of the given BigNumber number
     * @author Nishi Inc.
     * @since v1.1.0
     */
    public static BigNumber abs(BigNumber number) {
        return BigNumberUtils.absolute(number);
    }

    /**
     * @param number
     * @param power
     * @return A BigNumber containing number^power
     * @since v1.0.0
     * @author Nishi Incorporation
     */
    public static BigNumber pow(BigNumber number, int power) {
        return new BigNumber(number.pow(power)).consolidate();
    }

    /**
     * Slow performance
     * @since v1.1.0
     * @param number A BigNumber
     * @return A BigNumber containing value of factorial(given number)
     */
    public static BigNumber factorialOf(BigNumber number) {
        final BigNumber unit = new BigNumber(1);

        if(number.equals(unit) || number.equals(new BigNumber(0))) {
            return unit;
        }

        BigNumber result	= new BigNumber(number);
        BigNumber i			= new BigNumber();

        for(i.setValue(BigNumberUtils.sub(number, unit)); !i.isZero(); i.setValue(BigNumberUtils.sub(i, unit))) {
            result = result.multiply(i);
        }

        return result;
    }



    //=============================== Utility Methods ==========================================

    /**
     * Works similar to BigNumber.<i>reverse()</i>
     * @param number A BigNumber number
     * @return A BigNumber number with its digits in reverse order
     * @author Nishi Inc.
     * @since v1.2.0
     */
    public static BigNumber reverse(BigNumber number) {
        BigNumber result = new BigNumber(number);
        result.reverse();
        return result;
    }

    /**
     * Works similar to BigNumber.<i>reverse()</i>
     * @param numbers
     * @return An array of BigNumber numbers containing BigNumber numbers which have their digits in reverse order
     * @author Nishi Inc.
     * @since v1.0.0
     */
    public static BigNumber[] reverse(BigNumber... numbers) {
        int size = numbers.length;
        BigNumber[] result = new BigNumber[size];
        for(int i=0; i<size; i++) {
            result[i] = reverse(numbers[i]);
        }
        return result;
    }

    /**
     * @param numbers
     * @return A BigNumber number having secondNumber concatenated to firstNumber
     * @author Nishi Inc.
     * @since v1.0.0
     */
    public static BigNumber append(BigNumber... numbers) {
        BigNumber result = new BigNumber(numbers[0]);
        int length 		 = numbers.length;
        for(int i=1; i<length; i++) {
            result.append(numbers[i]);
        }
        return result;
    }

    //==========================================================================================

}
