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
import org.bigNumber.common.services.Constants;
import org.bigNumber.common.services.exceptions.IncompatibleCharacterException;

/**
 * This class makes Big type number object
 * @author <a href="mailto:shuklaalok7@gmail.com">Alok Shukla</a>
 * @since August 7, 2013
 */
public final class Big implements Serializable, Comparable<Big> {

	private static final long serialVersionUID = 1L;
	
	private List<Character>		value;
	private boolean				isNegative			=	false;
	private boolean				isFractional		=	false;
	private Integer				locationOfDecimal;
	private	Integer				size;
	
	/**
	 * Constructs a Big type number with default value 0
	 */
	public Big(){
		try {
			this.setValue(0);
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}
	}
	
	public Big(Big number) throws IncompatibleCharacterException {
		this.setValue(number);
	}
	
	/**
	 * Rounds off a number to the number of digits specified by Constants.DEFAULT_ROUND_OFF_DIGITS
	 * @author Alok Shukla
	 * @since August 8, 2013
	 */
	public void roundOff() {
		this.roundOff(Constants.DEFAULT_ROUND_OFF_DIGITS);
	}
	
	/**
	 * Rounds off a number to the given number of digits
	 * @author Alok Shukla
	 * @param numberOfDigitsAfterDecimal
	 * @since August 8, 2013
	 */
	public void roundOff(Integer numberOfDigitsAfterDecimal) {
		if(!this.isFractional())
			return;
		int j = this.locationOfDecimal();
		int i = j+1;
		if((this.size()-i) < numberOfDigitsAfterDecimal)
			return;
		else {
			j = j + numberOfDigitsAfterDecimal;
			i = j+1;
			if((Integer.parseInt(this.getValue().get(i).toString()) < 5) || (Integer.parseInt(this.getValue().get(i).toString()) == 5 && Integer.parseInt(this.getValue().get(j).toString())%2 == 0))
				return;
			if(Integer.parseInt(this.getValue().get(i).toString()) > 5) {
				try {
					this.modify(j, Integer.parseInt(this.getValue().get(j).toString()) + 1);
				} catch (NumberFormatException | IncompatibleCharacterException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Changes the digit at the given index to the given digit
	 * @param index
	 * @param newDigit
	 * @author Alok Shukla
	 * @throws IncompatibleCharacterException 
	 * @since v0.1.0
	 */
	public void modify(int index, int newDigit) throws IncompatibleCharacterException {
		if(newDigit>9 || newDigit<0)
			throw new IncompatibleCharacterException("From Big.modify(): newDIgit is incompatible.");
		this.getValue().add(index, (char)(newDigit + '0'));
		this.getValue().remove(index+1);
	}
	
	/**
	 * Changes the digit at the given index to the given digit
	 * @param index
	 * @param newDigit
	 * @author Alok Shukla
	 * @throws IncompatibleCharacterException 
	 * @since v0.1.0
	 */
	public void modify(int index, char newDigit) throws IncompatibleCharacterException {
		boolean flag = false;
		if(newDigit == '.') {
			if(!this.isFractional())
				flag = true;
			else if(this.getValue().get(index) == '.')
				return;
			else
				throw new IncompatibleCharacterException("From Big.modify(): newDIgit is incompatible.");
		} else if(newDigit>'0' && newDigit<'9') {
			flag = true;
		} else
			throw new IncompatibleCharacterException("From Big.modify(): newDIgit is incompatible.");
		if(flag)
			this.modify(index, newDigit-'0');
	}
	
	/**
	 * It doesn't return an Integer.<br/> It discards the fractional part and keeps the whole number.
	 * @author Alok Shukla
	 * @since v0.1.0
	 */
	public void convertToInteger() {
		if(!this.isFractional())
			return;
		else {
			for(int i=this.locationOfDecimal(); i<this.size(); i++)
				this.getValue().remove(i);
			this.setFractional(false);
		}
	}
	
	/**
	 * Converts a number to negative
	 * @author Alok Shukla
	 * @since August 8, 2013
	 */
	public void convertToNegative() {
		try {
			this.putAtFirst('-');
		} catch (IncompatibleCharacterException e) {
			e.showMsg();
		}
	}
	
	/**
	 * Puts the given digit at the beginning of the given number
	 * @param digit An int
	 * @throws IncompatibleCharacterException
	 * @author Alok Shukla
	 * @since August 8, 2013
	 */
	public void putAtFirst(int digit) throws IncompatibleCharacterException {
		Big result	=	new Big();
		result.put(digit);
		result.concat(this);
		this.setValue(result);
	}
	
	/**
	 * Puts the given character at the beginning of the given number
	 * @param character A char
	 * @throws IncompatibleCharacterException
	 * @author Alok Shukla
	 * @since August 8, 2013
	 */
	public void putAtFirst(char character) throws IncompatibleCharacterException{
		if(character != '.' || character != '-')
			throw new IncompatibleCharacterException("From Big.putAtFirst(): character is incompatible.");
		if(character == '.' && this.isFractional())
			throw new IncompatibleCharacterException("From Big.putAtFirst(): character is incompatible.");
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
	 * @param number A Big number
	 * @throws IncompatibleCharacterException 
	 * @author Alok Shukla
	 * @since v0.1.0
	 * @deprecated Replaced by public void append(Big number)
	 */
	public void concat(Big number) throws IncompatibleCharacterException {
		if(this.isFractional())
			throw new IncompatibleCharacterException("From Big.concat(): First number should not be a fraction.");
		if(number.isNegative())
			throw new IncompatibleCharacterException("From Big.concat(): Second number should not be negative.");
		this.getValue().addAll(number.getValue());
	}
	
	public String toString() {
		StringBuilder result 	= new StringBuilder();
		List<Character> value	= this.getValue();
		if(value != null) {
			int size = this.size();
			for(int i=0; i<size; i++)
				result.append(value.get(i));
		}
		return result.toString();
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
	 * @author Alok Shukla
	 * @since August 6, 2013
	 */
	public <E extends Comparable<E>> void setValue(E number) throws IncompatibleCharacterException {
		this.put(number);
	}
	
	/**
	 * It puts the provided number in the Big variable
	 * @param number As String which may contain only numbers 0 to 9, a leading -ve sign '-' and a decimal point  
	 * @throws IncompatibleCharacterException
	 * @author Alok Shukla
	 * @since August 6, 2013
	 */
	public void setValue(String number) throws IncompatibleCharacterException {
		List<Character> chars	=	new ArrayList<Character>();
		for(int i=0; i<number.length(); i++)
			chars.add(number.charAt(i));
		this.setValue(chars);
	}
	
	/**
	 * Appends the given number to the calling number
	 * @param number
	 * @throws IncompatibleCharacterException
	 * @author Alok Shukla
	 * @since v0.1.0
	 */
	public void append(Big number) throws IncompatibleCharacterException {
		if(this.isFractional() && number.isFractional())
			throw new IncompatibleCharacterException("From Big.concat(): A number cannot have two decimal points.");
		if(number.isNegative())
			throw new IncompatibleCharacterException("From Big.concat(): Second number should not be negative.");
		this.getValue().addAll(number.getValue());
	}
	
	/**
	 * Appends the given positive number to the Big number
	 * @param number
	 * @throws IncompatibleCharacterException 
	 */
	public void append (int number) throws IncompatibleCharacterException {
		if(number < 0)
			throw new IncompatibleCharacterException("From Big.append(): number is incompatible.");
		ArrayList<Character> num = new ArrayList<Character>();
		for(; number>10; number /= 10)
			num.add(0, (char)(number%10 +'0'));
		this.getValue().addAll(num);
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
		int lengthOfFirstNumber  = this.size();
		int lengthOfSecondNumber = number.size();

		if(this.isNegative && !number.isNegative)
			return -1;
		if(!this.isNegative && number.isNegative)
			return 1;
		
		//Both the numbers are of same sign
		boolean reverse = false;
		if(number.getValue().get(0) == '-')
			reverse = true;

		Big tmp1	=	null;
		Big tmp2	=	null;
		Big frc1	=	null;
		Big frc2	=	null;

		if(this.isFractional() && number.isFractional()) {
			tmp1	=	new Big();
			tmp2	=	new Big();
			try {
				tmp1.setValue(this.getValueTill(this.locationOfDecimal()-1));
				tmp2.setValue(number.getValueTill(number.locationOfDecimal()-1));
				result = tmp1.compareTo(tmp2);
				if(result == 0) {
					frc1	=	new Big();
					frc2	=	new Big();
					frc1.setValue(this.getValueBetween(this.locationOfDecimal()+1, this.size()-1));
					frc2.setValue(number.getValueBetween(number.locationOfDecimal()+1, number.size()-1));
					result	=	frc1.compareTo(frc2);
				}
			} catch (IncompatibleCharacterException e) {
				e.printStackTrace();
			}
		} else if(this.isFractional() && !number.isFractional()) {
			tmp1	=	new Big();
			try {
				tmp1.setValue(this.getValueTill(this.locationOfDecimal()-1));
				result = tmp1.compareTo(number);
				if(result == 0)
					result	=	1;
			} catch (IncompatibleCharacterException e) {
				e.printStackTrace();
			}
		} else if(!this.isFractional() && number.isFractional()) {
			tmp2	=	new Big();
			try {
				tmp2.setValue(number.getValueTill(number.locationOfDecimal()-1));
				result = this.compareTo(tmp2);
				if(result == 0)
					result	=	-1;
			} catch (IncompatibleCharacterException e) {
				e.printStackTrace();
			}
		} else {
			// Both the numbers are non-fractional and of same sign
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
					if(this.getValue().get(i) < number.getValue().get(i))
							return -1;
					else if(this.getValue().get(i) > number.getValue().get(i))
							return 1;
				}
			}
		}
		if(reverse)
			result *= -1;
		return result;
	}
	
	/**
	 * 
	 * @param startIndex
	 * @param endIndex
	 * @return Value of the number as List of characters between (and including) the given indices
	 */
	public List<Character> getValueBetween(int startIndex, int endIndex) {
		if(startIndex > this.size() || endIndex > this.size())
			throw new ArrayIndexOutOfBoundsException();
		else if(startIndex > endIndex) {
			int tmp 	= 	endIndex;
			endIndex	=	startIndex;
			startIndex	=	tmp;
		}
		
		List<Character> result	=	new ArrayList<Character>();
		for(int i=startIndex; i<=endIndex; i++) {
			result.add(this.getValue().get(i));
		}
		return result;
	}
	
	/**
	 * 
	 * @param index
	 * @return Value of the number as List of characters till the given index
	 */
	public List<Character> getValueTill(int index) {
		return this.getValueBetween(0, index);
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
				if(this.isFractional())
					throw new IncompatibleCharacterException("From Big.setValue(): value is incompatible.");
				this.setFractional(true);
				this.setLocationOfDecimal(i);
			}
			if(digit > 9 || digit < 0 || digit != '-' || digit != '.')
				throw new IncompatibleCharacterException("From Big.setValue(): value is incompatible.");
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
	public boolean isFractional() {
		return isFractional;
	}

	private void setFractional(boolean isFraction) {
		this.isFractional = isFraction;
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
	
	/**
	 * 
	 * @return Number of digits including decimal point and -ve sign
	 */
	public Integer size() {
		if(size == null)
			this.setSize(this.getValue().size());
		return size;
	}

	private void setSize(Integer size) {
		this.size = size;
	}

}