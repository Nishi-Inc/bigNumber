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

package org.bigNumber.models;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.bigNumber.common.services.ErrorMessages;
import org.bigNumber.common.services.GlobalConstants;
import org.bigNumber.common.interfaces.MathematicalMethods;
import org.bigNumber.common.interfaces.UtilityMethods;
import org.bigNumber.common.services.StringUtils;

/**
 * This class makes BigNumber type objects
 * @author <a href="mailto:shuklaalok7@gmail.com">Nishi Inc.</a>
 * @since August 7, 2013, v0.1.0
 */
public class BigNumber extends Root {

    private static final Integer	DEFAULT_ROUND_OFF_DIGITS = 2;

    private static final String FROM            = "From BigNumber.";
    private static final String APPEND          = "append";
    private static final String INSERT          = "insert";
    private static final String MODIFY          = "modify";
    private static final String SET_VALUE       = "setValue";
    private static final String PUT_AT_FIRST    = "putAtFirst";

    private List<Character>		value;
	private BigInteger			bigInteger;
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
        this.setValue(0);
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
		this.setValue(number.toPlainString());
	}

	/**
	 * @author Nishi Inc.
	 * @param number A BigInteger
	 * @since v1.0.0
	 */
	public BigNumber(BigInteger number) {
		this.setValue(number.toString());
	}

	/**
	 * @author Nishi Inc.
	 * @since v1.0.0
	 * @param number
	 */
	public BigNumber(String number) {
		this.setValue(number);
	}

	/**
	 * @author Nishi Inc.
	 * @since v1.0.0
	 * @param number
	 */
	public BigNumber(List<Character> number) {
		this.setValue(number);
	}

	/**
	 * @author Nishi Inc.
	 * @since v1.0.0
	 * @param number
	 */
	public BigNumber(Integer number) {
		this.setValue(number);
	}

	/**
	 * @author Nishi Inc.
	 * @since v1.0.0
	 * @param number
	 */
	public BigNumber(Float number) {
		this.setValue(number);
	}

	/**
	 * @author Nishi Inc.
	 * @since v1.0.0
	 * @param number
	 */
	public BigNumber(Double number) {
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
        final int PRIME = 13;
	    int hash = 0;
	    List<Character> val = this.getValue();
	    for(int digit : val) {
	    	digit -= GlobalConstants.ASCII_ZERO;
	    	if(digit>=0 && digit<=9) {
	    		hash = hash * PRIME + digit;
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
	public <E extends Comparable<E>> void setValue(E number) {
		this.setValue(number.toString());
	}

	@Override
	public void setValue(BigNumber number) {
		this.setValue(number.getValue());
	}

	@Override
	public void setValue(List<Character> number) {
		StringBuilder chars	=	new StringBuilder();
		int size			=	number.size();
		for(int i=0; i<size; i++) {
			chars.append(number.get(i));
		}
		this.setValue(chars.toString());
	}

	@Override
	public void setValue(String value) {
        String methodName = BigNumber.SET_VALUE;
		this.resetValue();
		value.replaceAll(GlobalConstants.SPACE, GlobalConstants.BLANK);
		int size = value.length();
		boolean error = true;

		for(int i=0; i<size; i++) {
			Character digit = value.charAt(i);
			/*if(digit == null) {
				break;
			}*/
			if(digit <= GlobalConstants.NINE && digit >= GlobalConstants.ZERO) {
				error = false;
			} else if(digit == GlobalConstants.MINUS) {
				error = false;
			} else if(digit == GlobalConstants.DECIMAL_POINT) {
				error = false;
			}
			if(error) {
				throw new IllegalArgumentException(BigNumber.FROM + methodName + ErrorMessages.INCOMPATIBLE_VALUE_MSG.getValue());
			}

			if(digit == GlobalConstants.DECIMAL_POINT) {
				if(this.isFractional()){
					throw new IllegalArgumentException(BigNumber.FROM + methodName + ErrorMessages.INCOMPATIBLE_VALUE_MSG.getValue());
				}
				this.setFractional(true);
				this.setLocationOfDecimal(i);
			}
			if(this.isZero() && (digit > GlobalConstants.ZERO && digit <= GlobalConstants.NINE)) {
				this.setZero(false);
			}
			
			if(this.locationOfDecimal() == 0) {
				this.getValue().add(GlobalConstants.ZERO);
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
			return GlobalConstants.ZERO_STR;
		}
		
		StringBuilder result = new StringBuilder();

		if(this.isFractional()) {
			String decStr = this.getBigDecimal().toEngineeringString();
			String[] keys = decStr.split(GlobalConstants.ENGINEERING_SYMBOL);
			if(keys.length == 2) {
				keys[1] = ((Integer)(Integer.parseInt(keys[1]) + 2)).toString();
				result.append(keys[0].charAt(0) + GlobalConstants.DECIMAL_POINT);
				if(keys[0].charAt(2) > GlobalConstants.FIVE || ((keys[0].charAt(1)-GlobalConstants.ZERO) %2 == 0 && keys[0].charAt(2) == GlobalConstants.FIVE)) {
					result.append((char)(keys[0].charAt(1) + 1));
				} else {
					result.append(keys[0].charAt(1));
				}
				result.append(GlobalConstants.ENGINEERING_SYMBOL + keys[1]);
			} else {
				int size = keys[0].length();
				if(size==1) {
					result.append(keys[0] + GlobalConstants.ENGINEERING_SYMBOL + GlobalConstants.ZERO_STR);
				} else if(size == 2) {
					result.append(keys[0].charAt(0) + GlobalConstants.DECIMAL_POINT_STR + keys[0].charAt(1) + GlobalConstants.ENGINEERING_SYMBOL + GlobalConstants.ONE_STR);
				} else {
					result.append(keys[0].charAt(0) + GlobalConstants.DECIMAL_POINT_STR);
					if(keys[0].charAt(2) > GlobalConstants.FIVE || ((keys[0].charAt(1)-GlobalConstants.ZERO) %2 == 0 && keys[0].charAt(2) == GlobalConstants.FIVE)) {
						result.append((char)(keys[0].charAt(1) + 1));
					} else {
						result.append(keys[0].charAt(1));
					}
					result.append(GlobalConstants.ENGINEERING_SYMBOL + (keys[0].split("\\.")[0].length()-1));
				}
			}
			return result.toString();
		}
		// Logic in case of BigInteger

		int i=0;
		if(this.isNegative()) {
			result.append(GlobalConstants.MINUS_STR);
			i = 1;
		}

		result.append(this.charAt(i));
		if(i+1 < this.size()) {
			if(i+2 < this.size()) {
				result.append('.');
				if(this.charAt(i+2) > GlobalConstants.FIVE || ((this.charAt(i+1)-GlobalConstants.ZERO) %2 == 0 && this.charAt(i+2) == GlobalConstants.FIVE)) {
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
		this.putAtFirst(GlobalConstants.MINUS);
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
			if((this.charAt(i) < GlobalConstants.FIVE) || (this.charAt(i) == GlobalConstants.FIVE && this.charAt(j)%2 == 0)) {
				return;
			}

			for(;this.charAt(j) == GlobalConstants.NINE; j--) {
				this.modify(j, GlobalConstants.ZERO);
			}

			this.modify(j, this.charAt(j) + 1 - GlobalConstants.ZERO);

			while(i<this.size()) {
				this.getValue().remove(i);
			}
		}
	}

	public void roundOff() {
		this.roundOff(BigNumber.DEFAULT_ROUND_OFF_DIGITS);
	}

	public void modify(int index, int newDigit) {
        String methodName = BigNumber.MODIFY;
		if(index == 0) {
			this.getValue().remove(0);
			this.putAtFirst(newDigit);
			return;
		}
		
		if(newDigit>9 || newDigit<0) {
			throw new IllegalArgumentException(BigNumber.FROM + methodName + ErrorMessages.NEW_DIGIT_INCOMPATIBLE_MSG.getValue());
		}
		this.getValue().add(index, (char)(newDigit + GlobalConstants.ZERO));
		this.getValue().remove(index+1);
	}

	public void modify(int index, char newDigit) {
        String methodName = BigNumber.MODIFY;
		if(index == 0) {
			this.getValue().remove(0);
			this.putAtFirst(newDigit);
			return;
		}
		
		if(newDigit == GlobalConstants.DECIMAL_POINT) {
			if(!this.isFractional()) {
				this.getValue().add(index, newDigit);
				this.getValue().remove(index+1);
			} else if(this.getValue().get(index) == GlobalConstants.DECIMAL_POINT) {
				return;
			} else {
				throw new IllegalArgumentException(BigNumber.FROM + methodName + ErrorMessages.NEW_DIGIT_INCOMPATIBLE_MSG.getValue());
			}
		} else if(newDigit>=GlobalConstants.ZERO && newDigit<=GlobalConstants.NINE) {
			this.modify(index, newDigit-GlobalConstants.ZERO);
		} else {
			throw new IllegalArgumentException(BigNumber.FROM + methodName + ErrorMessages.NEW_DIGIT_INCOMPATIBLE_MSG.getValue());
		}
	}

	public void insert(int index, char digit) {
        String methodName = BigNumber.INSERT;
		if(index == 0) {
			this.putAtFirst(digit);
			return;
		}
		
		if(digit == GlobalConstants.DECIMAL_POINT) {
			if(!this.isFractional()) {
				this.getValue().add(index, digit);
				this.syncFromValue();
			} else if(this.getValue().get(index) == GlobalConstants.DECIMAL_POINT) {
				return;
			} else {
				throw new IllegalArgumentException(BigNumber.FROM + methodName + ErrorMessages.NEW_DIGIT_INCOMPATIBLE_MSG.getValue());
			}
		} else if(digit>=GlobalConstants.ZERO && digit<=GlobalConstants.NINE) {
			this.insert(index, digit-GlobalConstants.ZERO);
		} else {
			throw new IllegalArgumentException(BigNumber.FROM + methodName + ErrorMessages.NEW_DIGIT_INCOMPATIBLE_MSG.getValue());
		}
	}

	public void insert(int index, int digit) {
        String methodName = BigNumber.INSERT;
		if(digit>9 || digit<0) {
			throw new IllegalArgumentException(BigNumber.FROM + methodName + ErrorMessages.NEW_DIGIT_INCOMPATIBLE_MSG.getValue());
		}
		this.getValue().add(index, (char)(digit + GlobalConstants.ZERO));
		this.syncFromValue();
	}

	public void putAtFirst(int digit) {
        String methodName = BigNumber.PUT_AT_FIRST;
		if(digit>9 || digit<0) {
			throw new IllegalArgumentException(BigNumber.FROM + methodName + ErrorMessages.NEW_DIGIT_INCOMPATIBLE_MSG.getValue());
		}
		List<Character> dig = new ArrayList<Character>();
		dig.add((char) (digit + GlobalConstants.ZERO));
		dig.addAll(this.getValue());
		this.setValue(dig);
	}

	public void putAtFirst(char character) {
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
			if(this.charAt(size-1) == GlobalConstants.DECIMAL_POINT) {
				this.getValue().add(count+limit, GlobalConstants.DECIMAL_POINT);
				this.getValue().remove(size);
				continue;
			}

            this.insert(count+limit, this.charAt(size-1));
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

		for(;this.charAt(startIndex) == GlobalConstants.ZERO; this.getValue().remove(startIndex));

		if(this.isFractional()) {
			// Consolidate from other end as well
			for(int i=this.size()-1; this.charAt(i) == GlobalConstants.ZERO; i=this.size()-1) {
				this.getValue().remove(i);
			}
		}

		if(this.charAt(this.size()-1) == GlobalConstants.DECIMAL_POINT) {
			this.getValue().remove(this.size()-1);
		}
		
		if(this.size() == 0) {
            this.setValue(0);
		}

		this.syncFromValue();
	}

	public void append(BigNumber number) {
        String methodName = BigNumber.APPEND;
		if(this.isFractional() && number.isFractional()) {
			throw new IllegalArgumentException(BigNumber.FROM + methodName + ErrorMessages.TWO_DECIMAL_POINTS_MSG.getValue());
		}
		if(number.isNegative()) {
			throw new IllegalArgumentException(BigNumber.FROM + methodName + ErrorMessages.SECOND_NUMBER_NEGATIVE_MSG.getValue());
		}
		this.getValue().addAll(number.getValue());
		this.syncFromValue();
	}

	public void append (int number) {
        String methodName = BigNumber.APPEND;
		if(number < 0) {
			throw new IllegalArgumentException(BigNumber.FROM + methodName + ErrorMessages.INCOMPATIBLE_VALUE_MSG.getValue());
		}
		ArrayList<Character> num = new ArrayList<Character>();
		for(; number>10; number /= 10) {
			num.add(0, (char)(number%10 + GlobalConstants.ZERO));
		}
		this.getValue().addAll(num);
		this.syncFromValue();
	}

	public void makeFractional() {
		if(this.isFractional()) {
			return;
		}

        this.setValue(this.toString() + GlobalConstants.DECIMAL_POINT + GlobalConstants.ZERO_STR + GlobalConstants.ZERO_STR);
	}

    @Override
    public BigNumber factorial() {
        if((this.compareTo(new BigNumber()) == 0) || (this.compareTo(GlobalConstants.UNITY) == 0)) {
            return GlobalConstants.UNITY;
        }

        BigNumber result = new BigNumber();
        BigNumber i = new BigNumber();
        result.setValue(this);

        for(i.setValue(subtract(this, GlobalConstants.UNITY)); i.compareTo(GlobalConstants.UNITY) != 0; i.setValue(subtract(i, GlobalConstants.UNITY))) {
            result.multiply(i);
        }

        result.consolidate();
        return result;
    }

    @Override
    public String detail() {
        StringBuilder bigNum = new StringBuilder(GlobalConstants.LEFT_BRACE);

        if(this.isZero()) {
            bigNum.append("value" + GlobalConstants.COLON + GlobalConstants.ZERO_STR);
            bigNum.append(GlobalConstants.COMMA + "isZero" + GlobalConstants.COLON + true);
        } else {
            bigNum.append("value" + GlobalConstants.COLON + StringUtils.combine(this.getValue(), GlobalConstants.BLANK));
            bigNum.append(GlobalConstants.COMMA + "isZero" + GlobalConstants.COLON + false);
        }

        if(this.isFractional()) {
            bigNum.append(GlobalConstants.COMMA + "isFractional" + GlobalConstants.COLON + true);
            bigNum.append(GlobalConstants.COMMA + "bigDecimal" + GlobalConstants.COLON + this.getBigDecimal().toString());
            bigNum.append(GlobalConstants.COMMA + "locationOfDecimal" + GlobalConstants.COLON + this.locationOfDecimal());
        } else {
            bigNum.append(GlobalConstants.COMMA + "isFractional" + GlobalConstants.COLON + false);
            bigNum.append(GlobalConstants.COMMA + "bigInteger" + GlobalConstants.COLON + this.getBigInteger().toString());
        }

        if(this.isNegative()) {
            bigNum.append(GlobalConstants.COMMA + "isNegative" + GlobalConstants.COLON + true);
        } else {
            bigNum.append(GlobalConstants.COMMA + "isNegative" + GlobalConstants.COLON + false);
        }

        bigNum.append(GlobalConstants.RIGHT_BRACE);

        return bigNum.toString();
    }


	//==================================================================================

	//========================== PRIVATE HELPER METHODS ================================

	protected void syncFromValue() {
		// set all variables and values as per value in the value of the BigNumber
		// Variables to set isZero, isNegative, locationOfDecimal, isFractional, bigDecimal or bigInteger
		List<Character> val = null;
		val = this.getValue();

		//this.setSize(val.size());

		if(val.get(0) == GlobalConstants.MINUS) {
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
			if(digit != GlobalConstants.DECIMAL_POINT) {
				if(digit > GlobalConstants.ZERO && digit < GlobalConstants.NINE) {
					this.setZero(false);
				}
			} else {
				this.setLocationOfDecimal(i);
				this.setFractional(true);
			}
            if(this.isFractional() && !this.isZero()) {
                break;
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
		this.setValue(this.getBigDecimal());
	}

	protected void syncFromInteger() {
		this.setValue(this.getBigInteger());
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
			this.setBigInteger(new BigInteger(GlobalConstants.ZERO_STR));
		}
		return bigInteger;
	}

	protected void setBigInteger(BigInteger bigInteger) {
		this.bigInteger = bigInteger;
		this.syncFromInteger();
	}

	@Override
	public boolean isZero() {
		return isZero;
	}

	private void setZero(boolean isZero) {
		this.isZero = isZero;
	}

	
}
