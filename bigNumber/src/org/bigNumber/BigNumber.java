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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.bigNumber.common.services.Constants;
import org.bigNumber.common.services.exceptions.IncompatibleCharacterException;
import org.bigNumber.interfaces.MathematicalMethods;
import org.bigNumber.interfaces.UtilityMethods;
import org.bigNumber.parents.StaticMethods;

/**
 * This class makes BigNumber type objects
 * @author <a href="mailto:shuklaalok7@gmail.com">Nishi Inc.</a>
 * @since August 7, 2013, v0.1.0
 */
public class BigNumber extends StaticMethods implements MathematicalMethods, UtilityMethods {

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
		this.setValue(number);
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

	@Override
	public int compareTo(BigNumber number) {
		int result = 0;

		if(!this.isFractional() && !number.isFractional()) {
			//Both are non-fractional
			result = this.getBigInteger().compareTo(number.getBigInteger());
		} else {
			this.makeFractional();
			number.makeFractional();
			result = this.getBigDecimal().compareTo(number.getBigDecimal());
			this.consolidate();
			number.consolidate();
		}
		return result;
	}

	@Override
	public int hashCode() {
	    int hash = 0;
	    List<Character> val = this.getValue();
	    for(int digit : val) {
	    	digit -= 48;
	    	if(digit>=0 && digit<=9) {
	    		hash = hash * 13 + digit;
	    	}
	    }
	    return hash;
	}
	
	@Override
	public boolean equals(Object number) {
		if(number instanceof BigNumber && this.compareTo((BigNumber) number) == 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder result 	= new StringBuilder();
		List<Character> value	= this.getValue();
		if(value != null) {
			int size = this.size();
			for(int i=0; i<size; i++) {
				result.append(value.get(i));
			}
		}
		return result.toString();
	}

	/*public String toString() {
		if(this.isFractional()) {
			return this.getBigDecimal().toPlainString();
		}
		return this.getBigInteger().toString();
	}*/

	//==================================================================================



	//================================ setValue() ======================================

	@Override
	public <E extends Comparable<E>> void setValue(E number) throws IncompatibleCharacterException {
		this.setValue(number.toString());
	}

	@Override
	public void setValue(BigNumber number) {
		try {
			this.setValue(number.getValue());
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setValue(List<Character> number) throws IncompatibleCharacterException {
		StringBuilder chars	=	new StringBuilder();
		int size			=	number.size();
		for(int i=0; i<size; i++) {
			chars.append(number.get(i));
		}
		this.setValue(chars.toString());
	}

	@Override
	public void setValue(String value) throws IncompatibleCharacterException {
		this.resetValue();
		value.replaceAll(" ", "");
		int size = value.length();
		boolean error = true;

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
				if(this.isFractional()){
					throw new IncompatibleCharacterException("From BigNumber.setValue(): value is incompatible.");
				}
				this.setFractional(true);
				this.setLocationOfDecimal(i);
			}
			if(this.isZero() && (digit > '0' && digit <= '9')) {
				this.setZero(false);
			}
			
			if(this.locationOfDecimal() == 0) {
				this.getValue().add('0');
				this.setLocationOfDecimal(1);
			}
			this.getValue().add(digit);
		}
		this.syncFromValue();
	}

	//==================================================================================


	//================================ Mathematical ====================================

	public void add(BigNumber number) {
		if(!this.isFractional() && !number.isFractional()) {
			//Both are non-fractional
			this.setBigInteger(this.getBigInteger().add(number.getBigInteger()));
		} else {
			this.makeFractional();
			number.makeFractional();
			this.setBigDecimal(this.getBigDecimal().add(number.getBigDecimal()));
			number.consolidate();
		}
		this.consolidate();
	}

	public void multiply(BigNumber number) {

		if(!this.isFractional() && !number.isFractional()) {
			//Both are non-fractional
			this.setBigInteger(this.getBigInteger().multiply(number.getBigInteger()));
		} else {
			this.makeFractional();
			number.makeFractional();
			this.setBigDecimal(this.getBigDecimal().multiply(number.getBigDecimal()));
			number.consolidate();
		}
		this.consolidate();
	}

	public void subtract(BigNumber number) {
		if(!this.isFractional() && !number.isFractional()) {
			//Both are non-fractional
			this.setBigInteger(this.getBigInteger().subtract(number.getBigInteger()));
		} else {
			this.makeFractional();
			number.makeFractional();
			this.setBigDecimal(this.getBigDecimal().subtract(number.getBigDecimal()));
			number.consolidate();
		}
		this.consolidate();
	}

	public void divide(BigNumber number) {
		if(number.isZero()) {
			throw new ArithmeticException();
		}
		
		this.makeFractional();
		number.makeFractional();
		try {
			this.setBigDecimal(this.getBigDecimal().divide(number.getBigDecimal()));
		} catch(ArithmeticException e) {
			this.consolidate();
			number.consolidate();
			if(!this.isFractional() && !number.isFractional()) {
				this.getBigInteger().divide(number.getBigInteger());
			} else {
				throw e;
			}
		}
		
		this.consolidate();
		number.consolidate();
	}

	public BigNumber[] divideAndRemainder(BigNumber number) {
		BigNumber[] result = {new BigNumber(),new BigNumber()};

		if(!this.isFractional() && !number.isFractional()) {
			// Both are non-fractional			
			BigInteger[] dummy;
			dummy = this.getBigInteger().divideAndRemainder(number.getBigInteger());
			result[0].setBigInteger(dummy[0]);
			result[1].setBigInteger(dummy[1]);
		} else {
			BigDecimal[] dummy;
			this.makeFractional();
			number.makeFractional();
			dummy = this.getBigDecimal().divideAndRemainder(number.getBigDecimal());
			result[0].setBigDecimal(dummy[0]);
			result[1].setBigDecimal(dummy[1]);
			this.consolidate();
			number.consolidate();
		}

		return result;
	}

	public void absolute() {
		/*if(this.isFractional()) {
			this.setBigDecimal(this.getBigDecimal().abs());
			this.();
		} else {
			this.setBigInteger(this.getBigInteger().abs());
			this.();
		}*/
		if(this.isNegative()) {
			this.getValue().remove(0);
			this.syncFromValue();
		}
	}

	public void modulus(BigNumber denominator) {
		this.setValue((this.divideAndRemainder(denominator))[1]);
		this.syncFromValue();
		this.absolute();
	}

	public void pow(int power) {
		if(this.isFractional()) {
			this.setBigDecimal(this.getBigDecimal().pow(power));
		} else {
			this.setBigInteger(this.getBigInteger().pow(power));
		}
	}

	//==================================================================================

	//================================= UTILITY ========================================

	public String toEngineeringString() {
		if(this.isZero()) {
			return "0";
		}
		
		StringBuilder result = new StringBuilder();

		if(this.isFractional()) {
			String decStr = this.getBigDecimal().toEngineeringString();
			String[] keys = decStr.split("E");
			if(keys.length == 2) {
				keys[1] = ((Integer)(Integer.parseInt(keys[1]) + 2)).toString();
				result.append(keys[0].charAt(0) + ".");
				if(keys[0].charAt(2) > '5' || ((keys[0].charAt(1)-'0') %2 == 0 && keys[0].charAt(2) == '5')) {
					result.append((char)(keys[0].charAt(1) + 1));
				} else {
					result.append(keys[0].charAt(1));
				}
				result.append("E" + keys[1]);
			} else {
				int size = keys[0].length();
				if(size==1) {
					result.append(keys[0] + "E0");
				} else if(size == 2) {
					result.append(keys[0].charAt(0) + "." + keys[0].charAt(1) + "E1");
				} else {
					result.append(keys[0].charAt(0) + ".");
					if(keys[0].charAt(2) > '5' || ((keys[0].charAt(1)-'0') %2 == 0 && keys[0].charAt(2) == '5')) {
						result.append((char)(keys[0].charAt(1) + 1));
					} else {
						result.append(keys[0].charAt(1));
					}
					result.append("E" + (keys[0].split("\\.")[0].length()-1));
				}
			}
			return result.toString();
		}
		// Logic in case of BigInteger

		int i=0;
		if(this.isNegative()) {
			result.append("-");
			i = 1;
		}

		result.append(this.charAt(i));
		if(i+1 < this.size()) {
			if(i+2 < this.size()) {
				result.append('.');
				if(this.charAt(i+2) > '5' || ((this.charAt(i+1)-'0') %2 == 0 && this.charAt(i+2) == '5')) {
					result.append((char)(this.charAt(i+1) + 1));
				} else {
					result.append(this.charAt(i+1));
				}
			}
		}
		result.append('E' + (this.size() - i - 1));
		return result.toString();
	}

	public List<Character> getValue() {
		if(value == null) {
			value	=	new ArrayList<Character>();
		}
		return value;
	}

	public BigDecimal toBigDecimal() {
		if(this.isFractional()) {
			return this.getBigDecimal();
		} else {
			this.makeFractional();
			BigDecimal result = new BigDecimal(this.toString());
			this.consolidate();
			return result;
		}
	}

	public BigInteger toBigInteger() {
		if(!this.isFractional()) {
			return this.getBigInteger();
		} else {
			BigNumber result = new BigNumber(this);
			result.convertToInteger();
			return result.getBigInteger();
		}
	}

	public Integer toInteger() {
		Integer result;
		try {
			result = new Integer(this.toString());
		} catch(NumberFormatException e) {
			//Trying to put first 19 digits
			try {
				result = new Integer(this.toString().substring(0, 19));
			} catch(Exception ex) {
				//Trying for first 10 digits
				result = new Integer(this.toString().substring(0, 10));
			}
		}
		return result;
	}

	public Double toDouble() {
		Double result;
		try {
			result = new Double(this.toString());
		} catch(NumberFormatException e) {
			//Trying to put first 19 digits
			try {
				result = new Double(this.toString().substring(0, 19));
			} catch(Exception ex) {
				//Trying for first 10 digits
				result = new Double(this.toString().substring(0, 10));
			}
		}
		return result;
	}

	public Integer size() {
		return this.getValue().size();
	}

	public String getValueBetween(int startIndex, int endIndex) {
		if(startIndex > this.size() || endIndex > this.size())
			throw new ArrayIndexOutOfBoundsException();
		else if(startIndex > endIndex) {
			int tmp 	= 	endIndex;
			endIndex	=	startIndex;
			startIndex	=	tmp;
		}

		StringBuilder result	=	new StringBuilder();
		for(int i=startIndex; i<=endIndex; i++) {
			result.append(this.getValue().get(i));
		}
		return result.toString();
	}

	public String getValueTill(int index) {
		return this.getValueBetween(0, index);
	}

	public Character charAt(int index) {
		return this.getValue().get(index);
	}

	public void convertToInteger() {
		if(!this.isFractional())
			return;
		else {
			for(int i=this.locationOfDecimal(); i<this.size();) {
				this.getValue().remove(i);
			}
			this.syncFromValue();
		}
	}

	public void makeNegative() {
		try {
			this.putAtFirst('-');
		} catch (IncompatibleCharacterException e) {
			e.showMsg();
		}
	}

	public void roundOff(Integer numberOfDigitsAfterDecimal) {
		if(!this.isFractional())
			return;
		if(numberOfDigitsAfterDecimal == 0) {
			this.convertToInteger();
			return;
		}
		int j = this.locationOfDecimal();
		int i = j+1;
		if((this.size()-i) < numberOfDigitsAfterDecimal) {
			return;
		}
		else {
			j += numberOfDigitsAfterDecimal;
			i = j+1;
			if(i>=this.size()) {
				return;
			}
			if((this.charAt(i) < '5') || (this.charAt(i) == '5' && this.charAt(j)%2 == 0)) {
				return;
			}

			for(;this.charAt(j) == '9';j--) {
				try {
					this.modify(j, '0');
				} catch (IncompatibleCharacterException e) {
					e.printStackTrace();
				}
			}

			try {
				this.modify(j, this.charAt(j) + 1 - '0');
			} catch (NumberFormatException | IncompatibleCharacterException e) {
				e.printStackTrace();
			}

			while(i<this.size()) {
				this.getValue().remove(i);
			}
		}
	}

	public void roundOff() {
		this.roundOff(Constants.DEFAULT_ROUND_OFF_DIGITS);
	}

	public void modify(int index, int newDigit) throws IncompatibleCharacterException {
		if(index == 0) {
			this.getValue().remove(0);
			this.putAtFirst(newDigit);
			return;
		}
		
		if(newDigit>9 || newDigit<0) {
			throw new IncompatibleCharacterException("From BigNumber.modify(): newDIgit is incompatible.");
		}
		this.getValue().add(index, (char)(newDigit + '0'));
		this.getValue().remove(index+1);
	}

	public void modify(int index, char newDigit) throws IncompatibleCharacterException {
		if(index == 0) {
			this.getValue().remove(0);
			this.putAtFirst(newDigit);
			return;
		}
		
		if(newDigit == '.') {
			if(!this.isFractional()) {
				this.getValue().add(index, newDigit);
				this.getValue().remove(index+1);
			} else if(this.getValue().get(index) == '.') {
				return;
			} else {
				throw new IncompatibleCharacterException("From BigNumber.modify(): newDIgit is incompatible.");
			}
		} else if(newDigit>='0' && newDigit<='9') {
			this.modify(index, newDigit-'0');
		} else {
			throw new IncompatibleCharacterException("From BigNumber.modify(): newDIgit is incompatible.");
		}
	}

	public void insert(int index, char digit) throws IncompatibleCharacterException {
		if(index == 0) {
			this.putAtFirst(digit);
			return;
		}
		
		if(digit == '.') {
			if(!this.isFractional()) {
				this.getValue().add(index, digit);
				this.syncFromValue();
			} else if(this.getValue().get(index) == '.') {
				return;
			} else {
				throw new IncompatibleCharacterException("From BigNumber.insert(): newDIgit is incompatible.");
			}
		} else if(digit>='0' && digit<='9') {
			this.insert(index, digit-'0');
		} else {
			throw new IncompatibleCharacterException("From BigNumber.insert(): newDIgit is incompatible.");
		}
	}

	public void insert(int index, int digit) throws IncompatibleCharacterException {
		if(digit>9 || digit<0) {
			throw new IncompatibleCharacterException("From BigNumber.insert(): newDIgit is incompatible.");
		}
		this.getValue().add(index, (char)(digit + '0'));
		this.syncFromValue();
	}

	public void putAtFirst(int digit) throws IncompatibleCharacterException {
		if(digit>9 || digit<0) {
			throw new IncompatibleCharacterException("From BigNumber.insert(): newDIgit is incompatible.");
		}
		List<Character> dig = new ArrayList<Character>();
		dig.add((char) (digit + '0'));
		dig.addAll(this.getValue());
		this.setValue(dig);
	}

	public void putAtFirst(char character) throws IncompatibleCharacterException {
		List<Character> dig = new ArrayList<Character>();
		dig.add(character);
		dig.addAll(this.getValue());
		this.setValue(dig);
	}

	public void reverse() {
		int size  = this.size();
		int limit = 0;
		if(this.isNegative()) {
			limit = 1;
		}
		
		for(int count=0; count<=size-limit-1; count++) {
			if(this.charAt(size-1) == '.') {
				this.getValue().add(count+limit, '.');
				this.getValue().remove(size);
				continue;
			}
			try {
				this.insert(count+limit, this.charAt(size-1));
			} catch (IncompatibleCharacterException e) {
				e.showMsg();
			}
			this.getValue().remove(size);
		}
		
		this.syncFromValue();
	}

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

		if(this.charAt(this.size()-1) == '.') {
			this.getValue().remove(this.size()-1);
		}
		
		if(this.size() == 0) {
			try {
				this.setValue(0);
			} catch (IncompatibleCharacterException e) {
				e.printStackTrace();
			}
		}

		this.syncFromValue();
	}

	public void append(BigNumber number) throws IncompatibleCharacterException {
		if(this.isFractional() && number.isFractional()) {
			throw new IncompatibleCharacterException("From BigNumber.append(): A number cannot have two decimal points.");
		}
		if(number.isNegative()) {
			throw new IncompatibleCharacterException("From BigNumber.append(): Second number should not be negative.");
		}
		this.getValue().addAll(number.getValue());
		this.syncFromValue();
	}

	public void append (int number) throws IncompatibleCharacterException {
		if(number < 0) {
			throw new IncompatibleCharacterException("From BigNumber.append(): number is incompatible.");
		}
		ArrayList<Character> num = new ArrayList<Character>();
		for(; number>10; number /= 10) {
			num.add(0, (char)(number%10 + '0'));
		}
		this.getValue().addAll(num);
		this.syncFromValue();
	}

	public void makeFractional() {
		if(this.isFractional()) {
			return;
		}

		try {
			this.setValue(this.toString() + ".00");
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}
	}


	//==================================================================================

	//========================== PRIVATE HELPER METHODS ================================

	protected void syncFromValue() {
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
		this.setLocationOfDecimal(-1);
		this.setFractional(false);
		
		int i=0;
		if(this.isNegative()) {
			i=1;
		}

		for(; i<val.size(); i++){
			char digit = val.get(i);
			if(digit != '.') {
				if(digit > '0' && digit < '9') {
					this.setZero(false);
				}
			} else {
				this.setLocationOfDecimal(i);
				this.setFractional(true);
			}
		}

		String valueStr = this.toString();
		if(this.isFractional()) {
			this.bigDecimal = new BigDecimal(valueStr);
		} else {
			this.bigInteger = new BigInteger(valueStr);
		}
	}

	protected void syncFromDecimal() {
		try {
			this.setValue(this.getBigDecimal());
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}
	}

	protected void syncFromInteger() {
		try {
			this.setValue(this.getBigInteger());
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}
	}

	private void resetValue() {
		this.value = null;
		this.setFractional(false);
		this.setZero(true);
		this.setNegative(false);
	}

	//==================================================================================


	/*==================================================================================
	 *                               GETTERS/SETTERS
	 *==================================================================================
	 */

	@Override
	public boolean isNegative() {
		return isNegative;
	}

	private void setNegative(boolean isNegative) {
		this.isNegative = isNegative;
	}

	@Override
	public boolean isFractional() {
		return isFractional;
	}

	private void setFractional(boolean isFraction) {
		this.isFractional = isFraction;
		if(!isFraction) {
			this.setLocationOfDecimal(-1);
		}
	}

	@Override
	public int locationOfDecimal() {
		if(!this.isFractional())
			return -1;
		return locationOfDecimal;
	}

	private void setLocationOfDecimal(Integer locationOfDecimal) {
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
		this.syncFromInteger();
	}

	private BigDecimal getBigDecimal() {
		if(bigDecimal == null) {
			this.setBigDecimal(new BigDecimal(0));
		}
		return bigDecimal;
	}

	private void setBigDecimal(BigDecimal bigDecimal) {
		this.bigDecimal = bigDecimal;
		this.syncFromDecimal();
	}

	@Override
	public boolean isZero() {
		return isZero;
	}

	private void setZero(boolean isZero) {
		this.isZero = isZero;
	}
	
	/*==========================================================
	 * 						Public Static Methods
	 * =========================================================
	 */
	
	/**
	 * Same as sum()
	 * @param numbers BigNumber numbers
	 * @return A BigNumber type variable containing result of arithmetic addition of the provided numbers
	 */
	public static BigNumber add(BigNumber... numbers) {
		int total  = numbers.length;

		BigNumber result = new BigNumber(numbers[0]);

		for(int i=1; i<total; i++) {
			result = addTwo(result, numbers[i]);
		}
		return result;
	}

	/**
	 * @param firstNumber
	 * @param secondNumber
	 * @return
	 */
	private static BigNumber addTwo(BigNumber firstNumber, BigNumber secondNumber) {
		BigNumber result = new BigNumber();

		if(!firstNumber.isFractional() && !secondNumber.isFractional()) {
			//Both are non-fractional
			result.setBigInteger(firstNumber.getBigInteger().add(secondNumber.getBigInteger()));
		} else {
			firstNumber.makeFractional();
			secondNumber.makeFractional();
			result.setBigDecimal(firstNumber.getBigDecimal().add(secondNumber.getBigDecimal()));
		}

		return result;
	}
	
	/**
	 * Same as sub()
	 * @param firstNumber
	 * @param secondNumber
	 * @return A BigNumber type variable containing firstNumber - secondNumber
	 * @author Nishi Inc.
	 * @since v1.0.0
	 */
	public static BigNumber subtract(BigNumber firstNumber, BigNumber secondNumber) {
		BigNumber result = new BigNumber();

		if(firstNumber.isFractional() && secondNumber.isFractional()) {
			//Both are non-fractional
			result.setBigInteger(firstNumber.getBigInteger().subtract(secondNumber.getBigInteger()));
		} else {
			firstNumber.makeFractional();
			secondNumber.makeFractional();
			result.setBigDecimal(firstNumber.getBigDecimal().subtract(secondNumber.getBigDecimal()));
		}

		return result;
	}
	
	/**
	 * @param numbers BigNumber numbers
	 * @return A BigNumber number containing product of the numbers provided
	 * @author Nishi Inc.
	 * @since v1.1.0
	 */
	public static BigNumber multiply(BigNumber... numbers) {
		int total  = numbers.length;
		BigNumber result = new BigNumber(numbers[0]);

		for(int i=1; i<total; i++) {
			result = BigNumber.multiplyTwo(result, numbers[i]);
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

		if(!firstNumber.isFractional() && !secondNumber.isFractional()) {
			// Both are non-fractional
			result.setBigInteger(firstNumber.getBigInteger().multiply(secondNumber.getBigInteger()));
		} else {
			firstNumber.makeFractional();
			secondNumber.makeFractional();
			result.setBigDecimal(firstNumber.getBigDecimal().multiply(secondNumber.getBigDecimal()));
		}
		
		result.consolidate();
		return result;
	}

	/**
	 * @author Nishi Inc.
	 * @since v1.0.0
	 * @throws ArithmeticException When denominator is zero or when division is giving endless number
	 * @param numerator or dividend
	 * @param denominator or divisor
	 * @return A BigNumber type variable containing result of arithmetic division of the provided numerator by the provided denominator
	 */
	public static BigNumber divide(BigNumber numerator, BigNumber denominator) throws ArithmeticException {
		if(denominator.isZero()) {
			throw new ArithmeticException();
		}
		
		BigNumber result	=	new BigNumber();
		numerator.makeFractional();
		denominator.makeFractional();
		try {
			result.setBigDecimal(numerator.getBigDecimal().divide(denominator.getBigDecimal()));
		} catch(ArithmeticException e) {
			numerator.consolidate();
			denominator.consolidate();
			if(!numerator.isFractional() && !denominator.isFractional()) {
				numerator.getBigInteger().divide(denominator.getBigInteger());
			} else {
				throw e;
			}
		}
		
		numerator.consolidate();
		denominator.consolidate();
		return result;
	}
	
	/**
	 * Returns absolute value of the given number
	 * @author Nishi Inc.
	 * @since v1.1.0
	 * @param number
	 * @return A BigNumber type variable containing absolute value of the given BigNumber number
	 */
	public static BigNumber absolute(BigNumber number) {
		if(!number.isNegative()) {
			return number;
		}
		BigNumber result = new BigNumber(number);
		result.getValue().remove(0);
		result.syncFromValue();
		return result;
	}
	
}
