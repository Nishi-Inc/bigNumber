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

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.bigNumber.common.services.Constants;
import org.bigNumber.common.services.exceptions.IncompatibleCharacterException;

/**
 * This class makes BigNumber type objects
 * @author <a href="mailto:shuklaalok7@gmail.com">Nishi Inc.</a>
 * @since August 7, 2013, v0.1.0
 */
public final class BigNumber implements Serializable, Comparable<BigNumber> {

	public static final long serialVersionUID = 1L;
	
	private List<Character>		value;
	private BigInteger			bigInteger;
	private BigDecimal			bigDecimal;
	private boolean				isNegative			=	false;
	private boolean				isFractional		=	false;
	private boolean				isZero				=	false;
	private Integer				locationOfDecimal;
	
	
	//____________________________________________________________________________________
	//********************************* CONSTRUCTORS *************************************
	//____________________________________________________________________________________
	
	
	/**
	 * Constructs a BigNumber type number with default value 0
	 * @author Nishi Inc.
	 * @since v0.1.0
	 */
	public BigNumber(){
		try {
			this.setValue(0);
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param number
	 * @author Nishi Inc.
	 * @since v0.1.0
	 */
	public BigNumber(BigNumber number) {
		try {
			this.setValue(number);
		} catch (IncompatibleCharacterException e) {
			e.showMsg();
		}
	}
	
	/**
	 * @author Nishi Inc.
	 * @param number
	 * @since v1.0.0
	 */
	public BigNumber(BigDecimal number) {
		try {
			this.setValue(number.toPlainString());
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @author Nishi Inc.
	 * @param number A BigInteger
	 * @since v1.0.0
	 */
	public BigNumber(BigInteger number) {
		try {
			this.setValue(number.toString());
		} catch (IncompatibleCharacterException e) {
			e.showMsg();
		}
	}
	
	/**
	 * @author Nishi Inc.
	 * @since v1.0.0
	 * @param number
	 * @throws IncompatibleCharacterException
	 */
	public BigNumber(String number) throws IncompatibleCharacterException {
		this.setValue(number);
	}
	
	/**
	 * @author Nishi Inc.
	 * @since v1.0.0
	 * @param number
	 * @throws IncompatibleCharacterException
	 */
	public BigNumber(List<Character> number) throws IncompatibleCharacterException {
		this.setValue(number);
	}
	
	/**
	 * @author Nishi Inc.
	 * @since v1.0.0
	 * @param number
	 * @throws IncompatibleCharacterException
	 */
	public BigNumber(Integer number) throws IncompatibleCharacterException {
		this.setValue(number);
	}
	
	/**
	 * @author Nishi Inc.
	 * @since v1.0.0
	 * @param number
	 * @throws IncompatibleCharacterException
	 */
	public BigNumber(Float number) throws IncompatibleCharacterException {
		this.setValue(number);
	}
	
	/**
	 * @author Nishi Inc.
	 * @since v1.0.0
	 * @param number
	 * @throws IncompatibleCharacterException
	 */
	public BigNumber(Double number) throws IncompatibleCharacterException {
		this.setValue(number);
	}
	
	//____________________________________________________________________________________
	//********************************* METHODS ******************************************
	//____________________________________________________________________________________
	
	
	//================================ INHERITED METHODS ===============================
	
	/**
	 * @return 0 if equal, +1 if greater and -1 if less
	 * @author Nishi Inc.
	 * @since v0.1.0
	 */
	@Override
	public int compareTo(BigNumber number) {
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

		BigNumber tmp1	=	null;
		BigNumber tmp2	=	null;
		BigNumber frc1	=	null;
		BigNumber frc2	=	null;

		if(this.isFractional() && number.isFractional()) {
			tmp1	=	new BigNumber();
			tmp2	=	new BigNumber();
			try {
				tmp1.setValue(this.getValueTill(this.locationOfDecimal()-1));
				tmp2.setValue(number.getValueTill(number.locationOfDecimal()-1));
				result = tmp1.compareTo(tmp2);
				if(result == 0) {
					frc1	=	new BigNumber();
					frc2	=	new BigNumber();
					frc1.setValue(this.getValueBetween(this.locationOfDecimal()+1, this.size()-1));
					frc2.setValue(number.getValueBetween(number.locationOfDecimal()+1, number.size()-1));
					result	=	frc1.compareTo(frc2);
				}
			} catch (IncompatibleCharacterException e) {
				e.printStackTrace();
			}
		} else if(this.isFractional() && !number.isFractional()) {
			tmp1	=	new BigNumber();
			try {
				tmp1.setValue(this.getValueTill(this.locationOfDecimal()-1));
				result = tmp1.compareTo(number);
				if(result == 0)
					result	=	1;
			} catch (IncompatibleCharacterException e) {
				e.printStackTrace();
			}
		} else if(!this.isFractional() && number.isFractional()) {
			tmp2	=	new BigNumber();
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
	 * It puts the provided number in the BigNumber variable
	 * toString() method on the provided number will be called hence do not use primitive types
	 * @param number
	 * As String, Integer, Float, Double, Long etc.
	 * @throws IncompatibleCharacterException
	 * @author Nishi Inc.
	 * @since August 6, 2013, v0.1.0
	 */
	public <E extends Comparable<E>> void setValue(E number) throws IncompatibleCharacterException {
		this.setValue(number.toString());
	}
	
	/**
	 * It puts the provided number in the BigNumber variable
	 * @param number
	 * BigNumber number
	 * @throws IncompatibleCharacterException
	 * @author Nishi Inc.
	 * @since v0.1.0
	 */
	public void setValue(BigNumber number) throws IncompatibleCharacterException {
		this.setValue(number.getValue());
	}
	
	/**
	 * It puts the provided number in the BigNumber variable
	 * @param number As String which may contain only numbers 0 to 9, a leading -ve sign '-' and a decimal point  
	 * @throws IncompatibleCharacterException
	 * @author Nishi Inc.
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
				throw new IncompatibleCharacterException("From BigNumber.setValue(): value is incompatible.");
			}
			
			if(digit == '.') {
				if(this.isFractional())
					throw new IncompatibleCharacterException("From BigNumber.setValue(): value is incompatible.");
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
	
	/**
	 * @author Nishi Inc.
	 * @since v1.0.0
	 * @param number To add to <i>this</i>
	 */
	public void add(BigNumber number) {

		if(this.isFractional() && number.isFractional()) {
			// Both are fractional
			this.setBigDecimal(this.getBigDecimal().add(number.getBigDecimal()));
			this.syncFromDecimal();
		} else if(!this.isFractional() && number.isFractional()) {
			// firstNumber is not fractional
			this.makeFractional();
			this.add(number);
		} else if(this.isFractional() && !number.isFractional()) {
			//secondNumber is not fractional
			number.makeFractional();
			this.add(number);
		} else {
			//Both are non-fractional
			this.setBigInteger(this.getBigInteger().add(number.getBigInteger()));
			this.syncFromInteger();
		}
	}
	
	/**
	 * @author Nishi Inc.
	 * @since v1.0.0
	 * @param number To multiply to <i>this</i>
	 */
	public void multiply(BigNumber number) {

		if(this.isFractional() && number.isFractional()) {
			// Both are fractional
			this.setBigDecimal(this.getBigDecimal().multiply(number.getBigDecimal()));
			this.syncFromDecimal();
		} else if(!this.isFractional() && number.isFractional()) {
			// firstNumber is not fractional
			this.makeFractional();
			this.multiply(number);
		} else if(this.isFractional() && !number.isFractional()) {
			//secondNumber is not fractional
			number.makeFractional();
			this.multiply(number);
		} else {
			//Both are non-fractional
			this.setBigInteger(this.getBigInteger().multiply(number.getBigInteger()));
			this.syncFromInteger();
		}
	}
	
	/**
	 * @author Nishi Inc.
	 * @since v1.0.0
	 * @param number To multiply to <i>this</i>
	 */
	public void subtract(BigNumber number) {
		if(this.isFractional() && number.isFractional()) {
			// Both are fractional
			this.setBigDecimal(this.getBigDecimal().subtract(number.getBigDecimal()));
			this.syncFromDecimal();
		} else if(!this.isFractional() && number.isFractional()) {
			// firstNumber is not fractional
			this.makeFractional();
			this.subtract(number);
		} else if(this.isFractional() && !number.isFractional()) {
			//secondNumber is not fractional
			number.makeFractional();
			this.subtract(number);
		} else {
			//Both are non-fractional
			this.setBigInteger(this.getBigInteger().subtract(number.getBigInteger()));
			this.syncFromInteger();
		}
	}
	
	/**
	 * @author Nishi Inc.
	 * @since v1.0.0
	 * @param number To multiply to <i>this</i>
	 */
	public void divide(BigNumber number) {
		if(this.isFractional() && number.isFractional()) {
			// Both are fractional
			this.setBigDecimal(this.getBigDecimal().divide(number.getBigDecimal()));
			this.syncFromDecimal();
		} else if(!this.isFractional() && number.isFractional()) {
			// firstNumber is not fractional
			this.makeFractional();
			this.divide(number);
		} else if(this.isFractional() && !number.isFractional()) {
			//secondNumber is not fractional
			number.makeFractional();
			this.divide(number);
		} else {
			//Both are non-fractional
			this.setBigInteger(this.getBigInteger().divide(number.getBigInteger()));
			this.syncFromInteger();
		}
	}
	
	/**
	 * 
	 * @param number A BigNumber to divide and compute remainder
	 * @return An array of two BigNumber elements, the quotient <i>(this/number)</i> is the 0th element and remainder <i>(this%number)</i> is the 1st element
	 */
	public BigNumber[] divideAndRemainder(BigNumber number) {
		BigNumber[] result = {new BigNumber(),new BigNumber()};
		
		if(this.isFractional() && number.isFractional()) {
			// Both are fractional
			BigDecimal[] dummy;
			dummy = this.getBigDecimal().divideAndRemainder(number.getBigDecimal());
			result[0].setBigDecimal(dummy[0]);
			result[1].setBigDecimal(dummy[1]);
			result[0].syncFromDecimal();
			result[1].syncFromDecimal();
		} else if(!this.isFractional() && number.isFractional()) {
			// this is not fractional
			this.makeFractional();
			this.divideAndRemainder(number);
		} else if(this.isFractional() && !number.isFractional()) {
			// number is not fractional
			number.makeFractional();
			this.divideAndRemainder(number);
		} else {
			//Both are non-fractional
			BigInteger[] dummy;
			dummy = this.getBigInteger().divideAndRemainder(number.getBigInteger());
			result[0].setBigInteger(dummy[0]);
			result[1].setBigInteger(dummy[1]);
			result[0].syncFromInteger();
			result[1].syncFromInteger();
		}
		
		return result;
	}
	
	public void absolute() {
		if(this.isFractional()) {
			this.setBigDecimal(this.getBigDecimal().abs());
			this.syncFromDecimal();
		} else {
			this.setBigInteger(this.getBigInteger().abs());
			this.syncFromInteger();
		}
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
	 * Returns a plain String containing value of the BigNumber Number
	 * @author Nishi Inc.
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
	 * Returns a plain String containing value of the BigNumber Number
	 * @author Nishi Inc.
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
		return result.toString();
	}
	
	/**
	 * @return A List of characters containing value of the BigNumber number
	 * @author Nishi Inc.
	 * @since v0.1.0
	 */
	public List<Character> getValue() {
		if(value == null)
			value	=	new ArrayList<Character>();
		return value;
	}
	
	/**
	 * @author Nishi Inc.
	 * @since v1.0.0
	 * @return Value of the BigNumber number as BigDecimal
	 */
	public BigDecimal toBigDecimal() {
		return this.getBigDecimal();
	}
	
	/**
	 * @author Nishi Inc.
	 * @since v1.0.0
	 * @return Value of the BigNumber number as BigInteger
	 */
	public BigDecimal toBigInteger() {
		return this.getBigDecimal();
	}
	
	/**
	 * @author Nishi Inc.
	 * @since v1.0.0
	 * @return Value of the BigNumber number as an Integer<Br/>WARNING: Data can be lost.
	 * @see java.lang.Integer
	 */
	public Integer toInteger() {
		Integer result;
		try {
			result = new Integer(this.toString());
		} catch(NumberFormatException e) {
			//Trying to put first 19 digits
			try {
				result = new Integer(this.toString().substring(0, 18));
			} catch(Exception ex) {
				//Trying for first 10 digits
				result = new Integer(this.toString().substring(0, 9));
			}
		}
		return result;
	}
	
	/**
	 * @author Nishi Inc.
	 * @since v1.0.0
	 * @return Value of the BigNumber number as a Double<Br/>WARNING: Data can be lost.
	 */
	public Double toDouble() {
		Double result;
		try {
			result = new Double(this.toString());
		} catch(NumberFormatException e) {
			//Trying to put first 19 digits
			try {
				result = new Double(this.toString().substring(0, 18));
			} catch(Exception ex) {
				//Trying for first 10 digits
				result = new Double(this.toString().substring(0, 9));
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @return Number of digits including decimal point and -ve sign
	 * @author Nishi Inc.
	 * @since v0.1.0
	 */
	public Integer size() {
		return this.getValue().size();
	}
	
	/**
	 * 
	 * @param startIndex
	 * @param endIndex
	 * @return Value of the number as List of characters between (and including) the given indices
	 * @author Nishi Inc.
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
	 * @author Nishi Inc.
	 * @since v0.1.0
	 */
	public List<Character> getValueTill(int index) {
		return this.getValueBetween(0, index);
	}
	
	/**
	 * Gives the character at specified index
	 * @param index
	 * @return The character at the specified index
	 * @author Nishi Inc.
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
	 * @author Nishi Inc.
	 * @since v0.1.0
	 */
	public Character charAt(int index) {
		return this.getValue().get(index);
	}
	
	/**
	 * It doesn't return an Integer.<br/>It discards the fractional part and keeps the whole number.
	 * @author Nishi Inc.
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
	 * @author Nishi Inc.
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
	 * @author Nishi Inc.
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
	 * @author Nishi Inc.
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
	
	/**
	 * Rounds off a number to the number of digits specified by Constants.DEFAULT_ROUND_OFF_DIGITS
	 * @author Nishi Inc.
	 * @since August 8, 2013, v0.1.0
	 */
	public void roundOff() {
		this.roundOff(Constants.DEFAULT_ROUND_OFF_DIGITS);
	}
	
	/**
	 * Changes the digit at the given index to the given digit
	 * @param index
	 * @param newDigit
	 * @author Nishi Inc.
	 * @throws IncompatibleCharacterException 
	 * @since v0.1.0
	 */
	public void modify(int index, int newDigit) throws IncompatibleCharacterException {
		if(newDigit>9 || newDigit<0)
			throw new IncompatibleCharacterException("From BigNumber.modify(): newDIgit is incompatible.");
		this.getValue().add(index, (char)(newDigit + '0'));
		this.getValue().remove(index+1);
	}
	
	/**
	 * Changes the digit at the given index to the given digit
	 * @param index
	 * @param newDigit
	 * @author Nishi Inc.
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
				throw new IncompatibleCharacterException("From BigNumber.modify(): newDIgit is incompatible.");
		} else if(newDigit>'0' && newDigit<'9') {
			flag = true;
		} else
			throw new IncompatibleCharacterException("From BigNumber.modify(): newDIgit is incompatible.");
		if(flag)
			this.modify(index, newDigit-'0');
	}
	
	/**
	 * Inserts a new digit at the given index
	 * @param index
	 * @param digit
	 * @throws IncompatibleCharacterException
	 * @author Nishi Inc.
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
				throw new IncompatibleCharacterException("From BigNumber.insert(): newDIgit is incompatible.");
		} else if(digit>'0' && digit<'9') {
			flag = true;
		} else
			throw new IncompatibleCharacterException("From BigNumber.insert(): newDIgit is incompatible.");
		if(flag)
			this.insert(index, digit-'0');
	}
	
	/**
	 * Inserts a new digit at the given index
	 * @param index
	 * @param digit
	 * @throws IncompatibleCharacterException
	 * @author Nishi Inc.
	 * @since v0.1.0
	 */
	public void insert(int index, int digit) throws IncompatibleCharacterException {
		if(digit>9 || digit<0)
			throw new IncompatibleCharacterException("From BigNumber.insert(): newDIgit is incompatible.");
		this.getValue().add(index, (char)(digit + '0'));
	}
	
	/**
	 * Puts the given digit at the beginning of the given number
	 * @param digit An int
	 * @throws IncompatibleCharacterException
	 * @author Nishi Inc.
	 * @since August 8, 2013, v0.1.0
	 */
	public void putAtFirst(int digit) throws IncompatibleCharacterException {
		BigNumber result	=	new BigNumber();
		result.setValue(digit);
		result.append(this);
		this.setValue(result);
	}
	
	/**
	 * Puts the given character at the beginning of the given number
	 * @param character A char
	 * @throws IncompatibleCharacterException
	 * @author Nishi Inc.
	 * @since August 8, 2013, v0.10
	 */
	public void putAtFirst(char character) throws IncompatibleCharacterException{
		if(character != '.' || character != '-')
			throw new IncompatibleCharacterException("From BigNumber.putAtFirst(): character is incompatible.");
		if(character == '.' && this.isFractional())
			throw new IncompatibleCharacterException("From BigNumber.putAtFirst(): character is incompatible.");
		if(character == '-' && this.isNegative())
			return;
		BigNumber result	=	new BigNumber();
		result.setValue(character);
		result.append(this);
	}
	
	/**
	 * Reverses the BigNumber
	 * @author Nishi Inc.
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
	 * @author Nishi Inc.
	 * @since v1.0.0
	 */
	public void consolidate() {
		// Remember -ve and fractions
		
		int startIndex = 0;
		if(isNegative()) {
			startIndex = 1;
		}
		
		for(;this.charAt(startIndex) == '0'; this.getValue().remove(startIndex));
		
		if(this.isFractional()) {
			// Consolidate from other end as well
			for(int i=this.size()-1; this.charAt(i) == '0'; i=this.size()-1) {
				this.getValue().remove(i);
			}
		}
		
		this.syncFromValue(true, true);
	}
	
	/**
	 * Appends the given number to the calling number
	 * @param number
	 * @throws IncompatibleCharacterException
	 * @author Nishi Inc.
	 * @since v0.1.0
	 */
	public void append(BigNumber number) throws IncompatibleCharacterException {
		if(this.isFractional() && number.isFractional())
			throw new IncompatibleCharacterException("From BigNumber.concat(): A number cannot have two decimal points.");
		if(number.isNegative())
			throw new IncompatibleCharacterException("From BigNumber.concat(): Second number should not be negative.");
		this.getValue().addAll(number.getValue());
	}
	
	/**
	 * Appends the given positive number to the BigNumber number
	 * @param number
	 * @throws IncompatibleCharacterException
	 * @author Nishi Inc.
	 * @since v0.1.0 
	 */
	public void append (int number) throws IncompatibleCharacterException {
		if(number < 0)
			throw new IncompatibleCharacterException("From BigNumber.append(): number is incompatible.");
		ArrayList<Character> num = new ArrayList<Character>();
		for(; number>10; number /= 10)
			num.add(0, (char)(number%10 +'0'));
		this.getValue().addAll(num);
	}
	
	/**
	 * Converts BigNumber to an Integer chopping off the decimal part of the BigNumber, if present
	 * @author Nishi Inc.
	 * @since v1.0.0
	 * @return Value of such modified BigNumber as BigInteger
	 */
	public BigInteger convertToBigInteger() {
		if(!this.isFractional()) {
			return this.getBigInteger();
		}
		StringBuilder val = new StringBuilder();
		for(int i=0; this.charAt(i) != '.'; i++) {
			val.append(this.charAt(i));
		}
		this.setBigInteger(new BigInteger(val.toString()));
		this.syncFromInteger();
		return this.getBigInteger();
	}
	
	/**
	 * Does nothing if <i>this</i> is already fractional else appends ".00"
	 */
	public void makeFractional() {
		if(this.isFractional()) {
			return;
		}
		
		try {
			this.setValue(this.toString() + ".00");
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}
		this.syncFromValue(true, false);
	}
	
	
	//==================================================================================
	
	//========================== PRIVATE HELPER METHODS ================================
	
	/**
	 * Sets all the field variables as per the value of bigNumber
	 */
	protected void syncFromValue(boolean toSetDecimal, boolean toSetInteger) {
		// set all variables and values as per value in the value of the BigNumber
		// Variables to set isZero, isNegative, locationOfDecimal, isFractional, bigDecimal or bigInteger
		List<Character> val = null;
		val = this.getValue();
		
		//this.setSize(val.size());
		
		if(val.get(0) == '-') {
			this.setNegative(true);
		} else {
			this.setNegative(false);
		}
		
		this.setZero(true);
		int i;
		if(this.isNegative()) {
			i=1;
		} else {
			i=0;
		}
		for(; i<val.size(); i++){
			char digit = val.get(i);
			if(digit != '.') {
				if(digit != '0') {
					this.setZero(false);
					break;
				}
			} else {
				this.setLocationOfDecimal(i);
				this.setFractional(true);
			}
		}
		
		String valueStr = this.toString();
		if(this.isFractional()) {
			if(toSetDecimal) {
				this.setBigDecimal(new BigDecimal(valueStr));
			}
		} else {
			if(toSetInteger) {
				this.setBigInteger(new BigInteger(valueStr));
			}
		}
	}
	
	/**
	 * Sets all the field variables as per the value of bigDecimal
	 */
	protected void syncFromDecimal() {
		try {
			this.setValue(this.getBigDecimal());
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}
		
		this.syncFromValue(false, true);
	}
	
	/**
	 * Sets all the field variables as per the value of bigInteger
	 */
	protected void syncFromInteger() {
		try {
			this.setValue(this.getBigInteger());
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}

		this.syncFromValue(true, false);
	}
	
	protected void resetValue() {
		this.value = null;
	}
	
	//==================================================================================
	

	/*==================================================================================
	 *                               GETTERS/SETTERS
	 *==================================================================================
	 */

	/**
	 * @return true if the number is negative else false
	 * @author Nishi Inc.
	 * @since v0.1.0
	 */
	public boolean isNegative() {
		return isNegative;
	}
	
	protected void setNegative(boolean isNegative) {
		this.isNegative = isNegative;
	}
	
	/**
	 * 
	 * @return true if the number is a fraction else false
	 * @author Nishi Inc.
	 * @since v0.1.0
	 */
	public boolean isFractional() {
		return isFractional;
	}

	protected void setFractional(boolean isFraction) {
		this.isFractional = isFraction;
		if(!isFraction)
			this.setLocationOfDecimal(-1);
	}

	/**
	 * 
	 * @return position of decimal point in the fractional number,
	 * -1 if it's not a fractional number
	 * @author Nishi Inc.
	 * @since v0.1.0
	 */
	public int locationOfDecimal() {
		if(!this.isFractional())
			return -1;
		return locationOfDecimal;
	}

	protected void setLocationOfDecimal(Integer locationOfDecimal) {
		this.locationOfDecimal = locationOfDecimal;
	}

	protected BigInteger getBigInteger() {
		if(bigInteger == null) {
			this.setBigInteger(new BigInteger("0"));
		}
		return bigInteger;
	}

	protected void setBigInteger(BigInteger bigInteger) {
		this.bigInteger = bigInteger;
	}

	protected BigDecimal getBigDecimal() {
		if(bigDecimal == null) {
			this.setBigDecimal(new BigDecimal(0));
		}
		return bigDecimal;
	}

	protected void setBigDecimal(BigDecimal bigDecimal) {
		this.bigDecimal = bigDecimal;
	}

	public boolean isZero() {
		return isZero;
	}

	protected void setZero(boolean isZero) {
		this.isZero = isZero;
	}
	
	/**
	 * @author Nishi Inc.
	 * @since v1.0.0
	 * @return A BigDecimal converting BigNumber to BigDecimal
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
	
	
	/*========================================================================================
	 * 			Protected Static Methods (Will be made public in successive releases)
	 * =======================================================================================
	 */
	
	/**
	 * @param numbers BigNumber numbers
	 * @return A BigNumber number containing product of the numbers provided
	 * @author Nishi Inc.
	 * @since v1.1.0
	 */
	protected static BigNumber multiply(BigNumber... numbers) {
		int total  = numbers.length;
		BigNumber result = new BigNumber(numbers[0]);
		
		for(int i=1; i<total; i++) {
			result = multiplyTwo(result, numbers[i]);
		}
		
		return result;
	}
	
	/**
	 * @author Nishi Inc.
	 * @since v1.1.0
	 * @param firstNumber
	 * @param secondNumber
	 * @return
	 */
	private static BigNumber multiplyTwo(BigNumber firstNumber, BigNumber secondNumber) {
		BigNumber result = new BigNumber();
		
		if(firstNumber.isFractional() && secondNumber.isFractional()) {
			// Both are fractional
			result.setBigDecimal(firstNumber.getBigDecimal().multiply(secondNumber.getBigDecimal()));
			result.syncFromDecimal();
		} else if(!firstNumber.isFractional() && secondNumber.isFractional()) {
			// TODO firstNumber is not fractional
		} else if(firstNumber.isFractional() && !secondNumber.isFractional()) {
			// TODO secondNumber is not fractional
		} else {
			//Both are non-fractional
			result.setBigInteger(firstNumber.getBigInteger().multiply(secondNumber.getBigInteger()));
			result.syncFromInteger();
		}
		
		return result;
	}

	/**
	 * 
	 * @param numerator or dividend
	 * @param denominator or divisor
	 * @return A BigNumber type variable containing result of arithmetic division of the provided numerator by the provided denominator
	 */
	protected static BigNumber divide(BigNumber numerator, BigNumber denominator) {
		// TODO Write logic
		BigNumber result	=	new BigNumber();
		return result;
	}

	/**
	 * Returns remainder
	 * @param numerator
	 * @param denominator
	 * @return A BigNumber type variable containing remainder of arithmetic division of the provided numerator by the provided denominator
	 * @throws IncompatibleCharacterException
	 * @author Nishi Inc.
	 * @since v1.1.0
	 */
	protected static BigNumber modulus(BigNumber numerator, BigNumber denominator) throws IncompatibleCharacterException {
		// TODO Write logic
		if(numerator.isFractional() || denominator.isFractional())
			throw new IncompatibleCharacterException("From Calculate.modulus(): Any of the numerator or denominator cannot be fractional.");
		BigNumber result	=	new BigNumber();
		return result;
	}

	/**
	 * Same as modulus()
	 * @param numerator
	 * @param denominator
	 * @return A BigNumber type variable containing remainder of arithmetic division of the provided numerator by the provided denominator
	 * @throws IncompatibleCharacterException
	 * @author Nishi Inc.
	 * @since v1.1.0
	 */
	protected static BigNumber mod(BigNumber numerator, BigNumber denominator) throws IncompatibleCharacterException {
		return modulus(numerator, denominator);
	}

	/**
	 * Same as sum()
	 * @param numbers BigNumber numbers
	 * @return A BigNumber type variable containing result of arithmetic addition of the provided numbers
	 */
	protected static BigNumber add(BigNumber... numbers) {
		int total  = numbers.length;
		
		BigNumber result = new BigNumber(numbers[0]);
		
		for(int i=1; i<total; i++)
			result = addTwo(result, numbers[i]);
		return result;
	}
	
	/**
	 *  This won't be converted to public
	 * @param firstNumber
	 * @param secondNumber
	 * @return
	 */
	private static BigNumber addTwo(BigNumber firstNumber, BigNumber secondNumber) {
		BigNumber result = new BigNumber();

		if(firstNumber.isFractional() && secondNumber.isFractional()) {
			// Both are fractional
			result.setBigDecimal(firstNumber.getBigDecimal().add(secondNumber.getBigDecimal()));
			result.syncFromDecimal();
		} else if(!firstNumber.isFractional() && secondNumber.isFractional()) {
			// TODO firstNumber is not fractional
		} else if(firstNumber.isFractional() && !secondNumber.isFractional()) {
			// TODO secondNumber is not fractional
		} else {
			//Both are non-fractional
			result.setBigInteger(firstNumber.getBigInteger().add(secondNumber.getBigInteger()));
			result.syncFromInteger();
		}

		return result;
	}

	/**
	 * Same as add() method
	 * @author Nishi Inc.
	 * @since v1.1.0
	 * @param numbers BigNumber numbers
	 * @return A BigNumber type variable containing result of arithmetic addition of the provided numbers
	 */
	protected static BigNumber sum(BigNumber... numbers) {
		return add(numbers);
	}

	/**
	 * Same as sub()
	 * @param firstNumber
	 * @param secondNumber
	 * @return A BigNumber type variable containing firstNumber - secondNumber
	 * @author Nishi Inc.
	 * @since v1.1.0
	 */
	protected static BigNumber subtract(BigNumber firstNumber, BigNumber secondNumber) {
		
		BigNumber result = new BigNumber();

		if(firstNumber.isFractional() && secondNumber.isFractional()) {
			// Both are fractional
			result.setBigDecimal(firstNumber.getBigDecimal().subtract(secondNumber.getBigDecimal()));
			result.syncFromDecimal();
		} else if(!firstNumber.isFractional() && secondNumber.isFractional()) {
			// firstNumber is not fractional
			firstNumber.makeFractional();
			return subtract(firstNumber, secondNumber);
		} else if(firstNumber.isFractional() && !secondNumber.isFractional()) {
			//secondNumber is not fractional
			secondNumber.makeFractional();
			return subtract(firstNumber, secondNumber);
		} else {
			//Both are non-fractional
			result.setBigInteger(firstNumber.getBigInteger().subtract(secondNumber.getBigInteger()));
			result.syncFromInteger();
		}

		return result;
	}

	/**
	 * Same as subtract()
	 * @author Nishi Inc.
	 * @since v1.1.0
	 * @param firstNumber
	 * @param secondNumber
	 * @return A BigNumber type variable containing firstNumber - secondNumber
	 */
	protected static BigNumber sub(BigNumber firstNumber, BigNumber secondNumber) {
		return subtract(firstNumber, secondNumber);
	}

	/**
	 * Returns absolute value of the given number
	 * @author Nishi Inc.
	 * @since v1.1.0
	 * @param number
	 * @return A BigNumber type variable containing absolute value of the given BigNumber number
	 */
	protected static BigNumber absolute(BigNumber number) {
		if(!number.isNegative())
			return number;
		BigNumber result = null;
		result = new BigNumber(number);
		result.getValue().remove(0);
		return result;
	}

	/**
	 * Same as absolute()
	 * @param number
	 * @return A BigNumber type variable containing absolute value of the given BigNumber number
	 * @author Nishi Inc.
	 * @since v1.1.0
	 */
	protected static BigNumber abs(BigNumber number) {
		return absolute(number);
	}
	
	//=============================== Utility Methods ==========================================
	
	/**
	 * Works similar to BigNumber.<i>reverse()</i>
	 * @param number A BigNumber number
	 * @return A BigNumber number with its digits in reverse order
	 * @author Nishi Inc.
	 * @since v1.2.0 
	 */
	protected static BigNumber reverse(BigNumber number) {
		number.reverse();
		return number;
	}
	
	/**
	 * Works similar to BigNumber.<i>reverse()</i>
	 * @param numbers
	 * @return An array of BigNumber numbers containing BigNumber numbers in order
	 * @author Nishi Inc.
	 * @since v1.2.0 
	 */
	protected static BigNumber[] reverse(BigNumber... numbers) {
		for(BigNumber number : numbers) {
			number.reverse();
		}
		return numbers;
	}
	
	/**
	 * Works similar to BigNumber.<i>consolidate()</i>
	 * @param number
	 * @return Removes leading zeroes and trailing zeroes as well in case of a fraction
	 * @author Nishi Inc.
	 * @since v1.2.0
	 */
	protected static BigNumber consolidate(BigNumber number) {
		number.consolidate();
		return number;
	}
	
	/**
	 * Works similar to BigNumber.<i>consolidate()</i>
	 * @param numbers
	 * @return Removes leading zeroes and trailing zeroes as well in case of a fraction
	 * @author Nishi Inc.
	 * @since v1.2.0
	 */
	protected static BigNumber[] consolidate(BigNumber... numbers) {
		for(BigNumber number : numbers) {
			number.consolidate();
		}
		return numbers;
	}
	
	
	/**
	 * @param firstNumber
	 * @param secondNumber
	 * @return A BigNumber number having secondNumber concatenated to firstNumber
	 * @throws IncompatibleCharacterException
	 * @author Nishi Inc.
	 * @since v1.2.0
	 */
	protected static BigNumber append(BigNumber... numbers) throws IncompatibleCharacterException {
		int length = numbers.length;
		for(int i=1; i<length; i++)
			numbers[0].append(numbers[i]);
		return numbers[0];
	}
	
	/**
	 * Puts the given digit at the beginning of the given number
	 * @param number A BigNumber number
	 * @param digit An int
	 * @return A BigNumber number containing given BigNumber number appended to the given digit
	 * @throws IncompatibleCharacterException
	 * @author Nishi Inc.
	 * @since v1.2.0
	 */
	protected static BigNumber putAtFirst(BigNumber number, int digit) throws IncompatibleCharacterException {
		number.putAtFirst(digit);
		return number;
	}
	
	/**
	 * Puts the given character at the beginning of the given number
	 * @param number A BigNumber number
	 * @param character A char
	 * @return A BigNumber number containing given BigNumber number appended to the given character
	 * @throws IncompatibleCharacterException
	 * @author Nishi Inc.
	 * @since v1.2.0
	 */
	protected static BigNumber putAtFirst(BigNumber number, char character) throws IncompatibleCharacterException{
		number.putAtFirst(character);
		return number;
	}
	
	//==========================================================================================

}