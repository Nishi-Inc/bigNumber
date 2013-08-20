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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.bigNumber.common.services.Constants;
import org.bigNumber.common.services.exceptions.IncompatibleCharacterException;

/**
 * This class makes Big type number object
 * @author <a href="mailto:shuklaalok7@gmail.com">Alok Shukla</a>
 * @since August 7, 2013, v0.1.0
 */
public final class Big implements Serializable, Comparable<Big> {

	private static final long serialVersionUID = 1L;
	
	private List<Character>		value;
	private BigInteger			bigInteger;
	private BigDecimal			bigDecimal;
	private boolean				isNegative			=	false;
	private boolean				isFractional		=	false;
	private boolean				isZero				=	false;
	private Integer				locationOfDecimal;
	private	Integer				size;
	
	
	//____________________________________________________________________________________
	//********************************* CONSTRUCTORS *************************************
	//____________________________________________________________________________________
	
	
	/**
	 * Constructs a Big type number with default value 0
	 * @author Alok Shukla
	 * @since v0.1.0
	 */
	public Big(){
		try {
			this.setValue(0);
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param number
	 * @author Alok Shukla
	 * @since v0.1.0
	 */
	public Big(Big number) {
		try {
			this.setValue(number);
		} catch (IncompatibleCharacterException e) {
			e.showMsg();
		}
	}
	
	/**
	 * @author Alok Shukla
	 * @param number
	 * @since v1.0.0
	 */
	public Big(BigDecimal number) {
		try {
			this.setValue(number.toPlainString());
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @author Alok Shukla
	 * @param number A BigInteger
	 * @since v1.0.0
	 */
	public Big(BigInteger number) {
		try {
			this.setValue(number.toString());
		} catch (IncompatibleCharacterException e) {
			e.showMsg();
		}
	}
	
	/**
	 * @author Alok Shukla
	 * @since v1.0.0
	 * @param number
	 * @throws IncompatibleCharacterException
	 */
	public Big(String number) throws IncompatibleCharacterException {
		this.setValue(number);
	}
	
	/**
	 * @author Alok Shukla
	 * @since v1.0.0
	 * @param number
	 * @throws IncompatibleCharacterException
	 */
	public Big(List<Character> number) throws IncompatibleCharacterException {
		this.setValue(number);
	}
	
	/**
	 * @author Alok Shukla
	 * @since v1.0.0
	 * @param number
	 * @throws IncompatibleCharacterException
	 */
	public Big(Integer number) throws IncompatibleCharacterException {
		this.setValue(number);
	}
	
	/**
	 * @author Alok Shukla
	 * @since v1.0.0
	 * @param number
	 * @throws IncompatibleCharacterException
	 */
	public Big(Float number) throws IncompatibleCharacterException {
		this.setValue(number);
	}
	
	/**
	 * @author Alok Shukla
	 * @since v1.0.0
	 * @param number
	 * @throws IncompatibleCharacterException
	 */
	public Big(Double number) throws IncompatibleCharacterException {
		this.setValue(number);
	}
	
	//____________________________________________________________________________________
	//********************************* METHODS ******************************************
	//____________________________________________________________________________________
	
	
	//================================ INHERITED METHODS ===============================
	
	/**
	 * @return 0 if equal, +1 if greater and -1 if less
	 * @author Alok Shukla
	 * @since v0.1.0
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
	
	//==================================================================================
	
	
	
	//================================ setValue() ======================================
	
	/**
	 * It puts the provided number in the Big variable
	 * @param number
	 * As String, int, float, double, long etc.
	 * @throws IncompatibleCharacterException
	 * @author Alok Shukla
	 * @since August 6, 2013, v0.1.0
	 */
	public <E extends Comparable<E>> void setValue(E number) throws IncompatibleCharacterException {
		this.setValue(number.toString());
	}
	
	/**
	 * It puts the provided number in the Big variable
	 * @param number
	 * Big number
	 * @throws IncompatibleCharacterException
	 * @author Alok Shukla
	 * @since v0.1.0
	 */
	public void setValue(Big number) throws IncompatibleCharacterException {
		this.setValue(number.getValue());
	}
	
	/**
	 * It puts the provided number in the Big variable
	 * @param number As String which may contain only numbers 0 to 9, a leading -ve sign '-' and a decimal point  
	 * @throws IncompatibleCharacterException
	 * @author Alok Shukla
	 * @since August 6, 2013, v0.1.0
	 */
	public void setValue(List<Character> number) throws IncompatibleCharacterException {
		StringBuilder chars	=	new StringBuilder();
		for(int i=0; i<number.size(); i++)
			chars.append(number.get(i));
		this.setValue(chars.toString());
	}
	
	/**
	 * <ul><li>No character accept '0' to '9', '.' and '-' is allowed.</li>
	 * <li>No two '.' are allowed.</li>
	 * <li>Spaces (' ' or " ") in the provided String are removed before setting the value so no problem with spaces.</li>
	 * <li>'-' is allowed only as first character</li></ul>
	 * @param value
	 * @throws IncompatibleCharacterException
	 */
	public void setValue(String value) throws IncompatibleCharacterException {
		this.resetValue();
		value.replaceAll(" ", "");
		int size = value.length();
		boolean error = true;
		this.setZero(true);
		for(int i=0; i<size; i++) {
			Character digit = value.charAt(i);
			/*if(digit == null) {
				break;
			}*/
			if(digit <= '9' && digit >= '0') {
				error = false;
			} else if(digit == '-') {
				error = false;
			} else if(digit == '.') {
				error = false;
			}
			if(error) {
				throw new IncompatibleCharacterException("From Big.setValue(): value is incompatible.");
			}
			
			if(digit == '.') {
				if(this.isFractional())
					throw new IncompatibleCharacterException("From Big.setValue(): value is incompatible.");
				this.setFractional(true);
				this.setLocationOfDecimal(i);
			}
			if(this.isZero() && (digit > '0' && digit <= '9'))
				this.setZero(false);
			this.getValue().add(digit);
		}
		
		if(this.isFractional()) {
			this.setBigDecimal(new BigDecimal(value));
		} else {
			this.setBigInteger(new BigInteger(value));
		}
		//this.consolidate();
		// Set other variables according to this new value
		if(this.getValue().get(0) == '-')
			this.setNegative(true);
	}
	
	//==================================================================================
	
	
	//================================ Mathematical ====================================
	
	public void add(Big number) {
		// TODO Write Logic
	}
	
	public void multiply(Big number) {
		// TODO Write Logic
	}
	
	public void subtract(Big number) {
		// TODO Write Logic
	}
	
	public void divide(Big number) {
		// TODO Write Logic
	}
	
	public void absolute() {
		// TODO Write Logic
	}
	
	public void pow(int power) {
		if(this.isFractional()) {
			this.getBigDecimal().pow(power);
			this.syncFromDecimal();
		} else {
			this.getBigInteger().pow(power);
			this.syncFromInteger();
		}
	}
	
	//==================================================================================
	
	//================================= UTILITY ========================================
	
	
	/**
	 * Returns a plain String containing value of the Big Number
	 * @author Alok Shukla
	 * @since v0.1.0
	 */
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
	
	/*public String toString() {
		if(this.isFractional()) {
			return this.getBigDecimal().toPlainString();
		}
		return this.getBigInteger().toString();
	}*/
	
	/**
	 * Returns a plain String containing value of the Big Number
	 * @author Alok Shukla
	 * @since v1.0.0
	 */
	public String toEngineeringString() {
		if(this.isZero()) {
			return "0";
		}
		
		if(this.isFractional()) {
			return this.getBigDecimal().toEngineeringString();
		}
		// Logic in case of BigInteger
		
		StringBuilder result = new StringBuilder();
		
		int i=0;
		if(this.isNegative()) {
			result.append("-");
			i = 1;
		}
		
		result.append(this.charAt(i));
		if(i+1 < this.size()) {
			result.append('.');
			if(i+2 < this.size()) {
				if(this.charAt(i+2) > '5' || ((this.charAt(i+1)-'0') %2 == 0 && this.charAt(i+2) == '5')) {
					result.append(this.charAt(i+1) + 1);
				}
			} else {
				result.append(this.charAt(i+1));
			}
		}
		result.append('E');
		result.append(this.size() - i - 1);
		
		/*int comparison = 0;
		try {
			comparison = Calculate.absolute(this).compareTo(new Big(1));
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}
		
		if(comparison == 0) {
			return "1E0";
		} else if(comparison == -1) {
			// number is smaller than 1
			for(int i=this.locationOfDecimal()+1; i<this.size(); i++) {
				if(this.charAt(i) != '0') {
					result.append(this.charAt(i));
					if(i+1 < this.size()) {
						result.append('.');
						result.append(this.charAt(i+1));
					}
					result.append('E');
					result.append(-1*(i-this.locationOfDecimal()));
					break;
				}
			}
		} else if(comparison == 1) {
			// number is greater than 1
			
		}*/
		
		return result.toString();
	}
	
	/**
	 * @return A List of characters containing value of the Big number
	 * @author Alok Shukla
	 * @since v0.1.0
	 */
	public List<Character> getValue() {
		if(value == null)
			value	=	new ArrayList<Character>();
		return value;
	}
	
	/**
	 * @author Alok Shukla
	 * @since v1.0.0
	 * @return Value of the Big number as BigDecimal
	 */
	public BigDecimal toBigDecimal() {
		return this.getBigDecimal();
	}
	
	/**
	 * @author Alok Shukla
	 * @since v1.0.0
	 * @return Value of the Big number as BigInteger
	 */
	public BigDecimal toBigInteger() {
		return this.getBigDecimal();
	}
	
	/**
	 * @author Alok Shukla
	 * @since v1.0.0
	 * @return Value of the Big number as an Integer<Br/>WARNING: Data can be lost.
	 */
	public Integer toInteger() {
		// TODO Write Logic
		return new Integer(0);
	}
	
	/**
	 * @author Alok Shukla
	 * @since v1.0.0
	 * @return Value of the Big number as a Double<Br/>WARNING: Data can be lost.
	 */
	public Double toDouble() {
		// TODO Write Logic
		return new Double(0);
	}
	
	/**
	 * 
	 * @param startIndex
	 * @param endIndex
	 * @return Value of the number as List of characters between (and including) the given indices
	 * @author Alok Shukla
	 * @since v0.1.0
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
	 * @author Alok Shukla
	 * @since v0.1.0
	 */
	public List<Character> getValueTill(int index) {
		return this.getValueBetween(0, index);
	}
	
	/**
	 * Gives the character at specified index
	 * @param index
	 * @return The character at the specified index
	 * @author Alok Shukla
	 * @since v0.1.0
	 * @deprecated Replaced by <i>charAt(int)</i>
	 */
	public char get(int index) {
		return this.getValue().get(index);
	}
	
	/**
	 * Gives the character at specified index
	 * @param index
	 * @return The character at the specified index
	 * @author Alok Shukla
	 * @since v0.1.0
	 */
	public Character charAt(int index) {
		return this.getValue().get(index);
	}
	
	/**
	 * It doesn't return an Integer.<br/>It discards the fractional part and keeps the whole number.
	 * @author Alok Shukla
	 * @since v0.1.0
	 */
	public void convertToInteger() {
		if(!this.isFractional())
			return;
		else {
			for(int i=this.locationOfDecimal(); i<this.size(); i++)
				this.getValue().remove(i);
			this.setBigDecimal(null);
			this.setBigInteger(new BigInteger(this.toString()));
			this.setFractional(false);
		}
	}
	
	/**
	 * Converts a number to negative
	 * @author Alok Shukla
	 * @since August 8, 2013, v0.1.0
	 * @deprecated Replaced by <i>makeNegative()</i>
	 */
	public void convertToNegative() {
		try {
			this.putAtFirst('-');
		} catch (IncompatibleCharacterException e) {
			e.showMsg();
		}
	}
	
	/**
	 * Converts a number to negative
	 * @author Alok Shukla
	 * @since August 8, 2013, v0.1.0
	 */
	public void makeNegative() {
		try {
			this.putAtFirst('-');
		} catch (IncompatibleCharacterException e) {
			e.showMsg();
		}
	}
	
	/**
	 * Rounds off a number to the given number of digits
	 * @author Alok Shukla
	 * @param numberOfDigitsAfterDecimal
	 * @since August 8, 2013, v0.1.0
	 */
	public void roundOff(Integer numberOfDigitsAfterDecimal) {
		if(!this.isFractional())
			return;
		if(numberOfDigitsAfterDecimal == 0) {
			this.convertToInteger();
			return;
		}
		int j = this.locationOfDecimal();
		int i = j+1;
		if((this.size()-i) < numberOfDigitsAfterDecimal)
			return;
		else {
			j += numberOfDigitsAfterDecimal;
			i = j+1;
			if((this.charAt(i) < '5') || (this.charAt(i) == '5' && this.charAt(j)%2 == 0))
				return;
			try {
				this.modify(j, this.charAt(j) + 1 - '0');
			} catch (NumberFormatException | IncompatibleCharacterException e) {
				e.printStackTrace();
			}
			j++;
			while(j<this.size()) {
				this.getValue().remove(j);
			}
		}
	}
	
	//==================================================================================
	
	//========================== PRIVATE HELPER METHODS ================================
	
	private void syncFromValue() {
		// TODO set all variables and values as per value in the value of the Big
		// Variables to set size, isZero, isNegative, locationOfDecimal, isFractional, bigDecimal or bigInteger
		List<Character> val = null;
		val = this.getValue();
		
		this.setSize(val.size());
		
		if(val.get(0) == '-') {
			this.setNegative(true);
		} else {
			this.setNegative(false);
		}
		
		// Write here
	}
	
	private void syncFromDecimal() {
		// TODO set all variables and values as per value in the BigDecimal variable		
	}
	
	private void syncFromInteger() {
		// TODO set all variables and values as per value in the BigInteger variable
	}
	
	private void resetValue() {
		this.value = null;
	}
	
	//==================================================================================
	
	/**
	 * Rounds off a number to the number of digits specified by Constants.DEFAULT_ROUND_OFF_DIGITS
	 * @author Alok Shukla
	 * @since August 8, 2013, v0.1.0
	 */
	public void roundOff() {
		this.roundOff(Constants.DEFAULT_ROUND_OFF_DIGITS);
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
	 * Inserts a new digit at the given index
	 * @param index
	 * @param digit
	 * @throws IncompatibleCharacterException
	 * @author Alok Shukla
	 * @since v0.1.0
	 */
	public void insert(int index, char digit) throws IncompatibleCharacterException {
		boolean flag = false;
		if(digit == '.') {
			if(!this.isFractional())
				flag = true;
			else if(this.getValue().get(index) == '.')
				return;
			else
				throw new IncompatibleCharacterException("From Big.insert(): newDIgit is incompatible.");
		} else if(digit>'0' && digit<'9') {
			flag = true;
		} else
			throw new IncompatibleCharacterException("From Big.insert(): newDIgit is incompatible.");
		if(flag)
			this.insert(index, digit-'0');
	}
	
	/**
	 * Inserts a new digit at the given index
	 * @param index
	 * @param digit
	 * @throws IncompatibleCharacterException
	 * @author Alok Shukla
	 * @since v0.1.0
	 */
	public void insert(int index, int digit) throws IncompatibleCharacterException {
		if(digit>9 || digit<0)
			throw new IncompatibleCharacterException("From Big.insert(): newDIgit is incompatible.");
		this.getValue().add(index, (char)(digit + '0'));
	}
	
	/**
	 * Puts the given digit at the beginning of the given number
	 * @param digit An int
	 * @throws IncompatibleCharacterException
	 * @author Alok Shukla
	 * @since August 8, 2013, v0.1.0
	 */
	public void putAtFirst(int digit) throws IncompatibleCharacterException {
		Big result	=	new Big();
		result.put(digit);
		result.append(this);
		this.setValue(result);
	}
	
	/**
	 * Puts the given character at the beginning of the given number
	 * @param character A char
	 * @throws IncompatibleCharacterException
	 * @author Alok Shukla
	 * @since August 8, 2013, v0.10
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
	 * @author Alok Shukla
	 * @since v0.1.0
	 */
	public void reverse() {
		int size  = this.size();
		int limit = 0;
		if(this.isNegative())
			limit = 1;
		for(int count=0; count<=size-limit-1; count++) {
			try {
				this.insert(count+limit, this.getValue().get(size-1));
			} catch (IncompatibleCharacterException e) {
				e.showMsg();
			}
			this.getValue().remove(size);
		}
	}
	
	/**
	 * Removes leading zeroes as well as trailing zeroes in case of fraction
	 * @author Alok Shukla
	 * @since v0.1.0
	 */
	public void consolidate() {
		// TODO Write Logic
		// Remember -ve and fractions
		int startIndex = 0;
		
		if(isNegative())
			startIndex = 1;
		
		if(this.isFractional()) {
			// Consolidate from both the ends
		} else {
			// Consolidate only from starting end
		}
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
	 * @author Alok Shukla
	 * @since v0.1.0 
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
	 * Replaced by setValue()
	 * It puts the provided number in the Big variable
	 * @param number
	 * As String, int, float, double, long etc.
	 * @throws IncompatibleCharacterException
	 * @author Alok Shukla
	 * @since v0.1.0
	 * @deprecated
	 */
	public <E extends Comparable<?>> void put(E number) throws IncompatibleCharacterException {
		ArrayList<Character> numberList	=	new ArrayList<Character>();
		String 				 num 		=	number.toString();
		int					 size 		=	num.length();
		for(int i=0; i<size; i++)
			numberList.add(num.charAt(i));
		this.setValue(numberList);
	}

	/**
	 * @return true if the number is negative else false
	 * @author Alok Shukla
	 * @since v0.1.0
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
	 * @author Alok Shukla
	 * @since v0.1.0
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
	 * @author Alok Shukla
	 * @since v0.1.0
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
	 * @author Alok Shukla
	 * @since v0.1.0
	 */
	public Integer size() {
		this.setSize(this.getValue().size());
		return size;
	}

	private void setSize(Integer size) {
		this.size = size;
	}

	private BigInteger getBigInteger() {
		if(bigInteger == null) {
			this.setBigInteger(new BigInteger("0"));
		}
		return bigInteger;
	}

	private void setBigInteger(BigInteger bigInteger) {
		this.bigInteger = bigInteger;
	}

	private BigDecimal getBigDecimal() {
		if(bigDecimal == null) {
			this.setBigDecimal(new BigDecimal(0));
		}
		return bigDecimal;
	}

	private void setBigDecimal(BigDecimal bigDecimal) {
		this.bigDecimal = bigDecimal;
	}

	public boolean isZero() {
		return isZero;
	}

	public void setZero(boolean isZero) {
		this.isZero = isZero;
	}
	
	/**
	 * @author Alok Shukla
	 * @since v1.0.0
	 * @return A BigDecimal converting Big to BigDecimal
	 */
	public BigDecimal convertToBigDecimal() {
		if(this.isFractional()) {
			return this.getBigDecimal();
		} else {
			StringBuilder val = new StringBuilder();
			for(int i=0; i<this.size(); i++) {
				val.append(this.get(i));
			}
			return new BigDecimal(val.toString());
		}
	}
	
	/**
	 * @author Alok Shukla
	 * @since v1.0.0
	 * @return A BigInteger chopping off the decimal part of the Big if present
	 */
	public BigInteger convertToBigInteger() {
		if(!this.isFractional()) {
			return this.getBigInteger();
		} else {
			StringBuilder val = new StringBuilder();
			for(int i=0; this.get(i) != '.'; i++) {
				val.append(this.get(i));
			}
			return new BigInteger(val.toString());
		}
	}

}