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

package org.bigNumber;

import org.bigNumber.common.models.Big;
import org.bigNumber.common.services.exceptions.IncompatibleCharacterException;

/**
 * This class provides methods for arithmetic calculations on big numbers
 * @author <a href="mailto:shuklaalok7@gmail.com">Alok Shukla</a>
 * @since v0.1.0
 * @Producer Nishi Inc.
 */
public final class Calculations {

	/**
	 * Private constructor to disallow objectification
	 */
	private Calculations() { }

	/**
	 * @param numbers Big numbers
	 * @return A Big number containing product of the numbers provided
	 */
	public Big multiply(Big... numbers) {
		int total  = numbers.length;
		if(total < 1)
			return null;
		Big result = new Big();
		try {
			result.setValue(1);
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}
		for(int i=0; i<total; i++)
			result = multiplyTwo(result, numbers[i]);
		return result;
	}
	
	private Big multiplyTwo(Big firstNumber, Big secondNumber) {
		// TODO Write logic for multiplying two numbers
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
		int total  = numbers.length;
		if(total < 1)
			return null;
		Big result = new Big();
		try {
			result.setValue(0);
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}
		for(int i=0; i<total; i++)
			result = addTwo(result, numbers[i]);
		return result;
	}
	
	private Big addTwo(Big firstNumber, Big secondNumber) {
		Big result = new Big();
		
		if(firstNumber.isNegative() && secondNumber.isNegative()) {
			try {
				result.setValue(add(absolute(firstNumber), absolute(secondNumber)));
			} catch (IncompatibleCharacterException e) {
				e.printStackTrace();
			}
			result.convertToNegative();
		} else if(!firstNumber.isNegative() && secondNumber.isNegative()) {
			try {
				result.setValue(subtract(firstNumber, secondNumber));
			} catch (IncompatibleCharacterException e) {
				e.printStackTrace();
			}
		} else if(firstNumber.isNegative() && !secondNumber.isNegative()) {
			try {
				result.setValue(subtract(secondNumber, firstNumber));
			} catch (IncompatibleCharacterException e) {
				e.printStackTrace();
			}
		} else {
			if(!firstNumber.isFractional() && !secondNumber.isFractional()) {
				// TODO Both are whole numbers
			} else if(firstNumber.isFractional() && !secondNumber.isFractional()) {
				// firstNumber is fractional
				for(int i=firstNumber.locationOfDecimal(); i<firstNumber.size(); i++){
					try {
						result.putAtFirst(firstNumber.getValue().get(i));
					} catch (IncompatibleCharacterException e) {
						e.showMsg();
					}
				}
				firstNumber.convertToInteger();
				Big dummy = new Big();
				dummy     = addTwo(firstNumber, secondNumber);
				for(int i=0; i<dummy.size(); i++) {
					try {
						result.putAtFirst(dummy.getValue().get(i));
					} catch (IncompatibleCharacterException e) {
						e.showMsg();
					}
				}
			} else if(!firstNumber.isFractional() && secondNumber.isFractional()) {
				// secondNumber is fractional
				for(int i=secondNumber.locationOfDecimal(); i<secondNumber.size(); i++){
					try {
						result.putAtFirst(secondNumber.getValue().get(i));
					} catch (IncompatibleCharacterException e) {
						e.showMsg();
					}
				}
				secondNumber.convertToInteger();
				Big dummy = new Big();
				dummy     = addTwo(firstNumber, secondNumber);
				for(int i=0; i<dummy.size(); i++) {
					try {
						result.putAtFirst(dummy.getValue().get(i));
					} catch (IncompatibleCharacterException e) {
						e.showMsg();
					}
				}
			} else {
				// Both are fractional
				int dADIFN = firstNumber.size() - firstNumber.locationOfDecimal() + 1;		// digitsAfterDecimalInFirstNumber
				int dADISN = secondNumber.size() - secondNumber.locationOfDecimal() + 1;	// digitsAfterDecimalInSecondNumber
				int digitsAfterDecimal = dADIFN;
				if(dADIFN < dADISN)
					digitsAfterDecimal = dADISN;
				try {
					for(;(firstNumber.size() - firstNumber.locationOfDecimal()) <= digitsAfterDecimal ; firstNumber.concat(new Big()));
					for(;(secondNumber.size() - secondNumber.locationOfDecimal()) <= digitsAfterDecimal ; secondNumber.concat(new Big()));
				} catch (IncompatibleCharacterException e) {
					e.showMsg();
				}
				
			}
		}
		
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
	 * @author Alok Shukla
	 * @since v0.1.0
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
				firstNumber.consolidate();
				secondNumber.consolidate();
				int sizeOfFirstNumber  = firstNumber.size();
				int sizeOfSecondNumber = secondNumber.size();

				if(firstNumber.isFractional() && secondNumber.isFractional()) {
					StringBuilder zeroes	=	new StringBuilder();
					for(int dif	=	firstNumber.locationOfDecimal() - secondNumber.locationOfDecimal(); dif > 0; dif--) {
						try {
							secondNumber.putAtFirst(0);
						} catch (IncompatibleCharacterException e) {
							e.showMsg();
						}
					}
				} else if(firstNumber.isFractional() && !secondNumber.isFractional()) {
					try {
						for(int i=firstNumber.locationOfDecimal(); i<sizeOfFirstNumber; i++)
							result.putAtFirst(firstNumber.getValue().get(i));
					} catch (IncompatibleCharacterException e) {
						e.showMsg();
					}
				} else if(!firstNumber.isFractional() && secondNumber.isFractional()) {

				} else {
					// TODO both are not fractional
				}

				for(int i=0; i<sizeOfSecondNumber; i++) {
					int carry = 0;

				}
			}
		} else {
			if(secondNumber.isNegative() && firstNumber.isNegative())
				result = subtract(absolute(firstNumber), absolute(secondNumber));
			else if(!secondNumber.isNegative() && firstNumber.isNegative())
				result = add(absolute(firstNumber),secondNumber);
			else
				result = subtract(secondNumber, firstNumber);
			result.convertToNegative();
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
