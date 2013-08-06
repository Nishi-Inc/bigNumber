package org.bigNumber;

import org.common.models.Big;
import org.common.services.exceptions.UnconcatenableException;

public class Utilities {
	
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

}
