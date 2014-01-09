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

package org.nishi.bigNumber.models;

import org.nishi.bigNumber.common.services.BigNumberUtils;
import org.nishi.bigNumber.common.services.ErrorMessages;
import org.nishi.bigNumber.helper.GlobalConstants;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * This class makes BigNumber type objects
 * @author <a href="mailto:shuklaalok7@gmail.com">Nishi Inc.</a>
 * @since August 7, 2013, v0.1.0
 */
public class BigNumber extends BigNumberParent {

    private static final BigNumber one   = new BigNumber(1);
    private static final BigNumber UNITY = one;

    private static final String     FROM                     = "From BigNumber.";
    private static final String     APPEND                   = "append";
    private static final String     INSERT                   = "insert";
    private static final String     MODIFY                   = "modify";
    private static final String     SET_VALUE                = "setValue";
    private static final String     PUT_AT_FIRST             = "putAtFirst";

	//____________________________________________________________________________________
	//********************************* CONSTRUCTORS *************************************
	//____________________________________________________________________________________


	/**
	 * Constructs a BigNumber type number with default value 0
	 * @since v0.1.0
	 */
	public BigNumber(){
        this.defaultBigNumberMathContext = new BigNumberMathContext();
        this.setValue(0);
	}

	/**
	 * Create a copy from the given BigNumber
	 * @param number A BigNumber
	 * @since v0.1.0
	 */
	public BigNumber(BigNumber number) {
		this.setValue(number);
	}

	/**
	 * @param number
	 * @since v1.0.0
	 */
	public BigNumber(BigDecimal number) {
		this.setValue(number.toPlainString());
	}

	/**
	 * @param number A BigInteger
	 * @since v1.0.0
	 */
	public BigNumber(BigInteger number) {
		this.setValue(number.toString());
	}

	/**
	 * @since v1.0.0
	 * @param number
	 */
	public BigNumber(String number) {
		this.setValue(number);
	}

	/**
	 * @since v1.0.0
	 * @param number
	 */
	public BigNumber(List<Character> number) {
		this.setValue(number);
	}

	/**
	 * @since v1.0.0
	 * @param number
	 */
	public BigNumber(Integer number) {
		this.setValue(number);
	}

	/**
	 * @since v1.0.0
	 * @param number
	 */
	public BigNumber(Float number) {
		this.setValue(number);
	}

	/**
	 * @since v1.0.0
	 * @param number
	 */
	public BigNumber(Double number) {
		this.setValue(number);
	}

	//____________________________________________________________________________________
	//********************************* METHODS ******************************************
	//____________________________________________________________________________________



	//================================ setValue() ======================================

    /**
     * It puts the provided number in the BigNumber variable
     * toString() method on the provided number will be called hence do not use primitive types
     * @param number
     * As String, Integer, Float, Double, Long etc.
     * @since August 6, 2013, v0.1.0
     */
	public <E extends Comparable<E>> void setValue(E number) {
		this.setValue(number.toString());
	}

    /**
     * It puts the provided number in the BigNumber variable
     * @param number BigNumber number
     * @since v0.1.0
     */
	public void setValue(BigNumber number) {
		this.setValue(number.getValue());
	}

    /**
     * <ul><li>No character accept '0' to '9', '.' and '-' is allowed.</li>
     * <li>No two '.' are allowed.</li>
     * <li>Spaces (' ' or " ") in the provided String are removed before setting the value so no problem with spaces.</li>
     * <li>'-' is allowed only as first character</li></ul>
     * @param number
     */
	public void setValue(List<Character> number) {
		StringBuilder chars	=	new StringBuilder();
		int size			=	number.size();
		for(int i=0; i<size; i++) {
			chars.append(number.get(i));
		}
		this.setValue(chars.toString());
	}

    /**
     * It puts the provided number in the BigNumber variable
     * @param value As String which may contain only numbers 0 to 9, a leading -ve sign '-' and a decimal point
     * @since August 6, 2013, v0.1.0
     */
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

	public BigNumber add(BigNumber number) {
        BigNumber result = new BigNumber(this);
		if(!result.isFractional() && !number.isFractional()) {
			//Both are non-fractional
			result.setBigInteger(result.getBigInteger().add(number.getBigInteger()));
		} else {
			result = result.makeFractional();
			number = number.makeFractional();
			result.setBigDecimal(result.getBigDecimal().add(number.getBigDecimal()));
			// number = number.consolidate();
		}
		return result.consolidate();
	}

    @Override
	public BigNumber multiply(BigNumber number) {
        BigNumber result = new BigNumber();
		if(!this.isFractional() && !number.isFractional()) {
			//Both are non-fractional
			result.setBigInteger(this.getBigInteger().multiply(number.getBigInteger()));
		} else {
			result = this.makeFractional();
			number = number.makeFractional();
			result.setBigDecimal(result.getBigDecimal().multiply(number.getBigDecimal()));
			// number.consolidate();
		}
		return result.consolidate();
	}

    @Override
	public BigNumber subtract(BigNumber number) {
        BigNumber result = new BigNumber();
        if(!this.isFractional() && !number.isFractional()) {
			//Both are non-fractional
			result.setBigInteger(this.getBigInteger().subtract(number.getBigInteger()));
		} else {
			result = this.makeFractional();
			number = number.makeFractional();
			result.setBigDecimal(result.getBigDecimal().subtract(number.getBigDecimal()));
			// number = number.consolidate();
		}
		return result.consolidate();
	}

    @Override
	public BigNumber divide(BigNumber number) {
		if(number.isZero()) {
			throw new ArithmeticException();
		}
		
		BigNumber result = new BigNumber(this.makeFractional());
		number = number.makeFractional();
		try {
			result.setBigDecimal(result.getBigDecimal().divide(number.getBigDecimal()));
		} catch(ArithmeticException e) {
			result = result.consolidate();
			number = number.consolidate();
			if(!result.isFractional() && !number.isFractional()) {
				result.setBigInteger(result.getBigInteger().divide(number.getBigInteger()));
			} else {
				throw e;
			}
		}

		return result.consolidate();
	}

	public BigNumber[] divideAndRemainder(BigNumber number) {
		BigNumber[] result = {new BigNumber(), new BigNumber()};

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

    @Override
	public BigNumber absolute() {
		if(this.isNegative()) {
            BigNumber result = new BigNumber(this);
			result.getValue().remove(0);
			result.syncFromValue();
            return result;
		} else {
            return this;
        }
	}

    @Override
	public BigNumber modulus(BigNumber denominator) {
        return new BigNumber(this.divideAndRemainder(denominator)[1]).absolute();
	}

    @Override
	public BigNumber pow(int power) {
        if(this.isFractional()) {
			return new BigNumber(this.getBigDecimal().pow(power));
		} else {
            return new BigNumber(this.getBigInteger().pow(power));
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

    @Override
	public Integer size() {
		return this.getValue().size();
	}

    @Override
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

    @Override
	public String getValueTill(int index) {
		return this.getValueBetween(0, index);
	}

    @Override
	public Character charAt(int index) {
		return this.getValue().get(index);
	}

    @Override
	public BigNumber convertToInteger() {
		if(!this.isFractional())
			return this;
		else {
            // FIXME If this GlobalConstants.DECIMAL_POINT_STR causes any issue
			return new BigNumber(this.toString().split(GlobalConstants.DECIMAL_POINT_STR)[1]);
		}
	}

    @Override
	public BigNumber makeNegative() {
		return this.putAtFirst(GlobalConstants.MINUS);
	}

    @Override
	public BigNumber roundOff(BigNumberMathContext mathContext) {
        //return this.isFractional()? new BigNumber(this.getBigDecimal().round(BigNumberMathContext.getMathContextObject(mathContext))) : this;


        if(!this.isFractional()) {
            return this;
        }

		if(mathContext.getNumberOfDigitsAfterDecimal() == 0) {
			return new BigNumber(this.convertToInteger());
		}

		int j = this.locationOfDecimal();
		int i = j+1;
		if((this.size()-i) < mathContext.getNumberOfDigitsAfterDecimal()) {
			return this;
		}

        j += mathContext.getNumberOfDigitsAfterDecimal();
        i = j+1;
        if(i>=this.size()) {
            return this;
        }
        if((this.charAt(i) < GlobalConstants.FIVE) || (this.charAt(i) == GlobalConstants.FIVE && this.charAt(j)%2 == 0)) {
            return this;
        }

        BigNumber result = new BigNumber(this);
        for(;result.charAt(j) == GlobalConstants.NINE; j--) {
            result.setValue(result.modify(j, GlobalConstants.ZERO));
        }

        result.setValue(result.modify(j, result.charAt(j) + 1 - GlobalConstants.ZERO));

        while(i<result.size()) {
            result.getValue().remove(i);
        }
        return result;
	}

    @Override
	public BigNumber roundOff() {
        return this.roundOff(this.getDefaultMathContext());
	}

    @Override
	public BigNumber modify(int index, int newDigit) {
        String methodName = BigNumber.MODIFY;
		if(index == 0) {
			this.getValue().remove(0);
            return this.putAtFirst(newDigit);
		}
		
		if(newDigit>9 || newDigit<0) {
			throw new IllegalArgumentException(BigNumber.FROM + methodName + ErrorMessages.NEW_DIGIT_INCOMPATIBLE_MSG.getValue());
		}
        BigNumber result = new BigNumber(this);
		result.getValue().add(index, (char)(newDigit + GlobalConstants.ZERO));
		result.getValue().remove(index+1);
        return result;
	}

    @Override
	public BigNumber modify(int index, char newDigit) {
        String methodName = BigNumber.MODIFY;
		if(index == 0) {
			this.getValue().remove(0);
			return this.putAtFirst(newDigit);
		}

        BigNumber result = new BigNumber(this);
		if(newDigit == GlobalConstants.DECIMAL_POINT) {
			if(!result.isFractional()) {
				result.getValue().add(index, newDigit);
				result.getValue().remove(index+1);
			} else if(result.getValue().get(index) == GlobalConstants.DECIMAL_POINT) {
				return result;
			} else {
				throw new IllegalArgumentException(BigNumber.FROM + methodName + ErrorMessages.NEW_DIGIT_INCOMPATIBLE_MSG.getValue());
			}
		} else if(newDigit>=GlobalConstants.ZERO && newDigit<=GlobalConstants.NINE) {
			return result.modify(index, newDigit-GlobalConstants.ZERO);
		} else {
			throw new IllegalArgumentException(BigNumber.FROM + methodName + ErrorMessages.NEW_DIGIT_INCOMPATIBLE_MSG.getValue());
		}
        return result;
	}

    @Override
	public BigNumber insert(int index, char digit) {
        String methodName = BigNumber.INSERT;
		if(index == 0) {
			return this.putAtFirst(digit);
		}

        BigNumber result = new BigNumber(this);
		if(digit == GlobalConstants.DECIMAL_POINT) {
			if(!result.isFractional()) {
				result.getValue().add(index, digit);
				result.syncFromValue();
			} else if(result.getValue().get(index) == GlobalConstants.DECIMAL_POINT) {
				return result;
			} else {
				throw new IllegalArgumentException(BigNumber.FROM + methodName + ErrorMessages.NEW_DIGIT_INCOMPATIBLE_MSG.getValue());
			}
		} else if(digit>=GlobalConstants.ZERO && digit<=GlobalConstants.NINE) {
			return result.insert(index, digit-GlobalConstants.ZERO);
		} else {
			throw new IllegalArgumentException(BigNumber.FROM + methodName + ErrorMessages.NEW_DIGIT_INCOMPATIBLE_MSG.getValue());
		}
        return result;
	}

    @Override
	public BigNumber insert(int index, int digit) {
        String methodName = BigNumber.INSERT;
		if(digit>9 || digit<0) {
			throw new IllegalArgumentException(BigNumber.FROM + methodName + ErrorMessages.NEW_DIGIT_INCOMPATIBLE_MSG.getValue());
		}
        BigNumber result = new BigNumber(this);
		result.getValue().add(index, (char)(digit + GlobalConstants.ZERO));
		result.syncFromValue();
        return result;
	}

    @Override
	public BigNumber putAtFirst(int digit) {
        String methodName = BigNumber.PUT_AT_FIRST;
		if(digit>9 || digit<0) {
			throw new IllegalArgumentException(BigNumber.FROM + methodName + ErrorMessages.NEW_DIGIT_INCOMPATIBLE_MSG.getValue());
		}
		List<Character> dig = new ArrayList<Character>();
		dig.add((char) (digit + GlobalConstants.ZERO));
		dig.addAll(this.getValue());
		return new BigNumber(dig);
	}

    @Override
	public BigNumber putAtFirst(char character) {
		List<Character> dig = new ArrayList<Character>();
		dig.add(character);
		dig.addAll(this.getValue());
		return new BigNumber(dig);
	}

    @Override
	public BigNumber reverse() {
		int size  = this.size();
		int limit = this.isNegative()? 1 : 0;

        BigNumber result = new BigNumber(this);
		for(int count=0; count<=size-limit-1; count++) {
			if(result.charAt(size - 1) == GlobalConstants.DECIMAL_POINT) {
				result.getValue().add(count+limit, GlobalConstants.DECIMAL_POINT);
				result.getValue().remove(size);
				continue;
			}

            result.insert(count + limit, result.charAt(size - 1));
			result.getValue().remove(size);
		}
		
		result.syncFromValue();
        return result;
	}

    @Override
	public BigNumber consolidate() {
		// Remember -ve and fractions
        BigNumber result = new BigNumber(this);

		for(int startIndex = result.isNegative()?1:0;result.charAt(startIndex) == GlobalConstants.ZERO; result.getValue().remove(startIndex));

		if(result.isFractional()) {
			// Consolidate from other end as well
			for(int i=result.size()-1; result.charAt(i) == GlobalConstants.ZERO; i=result.size()-1) {
				result.getValue().remove(i);
			}
		}

		if(result.charAt(result.size() - 1) == GlobalConstants.DECIMAL_POINT) {
			result.getValue().remove(result.size()-1);
		}
		
		if(result.size() == 0) {
            result.setValue(0);
		}

		result.syncFromValue();
        return result;
	}

    @Override
	public BigNumber append(BigNumber number) {
        String methodName = BigNumber.APPEND;
		if(this.isFractional() && number.isFractional()) {
			throw new IllegalArgumentException(BigNumber.FROM + methodName + ErrorMessages.TWO_DECIMAL_POINTS_MSG.getValue());
		}
		if(number.isNegative()) {
			throw new IllegalArgumentException(BigNumber.FROM + methodName + ErrorMessages.SECOND_NUMBER_NEGATIVE_MSG.getValue());
		}

        BigNumber result = new BigNumber();
        result.setValue(this.toString() + number.toString());
        result.syncFromValue();
        return result;
	}

    @Override
	public BigNumber append (int number) {
        String methodName = BigNumber.APPEND;
        if(number < 0) {
			throw new IllegalArgumentException(BigNumber.FROM + methodName + ErrorMessages.INCOMPATIBLE_VALUE_MSG.getValue());
		}

        BigNumber result = new BigNumber();
		result.setValue(this.toString() + ((Integer) number).toString());
		result.syncFromValue();
        return result;
	}

    @Override
	public BigNumber makeFractional() {
		if(this.isFractional()) {
			return this;
		}

        BigNumber result = new BigNumber(this);
        result.setValue(result.toString() + GlobalConstants.DECIMAL_POINT + GlobalConstants.ZERO_STR + GlobalConstants.ZERO_STR);
        return result;
	}

    @Override
    public BigNumber factorial() {
        if((this.compareTo(new BigNumber()) == 0) || (this.compareTo(UNITY) == 0)) {
            return UNITY;
        }

        BigNumber result = new BigNumber();
        BigNumber i = new BigNumber();
        result.setValue(this);

        for(i.setValue(BigNumberUtils.subtract(this, BigNumber.UNITY)); i.compareTo(UNITY) != 0; i.setValue(BigNumberUtils.subtract(i, UNITY))) {
            result.multiply(i);
        }

        result.consolidate();
        return result;
    }

    public String toJSON() {
        StringBuilder bigNum = new StringBuilder(GlobalConstants.LEFT_BRACE);

        bigNum.append(GlobalConstants.COMMA + GlobalConstants.DOUBLE_QUOTE + "isZero" + GlobalConstants.DOUBLE_QUOTE + GlobalConstants.COLON + this.isZero());
        if(this.isZero()) {
            bigNum.append(GlobalConstants.DOUBLE_QUOTE + "value" + GlobalConstants.DOUBLE_QUOTE + GlobalConstants.COLON + GlobalConstants.DOUBLE_QUOTE +
                    GlobalConstants.ZERO_STR + GlobalConstants.DOUBLE_QUOTE);
        } else {
            this.syncFromValue();
        }

        bigNum.append(GlobalConstants.COMMA + GlobalConstants.DOUBLE_QUOTE + "isFractional" + GlobalConstants.DOUBLE_QUOTE + GlobalConstants.COLON + this.isFractional());
        if(this.isFractional()) {
            bigNum.append(GlobalConstants.DOUBLE_QUOTE + "value" + GlobalConstants.DOUBLE_QUOTE + GlobalConstants.COLON + GlobalConstants.DOUBLE_QUOTE +
                    this.getBigDecimal().toString() + GlobalConstants.DOUBLE_QUOTE);
            bigNum.append(GlobalConstants.COMMA + GlobalConstants.DOUBLE_QUOTE + "bigDecimal" + GlobalConstants.DOUBLE_QUOTE + GlobalConstants.COLON +
                    GlobalConstants.DOUBLE_QUOTE + this.getBigDecimal().toString() + GlobalConstants.DOUBLE_QUOTE);
            bigNum.append(GlobalConstants.COMMA + GlobalConstants.DOUBLE_QUOTE + "locationOfDecimal" + GlobalConstants.DOUBLE_QUOTE + GlobalConstants.COLON +
                    GlobalConstants.DOUBLE_QUOTE + this.locationOfDecimal() + GlobalConstants.DOUBLE_QUOTE);
        } else {
            bigNum.append(GlobalConstants.DOUBLE_QUOTE + "value" + GlobalConstants.DOUBLE_QUOTE + GlobalConstants.COLON + GlobalConstants.DOUBLE_QUOTE +
                    this.getBigInteger().toString() + GlobalConstants.DOUBLE_QUOTE);
            bigNum.append(GlobalConstants.COMMA + GlobalConstants.DOUBLE_QUOTE + "bigInteger" + GlobalConstants.DOUBLE_QUOTE + GlobalConstants.COLON +
                    GlobalConstants.DOUBLE_QUOTE + this.getBigInteger().toString() + GlobalConstants.DOUBLE_QUOTE);
        }

        bigNum.append(GlobalConstants.COMMA + GlobalConstants.DOUBLE_QUOTE + "isNegative" + GlobalConstants.DOUBLE_QUOTE + GlobalConstants.COLON + this.isNegative());
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

        int i = (this.isNegative()?1:0);

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

    @Override
	protected void syncFromDecimal() {
		this.setValue(this.getBigDecimal());
	}

    @Override
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

}
