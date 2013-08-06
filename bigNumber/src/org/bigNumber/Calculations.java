package org.bigNumber;

import org.common.models.Big;
import org.common.services.exceptions.IncompatibleCharacterException;

/**
 * This class provides methods for arithmetic calculations on big numbers
 * @author <a href="mailto:shuklaalok7@gmail.com">Alok Shukla</a>
 * @since August 6, 2013
 * @Producer Nishi Inc.
 */
public class Calculations {
	
	/**
	 * @param numbers Big numbers
	 * @return A Big number containing product of the numbers provided
	 */
	public Big multiply(Big... numbers) {
		// TODO Write logic
		int size   = numbers.length;
		Big result = new Big();
		return result;
	}
	
	/**
	 * 
	 * @param numerator or dividend
	 * @param denominator or divisor
	 * @return A Big type variable containing result of arithmetic division of the provided numerator by the provided denominator
	 */
	public Big divide(Big numerator, Big denominator) {
		// TODO Write logic
		Big result	=	new Big();
		return result;
	}
	
	/**
	 * Same as mod()
	 * @param numerator
	 * @param denominator
	 * @return A Big type variable containing remainder of arithmetic division of the provided numerator by the provided denominator
	 */
	public Big modulus(Big numerator, Big denominator) {
		// TODO Write logic
		Big result	=	new Big();
		return result;
	}
	
	/**
	 * Same as modulus()
	 * @param numerator
	 * @param denominator
	 * @return A Big type variable containing remainder of arithmetic division of the provided numerator by the provided denominator
	 */
	public Big mod(Big numerator, Big denominator) {
		return modulus(numerator, denominator);
	}
	
	/**
	 * Same as sum()
	 * @param numbers Big numbers
	 * @return A Big type variable containing result of arithmetic addition of the provided numbers
	 */
	public Big add(Big... numbers) {
		// TODO Write logic
		Big result	=	new Big();
		return result;
	}
	
	/**
	 * Same as add() method
	 * @param numbers Big numbers
	 * @return A Big type variable containing result of arithmetic addition of the provided numbers
	 */
	public Big sum(Big... numbers) {
		return add(numbers);
	}
	
	/**
	 * Same as sub()
	 * @param firstNumber
	 * @param secondNumber
	 * @return A Big type variable containing firstNumber - secondNumber
	 */
	public Big subtract(Big firstNumber, Big secondNumber) {
		Big result	=	new Big();
		int compare =	firstNumber.compareTo(secondNumber);
		if(compare == 0) {
			try {
				result.put(0);
			} catch (IncompatibleCharacterException e) {
				e.printStackTrace();
			}
		}
		else if(compare == 1) {
			if(firstNumber.isNegative() && secondNumber.isNegative())
				result = subtract(absolute(secondNumber), absolute(firstNumber));
			else if(!firstNumber.isNegative() && secondNumber.isNegative())
				result = add(absolute(secondNumber), firstNumber);
			else {
				// TODO Both numbers are positive and first number is greater
				int sizeOfFirstNumber  = firstNumber.getValue().size();
				int sizeOfSecondNumber = secondNumber.getValue().size();
				for(int i=0; i<sizeOfSecondNumber; i++) {
					
				}
			}
		} else {
			if(secondNumber.isNegative() && firstNumber.isNegative())
				result = subtract(absolute(firstNumber), absolute(secondNumber));
			else if(!secondNumber.isNegative() && firstNumber.isNegative())
				result = add(absolute(firstNumber),secondNumber);
			else
				result = subtract(secondNumber, firstNumber);
		}
		return result;
	}
	
	/**
	 * Same as subtract()
	 * @param firstNumber
	 * @param secondNumber
	 * @return A Big type variable containing firstNumber - secondNumber
	 */
	public Big sub(Big firstNumber, Big secondNumber) {
		return subtract(firstNumber, secondNumber);
	}
	
	/**
	 * Same as abs()
	 * @param number
	 * @return A Big type variable containing absolute value of the given Big number
	 */
	public Big absolute(Big number) {
		if(!number.isNegative())
			return number;
		Big result = null;
		try {
			result = new Big(number);
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}
		result.getValue().remove(0);
		return result;
	}
	
	/**
	 * Same as absolute()
	 * @param number
	 * @return A Big type variable containing absolute value of the given Big number
	 */
	public Big abs(Big number) {
		return absolute(number);
	}

}
