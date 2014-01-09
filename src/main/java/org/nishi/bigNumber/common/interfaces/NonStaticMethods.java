package org.nishi.bigNumber.common.interfaces;

import org.nishi.bigNumber.models.BigNumber;
import org.nishi.bigNumber.models.BigNumberMathContext;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * @author aloks
 * Date: 21/11/13
 * Time: 6:06 PM
 * @since 2.0.0
 */
public interface NonStaticMethods extends Comparable<BigNumber>, Serializable {

    /**
     * @since v1.0.0
     * @param number To add to <i>this</i>
     * @return BigNumber Sum of this + givenNumber
     */
    public BigNumber add(BigNumber number);

    /**
     * @return BigNumber product of <i>this</i> and provided number
     * @since v1.0.0
     * @param number To multiply to <i>this</i>
     */
    public BigNumber multiply(BigNumber number);

    /**
     * @return BigNumber <code>this - number</code>
     * @since v1.0.0
     * @param number To subtract from <i>this</i>
     */
    public BigNumber subtract(BigNumber number);

    /**
     *
     * @since v1.0.0
     * @param number to divide <i>this</i> with
     * @return BigNumber <code>this/number</code>
     */
    public BigNumber divide(BigNumber number);

    /**
     * @since v1.0.0
     * @param number A BigNumber to divide and compute remainder
     * @return An array of two BigNumber elements, the quotient <i>(this/number)</i> is the 0th element and remainder <i>(this%number)</i> is the 1st element
     */
    public BigNumber[] divideAndRemainder(BigNumber number);

    /**
     * @return If <code>this.isNegative()</code> is true, <code>- this</code><br/>
     * else <code>this</code>
     * @since v1.0.0
     */
    public BigNumber absolute();

    /**
     * @return BigNumber containing <code>this % denominator</code>
     * @param denominator The number to divide <code>this</code> with
     * @since v1.0.0
     */
    public BigNumber modulus(BigNumber denominator);

    /**
     * @return <code>this^power</code>
     * @param power int to raise power with
     * @since v1.0.0
     */
    public BigNumber pow(int power);

    /**
     * @since v1.1.0
     * @return A BigNumber, Factorial value of the calling number no change in the calling number will be made
     * @deprecated
     */
    public BigNumber factorial();

    /**
     * Returns a plain String containing value of the BigNumber Number
     * @author Nishi Inc.
     * @since v1.0.0
     */
    public String toEngineeringString();

    /**
     * @return A List of characters containing value of the BigNumber number
     * @author Nishi Inc.
     * @since v0.1.0
     */
    public List<Character> getValue();

    /**
     * @author Nishi Inc.
     * @since v1.0.0
     * @return Value of the BigNumber number as BigDecimal
     */
    public BigDecimal toBigDecimal();

    /**
     * @author Nishi Inc.
     * @since v1.0.0
     * @return Value of the BigNumber number as BigInteger
     */
    public BigInteger toBigInteger();

    /**
     * This method is to cast BigNumber to a Integer variable.<br/>
     * WARNING: Data can be lost.<Br/>
     * Use it only when you are sure that the value can be fit into a Integer variable
     * @author Nishi Inc.
     * @since v1.0.0
     * @return Value of the BigNumber number as an Integer
     * @see java.lang.Integer
     */
    public Integer toInteger();

    /**
     * This method is to cast BigNumber to a Double variable.<br/>
     * WARNING: Data can be lost.<Br/>
     * Use it only when you are sure that the value can be fit into a Double variable
     * @author Nishi Inc.
     * @since v1.0.0
     * @return Value of the BigNumber number as a Double
     * @see java.lang.Double
     */
    public Double toDouble();

    /**
     *
     * @return Number of digits including decimal point and -ve sign
     * @author Nishi Inc.
     * @since v0.1.0
     */
    public Integer size();

    /**
     *
     * @param startIndex
     * @param endIndex
     * @return Value of the number as string containing characters between (and including) the given indices
     * @author Nishi Inc.
     * @since v0.1.0
     */
    public String getValueBetween(int startIndex, int endIndex);

    /**
     *
     * @param index
     * @return Value of the number as String containing characters till the given index
     * @author Nishi Inc.
     * @since v0.1.0
     */
    public String getValueTill(int index);

    /**
     * Gives the character at specified index
     * @param index where?
     * @return The character at the specified index
     * @since v0.1.0
     */
    public Character charAt(int index);

    /**
     * @return An Integer.<br/>It discards the fractional part and keeps the whole number.
     * @since v0.1.0
     */
    public BigNumber convertToInteger();

    /**
     * @return Converts a number to negative
     * @since August 8, 2013, v0.1.0
     */
    public BigNumber makeNegative();

    /**
     * @return Rounds off a number to the given number of digits
     * @param mathContext A BigNumberMathContext object to define roundingOff parameters
     * @since August 8, 2013, v0.1.0
     * @see BigNumberMathContext
     */
    public BigNumber roundOff(BigNumberMathContext mathContext);

    /**
     * @return Rounds off a number to the number of digits specified by GlobalConstants.DEFAULT_ROUND_OFF_DIGITS
     * @since August 8, 2013, v0.1.0
     */
    public BigNumber roundOff();

    /**
     * @return Changes the digit at the given index to the given digit
     * @param index where
     * @param newDigit changes to this newDigit
     * @since v0.1.0
     */
    public BigNumber modify(int index, int newDigit);

    /**
     * @return Changes the digit at the given index to the given digit
     * @param index where to change
     * @param newDigit what to change to
     * @since v0.1.0
     */
    public BigNumber modify(int index, char newDigit);

    /**
     * @return Inserts a new digit at the given index
     * @param index where?
     * @param digit what?
     * @since v0.1.0
     */
    public BigNumber insert(int index, char digit);

    /**
     * @return Inserts a new digit at the given index
     * @param index where to insert
     * @param digit what to insert
     * @since v0.1.0
     */
    public BigNumber insert(int index, int digit);

    /**
     * @return Puts the given digit at the beginning of the given number
     * @param digit An int
     * @since August 8, 2013, v0.1.0
     */
    public BigNumber putAtFirst(int digit);

    /**
     * @return Puts the given character at the beginning of the given number
     * @param character A char
     * @author Nishi Inc.
     * @since August 8, 2013, v0.1.0
     */
    public BigNumber putAtFirst(char character);

    /**
     * @return Reverses the BigNumber
     * @since v0.1.0
     */
    public BigNumber reverse();

    /**
     * @return Removes leading zeroes as well as trailing zeroes in case of fraction
     * @since v1.0.0
     */
    public BigNumber consolidate();

    /**
     * @return Appends the given number to the calling number
     * @param number A BigNumber
     * @since v0.1.0
     */
    public BigNumber append(BigNumber number);

    /**
     * @return Appends the given positive number to the BigNumber number
     * @param number A positive int
     * @since v0.1.0
     */
    public BigNumber append (int number);

    /**
     * @return The same BigNumber if <i>this</i> is already fractional else appends ".00"
     * @since v1.0.0
     */
    public BigNumber makeFractional();

    /**
     *
     * @return A String containing BigNumber in JSON format
     */
    public String toJSON();
}
