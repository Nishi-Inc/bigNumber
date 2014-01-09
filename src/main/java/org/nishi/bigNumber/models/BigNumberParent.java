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

import org.nishi.bigNumber.common.interfaces.NonStaticMethods;
import org.nishi.bigNumber.helper.GlobalConstants;
import org.nishi.bigNumber.helper.Utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * Parent class for BigNumber
 * @author Nishi Inc.
 * @since v1.1.0
 */
public abstract class BigNumberParent implements NonStaticMethods {

	protected static final long serialVersionUID = 1L;

    protected boolean				isNegative			=	false;
    protected boolean				isFractional		=	false;
    protected boolean				isZero				=	false;
    protected Integer				locationOfDecimal;
    protected BigInteger            bigInteger;
    protected BigDecimal            bigDecimal;
    protected List<Character>		value;
    protected BigNumberMathContext  defaultBigNumberMathContext;

    protected BigDecimal getBigDecimal() {
        if(bigDecimal == null) {
            this.setBigDecimal(new BigDecimal(0));
        }
        return bigDecimal;
    }

    public BigNumberMathContext getDefaultMathContext() {
        return defaultBigNumberMathContext;
    }

    public void setDefaultMathContext(BigNumberMathContext defaultMathContext) {
        this.defaultBigNumberMathContext = defaultMathContext;
    }

    protected void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
        this.syncFromDecimal();
    }

    /**
     * Sets all the fields of BigNumber as per the value of BigDecimal field
     */
    protected abstract void syncFromDecimal();

    /**
     * Sets all the fields of BigNumber as per the value of BigInteger field
     */
    protected abstract void syncFromInteger();

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
        return Utils.combine(this.getValue(), GlobalConstants.BLANK);
	}


    //==================================================================================

    /*==================================================================================
	 *                               GETTERS/SETTERS
	 *==================================================================================
	 */

    /**
     * @return true if the number is negative else false
     * @since v0.1.0
     */
    public boolean isNegative() {
        return isNegative;
    }

    protected void setNegative(boolean isNegative) {
        this.isNegative = isNegative;
    }

    /**
     * @return true if the number is a fraction else false
     * @since v0.1.0
     */
    public boolean isFractional() {
        return isFractional;
    }

    protected void setFractional(boolean isFraction) {
        this.isFractional = isFraction;
        if(!isFraction) {
            this.setLocationOfDecimal(-1);
        }
    }

    /**
     * @return position of decimal point in the fractional number,<br/>
     * -1 if it's not a fractional number
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
            this.setBigInteger(new BigInteger(GlobalConstants.ZERO_STR));
        }
        return bigInteger;
    }

    protected void setBigInteger(BigInteger bigInteger) {
        this.bigInteger = bigInteger;
        this.syncFromInteger();
    }

    /**
     * @return true if the number has value = 0 or "0" else false
     * @since v0.1.0
     */
    public boolean isZero() {
        return isZero;
    }

    protected void setZero(boolean isZero) {
        this.isZero = isZero;
    }

}
