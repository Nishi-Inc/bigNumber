package org.bigNumber;

import org.bigNumber.common.models.Big;
import org.bigNumber.common.services.exceptions.IncompatibleCharacterException;
import org.bigNumber.common.services.exceptions.UnconcatenableException;

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
	 * @throws UnconcatenableException 
	 */
	public Big concat(Big firstNumber, Big secondNumber) throws UnconcatenableException {
		firstNumber.concat(secondNumber);
		return firstNumber;
	}
	
	/**
	 * Works similar to Big.<i>concat(</i>Big number<i>)</i>
	 * @param firstNumber
	 * @param secondNumber
	 * @return A Big number having secondNumber concatenated to firstNumber
	 * @throws UnconcatenableException 
	 */
	public Big concat(Big... numbers) throws UnconcatenableException {
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
	 * @throws UnconcatenableException
	 */
	public Big putAtFirst(Big number, int digit) throws IncompatibleCharacterException, UnconcatenableException {
		number.putAtFirst(digit);
		return number;
	}
	
	/**
	 * Puts the given character at the beginning of the given number
	 * @param number A Big number
	 * @param character A char
	 * @return A Big number containing given Big number appended to the given character
	 * @throws IncompatibleCharacterException
	 * @throws UnconcatenableException
	 */
	public Big putAtFirst(Big number, char character) throws IncompatibleCharacterException, UnconcatenableException {
		number.putAtFirst(character);
		return number;
	}

}
