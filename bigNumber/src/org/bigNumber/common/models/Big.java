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

package org.bigNumber.common.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bigNumber.common.services.exceptions.IncompatibleCharacterException;
import org.bigNumber.common.services.exceptions.UnconcatenableException;

/**
 * This class makes Big type number object
 * @author <a href="mailto:shuklaalok7@gmail.com">Alok Shukla</a>
 * @since August 7, 2013
 */
public final class Big implements Serializable, Comparable<Big> {

	private static final long serialVersionUID = 1L;
	
	private List<Character>		value;
	private boolean				isNegative			=	false;
	private boolean				isFraction			=	false;
	private Integer				locationOfDecimal;
	
	public Big(){
	}
	
	public Big(Big number) throws IncompatibleCharacterException {
		this.setValue(number);
	}
	
	public void roundOf() {
		
	}
	
	/**
	 * It doesn't return an Integer.<br/> It discards the fractional part and keeps the whole number.
	 */
	public void convertToInteger() {
		if(!this.isFraction)
			return;
		else {
			for(int i=this.locationOfDecimal(); i<this.getValue().size(); i++)
				this.getValue().remove(i);
			this.setFraction(false);
		}
	}
	
	/**
	 * Converts a number to negative
	 */
	public void convertToNegative() {
		try {
			this.putAtFirst('-');
		} catch (IncompatibleCharacterException | UnconcatenableException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Puts the given digit at the beginning of the given number
	 * @param digit An int
	 * @throws IncompatibleCharacterException
	 * @throws UnconcatenableException
	 */
	public void putAtFirst(int digit) throws IncompatibleCharacterException, UnconcatenableException {
		Big result	=	new Big();
		result.put(digit);
		result.concat(this);
		this.setValue(result);
	}
	
	/**
	 * Puts the given character at the beginning of the given number
	 * @param character A char
	 * @throws IncompatibleCharacterException
	 * @throws UnconcatenableException
	 */
	public void putAtFirst(char character) throws IncompatibleCharacterException, UnconcatenableException {
		if(character != '.' || character != '-')
			throw new IncompatibleCharacterException();
		if(character == '.' && this.isFraction())
			throw new IncompatibleCharacterException();
		if(character == '-' && this.isNegative())
			return;
		Big result	=	new Big();
		result.put(character);
		result.concat(this);
	}
	
	/**
	 * Reverses the Big number
	 */
	public void reverse() {
		// TODO Write Logic
	}
	
	/**
	 * Removes leading zeroes as well as trailing zeroes in case of fraction
	 */
	public void consolidate() {
		// TODO Write Logic
		// Remember -ve and fractions
	}
	
	/**
	 * Concats the provided number at the end of the calling number
	 * @param number
	 * @throws UnconcatenableException 
	 */
	public void concat(Big number) throws UnconcatenableException {
		if(this.isFraction())
			throw new UnconcatenableException("From Big.concat(): First number should not be a fraction.");
		if(number.isNegative())
			throw new UnconcatenableException("From Big.concat(): Second number should not be negative.");
		this.getValue().addAll(number.getValue());
	}
	
	/**
	 * It puts the provided number in the Big variable
	 * @param number
	 * Big number
	 * @throws IncompatibleCharacterException
	 */
	public void setValue(Big number) throws IncompatibleCharacterException {
		this.setValue(number.getValue());
	}
	
	/**
	 * It puts the provided number in the Big variable
	 * @param number
	 * As String, int, float, double, long etc.
	 * @throws IncompatibleCharacterException
	 */
	public <E extends Comparable<E>> void setValue(E number) throws IncompatibleCharacterException {
		this.put(number);
	}
	
	/**
	 * It puts the provided number in the Big variable
	 * @param number As String which may contain only numbers 0 to 9, a leading -ve sign '-' and a decimal point  
	 * @throws IncompatibleCharacterException
	 */
	public void setValue(String number) throws IncompatibleCharacterException {
		List<Character> chars	=	new ArrayList<Character>();
		for(int i=0; i<number.length(); i++)
			chars.add(number.charAt(i));
		this.setValue(chars);
	}
	
	/**
	 * It puts the provided number in the Big variable
	 * @param number
	 * As String, int, float, double, long etc.
	 * @throws IncompatibleCharacterException
	 */
	public <E extends Comparable<E>> void put(E number) throws IncompatibleCharacterException {
		ArrayList<Character> numberList	=	new ArrayList<Character>();
		String 				 num 		=	number.toString();
		int					 size 		=	num.length();
		for(int i=0; i<size; i++)
			numberList.add(num.charAt(i));
		this.setValue(numberList);
	}
	
	/**
	 * @return 0 if equal, +1 if greater and -1 if less
	 */
	@Override
	public int compareTo(Big number) {
		// TODO Incorporate cases of fractional numbers
		int result = 0;
		int lengthOfFirstNumber  = this.getValue().size();
		int lengthOfSecondNumber = number.getValue().size();
		
		if(this.isNegative && !number.isNegative)
			return -1;
		if(!this.isNegative && number.isNegative)
			return 1;
		
		// Now either both are positive or both are negative
		boolean reverse = false;
		if(number.getValue().get(0) == '-')
			reverse = true;
		
		if(lengthOfFirstNumber > lengthOfSecondNumber)
			return 1;
		else if(lengthOfFirstNumber < lengthOfSecondNumber)
			return -1;
		else {
			// Now both of them are of same sign and of same length
			int length = lengthOfFirstNumber;
			int i=0;
			if(reverse)
				i=1;
			for(; i<=length; i++) {
				if(this.getValue().get(i) < number.getValue().get(i)) {
					if(reverse)
						return 1;
					else
						return -1;
				}
				else if(this.getValue().get(i) > number.getValue().get(i)) {
					if(reverse)
						return -1;
					else
						return 1;
				}
			}
		}
		return result;
	}

	/**
	 * @return A List of characters containing value of the Big number
	 */
	public List<Character> getValue() {
		if(value == null)
			value	=	new ArrayList<Character>();
		return value;
	}

	private void setValue(List<Character> value) throws IncompatibleCharacterException {
		int size = value.size();
		for(int i=0; i<size; i++) {
			Character digit = value.get(i);
			if(digit == null)
				break;
			if(digit == '.') {
				if(this.isFraction())
					throw new IncompatibleCharacterException();
				this.setFraction(true);
				this.setLocationOfDecimal(i);
			}
			if(digit > 9 || digit < 0 || digit != '-' || digit != '.')
				throw new IncompatibleCharacterException();
		}
		this.value = value;
		//this.consolidate();
		// Set other variables according to this new value
		if(this.getValue().get(0) == '-')
			this.setNegative(true);
	}

	/**
	 * 
	 * @return true if the number is negative else false
	 */
	public boolean isNegative() {
		return isNegative;
	}
	
	private void setNegative(boolean isNegative) {
		this.isNegative = isNegative;
	}
	
	/**
	 * 
	 * @return true if the number is a fraction else false
	 */
	public boolean isFraction() {
		return isFraction;
	}

	private void setFraction(boolean isFraction) {
		this.isFraction = isFraction;
		if(!isFraction)
			this.setLocationOfDecimal(-1);
	}

	/**
	 * 
	 * @return position of decimal point in the fractional number,
	 * -1 if it's not a fractional number
	 */
	public int locationOfDecimal() {
		if(locationOfDecimal == null)
			return -1;
		return locationOfDecimal;
	}

	private void setLocationOfDecimal(Integer locationOfDecimal) {
		this.locationOfDecimal = locationOfDecimal;
	}

}