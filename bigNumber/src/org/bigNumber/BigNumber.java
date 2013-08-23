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
import java.util.List;
import org.bigNumber.common.services.exceptions.IncompatibleCharacterException;

public abstract class BigNumber implements Serializable, Comparable<BigNumber> {

	private static final long serialVersionUID = 1L;
	
	private List<Character>		value;
	private BigInteger			bigInteger;
	private BigDecimal			bigDecimal;
	private boolean				isNegative			=	false;
	private boolean				isFractional		=	false;
	private boolean				isZero				=	false;
	private Integer				locationOfDecimal;
	private	Integer				size;

	
	/**
	 * @return 0 if equal, +1 if greater and -1 if less
	 * @author Alok Shukla
	 * @since v1.0.0
	 */
	@Override
	public int compareTo(BigNumber number) {
		int result = 0;
		int lengthOfFirstNumber  = this.size();
		int lengthOfSecondNumber = number.size();

		if(this.isNegative() && !number.isNegative())
			return -1;
		if(!this.isNegative() && number.isNegative())
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
	
	//====================== GETTERS/SETTERS =========================
	
	protected List<Character> getValue() {
		return value;
	}

	protected Integer size() {
		return size;
	}

	protected void setSize(Integer size) {
		this.size = size;
	}

	protected Integer locationOfDecimal() {
		return locationOfDecimal;
	}

	protected void setLocationOfDecimal(Integer locationOfDecimal) {
		this.locationOfDecimal = locationOfDecimal;
	}

	protected boolean isZero() {
		return isZero;
	}

	protected void setZero(boolean isZero) {
		this.isZero = isZero;
	}

	protected boolean isFractional() {
		return isFractional;
	}

	protected void setFractional(boolean isFractional) {
		this.isFractional = isFractional;
	}

	protected BigDecimal getBigDecimal() {
		return bigDecimal;
	}

	protected void setBigDecimal(BigDecimal bigDecimal) {
		this.bigDecimal = bigDecimal;
	}

	protected boolean isNegative() {
		return isNegative;
	}

	protected void setNegative(boolean isNegative) {
		this.isNegative = isNegative;
	}

	protected BigInteger getBigInteger() {
		return bigInteger;
	}

	protected void setBigInteger(BigInteger bigInteger) {
		this.bigInteger = bigInteger;
	}
	
	//____________________________________________________________________________________
	//********************************* CONSTRUCTORS *************************************
	//____________________________________________________________________________________

	/**
	 * Constructs a Big type number with default value 0
	 * @author Alok Shukla
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
	 * @author Alok Shukla
	 * @since v0.1.0
	 */
	public BigNumber(Big number) {
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
	public BigNumber(BigDecimal number) {
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
	public BigNumber(BigInteger number) {
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
	public BigNumber(String number) throws IncompatibleCharacterException {
		this.setValue(number);
	}
	
	/**
	 * @author Alok Shukla
	 * @since v1.0.0
	 * @param number
	 * @throws IncompatibleCharacterException
	 */
	public BigNumber(List<Character> number) throws IncompatibleCharacterException {
		this.setValue(number);
	}
	
	/**
	 * @author Alok Shukla
	 * @since v1.0.0
	 * @param number
	 * @throws IncompatibleCharacterException
	 */
	public BigNumber(Integer number) throws IncompatibleCharacterException {
		this.setValue(number);
	}
	
	/**
	 * @author Alok Shukla
	 * @since v1.0.0
	 * @param number
	 * @throws IncompatibleCharacterException
	 */
	public BigNumber(Float number) throws IncompatibleCharacterException {
		this.setValue(number);
	}
	
	/**
	 * @author Alok Shukla
	 * @since v1.0.0
	 * @param number
	 * @throws IncompatibleCharacterException
	 */
	public BigNumber(Double number) throws IncompatibleCharacterException {
		this.setValue(number);
	}
	
	/*======================================================================
	 * 						ABSTRACT METHODS
	 * =====================================================================
	*/
	
	/**
	 * It puts the provided number in the Big variable
	 * @param number
	 * As String, int, float, double, long etc.
	 * @throws IncompatibleCharacterException
	 * @author Alok Shukla
	 * @since August 6, 2013, v0.1.0
	 */
	public abstract <E extends Comparable<E>> void setValue(E number) throws IncompatibleCharacterException;
	
	/**
	 * It puts the provided number in the Big variable
	 * @param number
	 * Big number
	 * @throws IncompatibleCharacterException
	 * @author Alok Shukla
	 * @since v0.1.0
	 */
	public abstract void setValue(Big number) throws IncompatibleCharacterException;
	
	/**
	 * It puts the provided number in the Big variable
	 * @param number As String which may contain only numbers 0 to 9, a leading -ve sign '-' and a decimal point  
	 * @throws IncompatibleCharacterException
	 * @author Alok Shukla
	 * @since August 6, 2013, v0.1.0
	 */
	public abstract void setValue(List<Character> number) throws IncompatibleCharacterException;
	
	/**
	 * <ul><li>No character accept '0' to '9', '.' and '-' is allowed.</li>
	 * <li>No two '.' are allowed.</li>
	 * <li>Spaces (' ' or " ") in the provided String are removed before setting the value so no problem with spaces.</li>
	 * <li>'-' is allowed only as first character</li></ul>
	 * @param value
	 * @throws IncompatibleCharacterException
	 */
	public abstract void setValue(String value) throws IncompatibleCharacterException;
	
}
