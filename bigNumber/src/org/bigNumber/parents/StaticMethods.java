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


package org.bigNumber.parents;

import org.bigNumber.BigNumber;
import org.bigNumber.common.services.Constants;

/**
 * Superclass for BigNumber
 * @author Nishi Inc.
 * @since v1.1.0
 */
public abstract class StaticMethods extends Methods {

	private static final long serialVersionUID = 1L;

	@Override
	public abstract int compareTo(BigNumber number);
	
	
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

		for(i.setValue(sub(number, Constants.UNITY)); !i.isZero(); i.setValue(sub(number, Constants.UNITY))) {
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

}
