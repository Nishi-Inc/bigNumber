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

package org.common.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.common.services.exceptions.IncompatibleCharacterException;
import org.common.services.exceptions.UnconcatenableException;

/**
 * This class makes Big type number object
 * @author <a href="mailto:shuklaalok7@gmail.com">Alok Shukla</a>
 * @since August 7, 2013
 */
public class Big implements Serializable, Comparable<Big> {

	private static final long serialVersionUID = 1L;
	
	private List<Character>		value;
	private boolean				isNegative;
	private boolean				isFraction;
	
	public Big(){
	}
	
	public Big(Big number) throws IncompatibleCharacterException {
		this.setValue(number);
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
			if(digit == '.')
				this.setFraction(true);
			if(digit > 9 || digit < 0 || digit != '-' || digit != '.')
				throw new IncompatibleCharacterException();
		}
		this.value = value;
		this.consolidate();
		// Set other variables according to this new value
		if(this.getValue().get(0) == '-')
			this.setNegative(true);
	}

	public boolean isNegative() {
		return isNegative;
	}

	private void setNegative(boolean isNegative) {
		this.isNegative = isNegative;
	}

	public boolean isFraction() {
		return isFraction;
	}

	public void setFraction(boolean isFraction) {
		this.isFraction = isFraction;
	}

}
