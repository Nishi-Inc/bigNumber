/*
 * ========================= DECLARATION ===============================
 * NOTE: THIS IS NOT TO BE REMOVED IN ANY MODIFICATION & DISTRIBUTION
 * OF THIS CLASS.
 * 
 * Author: Nishi Inc.
 * Conceptualized by: Alok Shukla
 * Made available for free by: Nishi Inc. 
 * =====================================================================
 */

package org.bigNumber.models;

import java.math.BigDecimal;
import java.util.List;

import org.bigNumber.common.services.GlobalConstants;
import org.bigNumber.common.interfaces.MathematicalMethods;
import org.bigNumber.common.interfaces.UtilityMethods;

/**
 * Parent class for BigNumber
 * @author Nishi Inc.
 * @since v1.1.0
 */
public abstract class Root implements MathematicalMethods, UtilityMethods {

	protected static final long serialVersionUID = 1L;

    protected BigDecimal bigDecimal;

    protected BigDecimal getBigDecimal() {
        if(bigDecimal == null) {
            this.setBigDecimal(new BigDecimal(0));
        }
        return bigDecimal;
    }

    protected void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
        this.syncFromDecimal();
    }


    /**
     * @return 0 if equal, +1 if greater and -1 if less
     * @author Nishi Inc.
     * @since v1.1.0
     */
    @Override
    public abstract int compareTo(BigNumber number);
    @Override
    public abstract int hashCode();
    @Override
    public abstract boolean equals(Object number);

    /**
     * Returns a plain String containing value of the BigNumber Number
     * @author Nishi Inc.
     * @since v0.1.0
     */
    @Override
    public abstract String toString();

    /**
     * Sets all the field variables as per the value of bigNumber.
     */
    protected abstract void syncFromValue();

    /**
     * Sets all the field variables as per the value of bigInteger
     */
    protected abstract void syncFromInteger();

    /**
     * Sets all the field variables as per the value of bigDecimal
     */
    protected abstract void syncFromDecimal();

    /**
     * It puts the provided number in the BigNumber variable
     * toString() method on the provided number will be called hence do not use primitive types
     * @param number
     * As String, Integer, Float, Double, Long etc.
     * @author Nishi Inc.
     * @since August 6, 2013, v0.1.0
     */
    public abstract <E extends Comparable<E>> void setValue(E number);

    /**
     * It puts the provided number in the BigNumber variable
     * @param number
     * BigNumber number
     * @author Nishi Inc.
     * @since v0.1.0
     */
    public abstract void setValue(BigNumber number);

    /**
     * It puts the provided number in the BigNumber variable
     * @param number As String which may contain only numbers 0 to 9, a leading -ve sign '-' and a decimal point
     * @author Nishi Inc.
     * @since August 6, 2013, v0.1.0
     */
    public abstract void setValue(List<Character> number);

    /**
     * <ul><li>No character accept '0' to '9', '.' and '-' is allowed.</li>
     * <li>No two '.' are allowed.</li>
     * <li>Spaces (' ' or " ") in the provided String are removed before setting the value so no problem with spaces.</li>
     * <li>'-' is allowed only as first character</li></ul>
     * @param value
     */
    public abstract void setValue(String value);

    /**
     * @return position of decimal point in the fractional number,
     * -1 if it's not a fractional number
     * @author Nishi Inc.
     * @since v0.1.0
     */
    public abstract int locationOfDecimal();

    /**
     *
     * @return true if the number is a fraction else false
     * @author Nishi Inc.
     * @since v0.1.0
     */
    public abstract boolean isFractional();

    /**
     * @return true if the number is negative else false
     * @author Nishi Inc.
     * @since v0.1.0
     */
    public abstract boolean isNegative();

    /**
     * @return true if the number has value = 0 or "0" else false
     * @author Nishi Inc.
     * @since v0.1.0
     */
    public abstract boolean isZero();

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
        BigNumber result	=	new BigNumber();
        result.setValue(numerator.divideAndRemainder(denominator)[1]);
        result.absolute();
        return result;
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
        return modulus(numerator, denominator);
    }

    /**
     * Same as add() method
     * @author Nishi Inc.
     * @since v1.1.0
     * @param numbers BigNumber numbers
     * @return A BigNumber type variable containing result of arithmetic addition of the provided numbers
     */
    public static BigNumber sum(BigNumber... numbers) {
        return BigNumber.add(numbers);
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
        return BigNumber.subtract(firstNumber, secondNumber);
    }

    /**
     * Same as absolute()
     * @param number
     * @return A BigNumber type variable containing absolute value of the given BigNumber number
     * @author Nishi Inc.
     * @since v1.1.0
     */
    public static BigNumber abs(BigNumber number) {
        return BigNumber.absolute(number);
    }

    /**
     * @param number
     * @param power
     * @return A BigNumber containing number^power
     * @since v1.0.0
     * @author Nishi Incorporation
     */
    public static BigNumber pow(BigNumber number, int power) {
        BigNumber result = new BigNumber(number);
        result.pow(power);
        return result;
    }

    /**
     * @author Nishi Incorporation
     * @since v1.1.0
     * @param number
     * @return A BigNumber containing value of factorial(given number)
     */
    public static BigNumber factorialOf(BigNumber number) {
        BigNumber result	= new BigNumber(number);
        BigNumber i			= new BigNumber();

        for(i.setValue(sub(number, GlobalConstants.UNITY)); !i.isZero(); i.setValue(sub(i, GlobalConstants.UNITY))) {
            result.multiply(i);
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
            result = addTwo(result, numbers[i]);
        }
        return result;
    }

    /**
     * @param firstNumber
     * @param secondNumber
     * @return
     */
    private static BigNumber addTwo(BigNumber firstNumber, BigNumber secondNumber) {
        BigNumber result = new BigNumber();

        if(!firstNumber.isFractional() && !secondNumber.isFractional()) {
            //Both are non-fractional
            result.setBigInteger(firstNumber.getBigInteger().add(secondNumber.getBigInteger()));
        } else {
            firstNumber.makeFractional();
            secondNumber.makeFractional();
            result.setBigDecimal(firstNumber.getBigDecimal().add(secondNumber.getBigDecimal()));
        }

        return result;
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
        BigNumber result = new BigNumber();

        if(firstNumber.isFractional() && secondNumber.isFractional()) {
            //Both are non-fractional
            result.setBigInteger(firstNumber.getBigInteger().subtract(secondNumber.getBigInteger()));
        } else {
            firstNumber.makeFractional();
            secondNumber.makeFractional();
            result.setBigDecimal(firstNumber.getBigDecimal().subtract(secondNumber.getBigDecimal()));
        }

        result.consolidate();
        return result;
    }

    /**
     * @param numbers BigNumber numbers
     * @return A BigNumber number containing product of the numbers provided
     * @author Nishi Inc.
     * @since v1.1.0
     */
    public static BigNumber multiply(BigNumber... numbers) {
        int total  = numbers.length;
        BigNumber result = new BigNumber(numbers[0]);

        for(int i=1; i<total; i++) {
            result = Root.multiplyTwo(result, numbers[i]);
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
        BigNumber result = new BigNumber();

        if(!firstNumber.isFractional() && !secondNumber.isFractional()) {
            // Both are non-fractional
            result.setBigInteger(firstNumber.getBigInteger().multiply(secondNumber.getBigInteger()));
        } else {
            firstNumber.makeFractional();
            secondNumber.makeFractional();
            result.setBigDecimal(firstNumber.getBigDecimal().multiply(secondNumber.getBigDecimal()));
        }

        result.consolidate();
        return result;
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
        if(denominator.isZero()) {
            throw new ArithmeticException();
        }

        BigNumber result	=	new BigNumber();
        numerator.makeFractional();
        denominator.makeFractional();
        try {
            result.setBigDecimal(numerator.getBigDecimal().divide(denominator.getBigDecimal()));
        } catch(ArithmeticException e) {
            numerator.consolidate();
            denominator.consolidate();
            if(!numerator.isFractional() && !denominator.isFractional()) {
                numerator.getBigInteger().divide(denominator.getBigInteger());
            } else {
                throw e;
            }
        }

        numerator.consolidate();
        denominator.consolidate();
        return result;
    }

    /**
     * Returns absolute value of the given number
     * @author Nishi Inc.
     * @since v1.1.0
     * @param number
     * @return A BigNumber type variable containing absolute value of the given BigNumber number
     */
    public static BigNumber absolute(BigNumber number) {
        if(!number.isNegative()) {
            return number;
        }
        BigNumber result = new BigNumber(number);
        result.getValue().remove(0);
        result.syncFromValue();
        return result;
    }

}
