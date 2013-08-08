package org.bigNumber;

import org.bigNumber.common.models.Big;
import org.bigNumber.common.services.exceptions.IncompatibleCharacterException;

public final class Utilities {
	
	/**
	 * Private constructor to disallow objectification
	 */
	private Utilities() {}
	
	/**
	 * Works similar to Big.<i>reverse()</i>
	 * @param number A Big number
	 * @return A Big number with its digits in reverse order 
	 */
	public Big reverse(Big number) {
		number.reverse();
		return number;
	}
	
	/**
	 * Works similar to Big.<i>reverse()</i>
	 * @param numbers
	 * @return An array of Big numbers containing Big numbers in order 
	 */
	public Big[] reverse(Big... numbers) {
		for(Big number : numbers) {
			number.reverse();
		}
		return numbers;
	}
	
	/**
	 * Works similar to Big.<i>consolidate()</i>
	 * @param number
	 * @return Removes leading zeroes and trailing zeroes as well in case of a fraction
	 */
	public Big consolidate(Big number) {
		number.consolidate();
		return number;
	}
	
	/**
	 * Works similar to Big.<i>consolidate()</i>
	 * @param numbers
	 * @return Removes leading zeroes and trailing zeroes as well in case of a fraction
	 */
	public Big[] consolidate(Big... numbers) {
		for(Big number : numbers) {
			number.consolidate();
		}
		return numbers;
	}
	
	/**
	 * Works similar to Big.<i>concat(</i>Big number<i>)</i>
	 * @param firstNumber
	 * @param secondNumber
	 * @return A Big number having secondNumber concatenated to firstNumber
	 * @throws IncompatibleCharacterException
	 * @author Alok Shukla
	 * @since v0.1.0
	 * @deprecated
	 */
	public Big concat(Big firstNumber, Big secondNumber) throws IncompatibleCharacterException {
		firstNumber.concat(secondNumber);
		return firstNumber;
	}
	
	/**
	 * Works similar to Big.<i>concat(</i>Big number<i>)</i>
	 * @param firstNumber
	 * @param secondNumber
	 * @return A Big number having secondNumber concatenated to firstNumber
	 * @throws IncompatibleCharacterException
	 * @author Alok Shukla
	 * @since v0.1.0
	 * @deprecated
	 */
	public Big concat(Big... numbers) throws IncompatibleCharacterException {
		int length = numbers.length;
		for(int i=1; i<length; i++)
			numbers[0].concat(numbers[i]);
		return numbers[0];
	}
	
	/**
	 * Puts the given digit at the beginning of the given number
	 * @param number A Big number
	 * @param digit An int
	 * @return A Big number containing given Big number appended to the given digit
	 * @throws IncompatibleCharacterException
	 */
	public Big putAtFirst(Big number, int digit) throws IncompatibleCharacterException {
		number.putAtFirst(digit);
		return number;
	}
	
	/**
	 * Puts the given character at the beginning of the given number
	 * @param number A Big number
	 * @param character A char
	 * @return A Big number containing given Big number appended to the given character
	 * @throws IncompatibleCharacterException
	 */
	public Big putAtFirst(Big number, char character) throws IncompatibleCharacterException{
		number.putAtFirst(character);
		return number;
	}

}
