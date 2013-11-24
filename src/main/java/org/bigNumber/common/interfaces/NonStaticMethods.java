package org.bigNumber.common.interfaces;

import org.bigNumber.models.BigNumber;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.List;

/**
 * @author aloks
 * Date: 21/11/13
 * Time: 6:06 PM
 * @since 2.0.0
 */
public interface NonStaticMethods extends Comparable<BigNumber>, Serializable {

    /**
     * @author Nishi Inc.
     * @since v1.0.0
     * @param number To add to <i>this</i>
     */
    public void add(BigNumber number);

    /**
     * Now BigNumber <i>this</i> will hold product of <i>this</i> and provided number
     * @author Nishi Inc.
     * @since v1.0.0
     * @param number To multiply to <i>this</i>
     */
    public void multiply(BigNumber number);

    /**
     * @author Nishi Inc.
     * @since v1.0.0
     * @param number To multiply to <i>this</i>
     */
    public void subtract(BigNumber number);

    /**
     * @author Nishi Inc.
     * @since v1.0.0
     * @param number to divide <i>this</i> with
     */
    public void divide(BigNumber number);

    /**
     * @author Nishi Inc.
     * @since v1.0.0
     * @param number A BigNumber to divide and compute remainder
     * @return An array of two BigNumber elements, the quotient <i>(this/number)</i> is the 0th element and remainder <i>(this%number)</i> is the 1st element
     */
    public BigNumber[] divideAndRemainder(BigNumber number);

    /**
     * After this operation <i>this</i> will hold value of +ve value of <i>this</i>
     * @author Nishi Inc.
     * @since v1.0.0
     */
    public void absolute();

    /**
     * @author Nishi Inc.
     * @since v1.0.0
     * @param denominator
     */
    public void modulus(BigNumber denominator);

    /**
     * After this operation this = this^power
     * @author Nishi Inc.
     * @since v1.0.0
     * @param power
     */
    public void pow(int power);

    /**
     * @author Nishi Inc
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
     * @param index
     * @return The character at the specified index
     * @author Nishi Inc.
     * @since v0.1.0
     */
    public Character charAt(int index);

    /**
     * It doesn't return an Integer.<br/>It discards the fractional part and keeps the whole number.
     * @author Nishi Inc.
     * @since v0.1.0
     */
    public void convertToInteger();

    /**
     * Converts a number to negative
     * @author Nishi Inc.
     * @since August 8, 2013, v0.1.0
     */
    public void makeNegative();

    /**
     * Rounds off a number to the given number of digits
     * @author Nishi Inc.
     * @param mathContext
     * @since August 8, 2013, v0.1.0
     */
    public void roundOff(MathContext mathContext);

    /**
     * Rounds off a number to the number of digits specified by GlobalConstants.DEFAULT_ROUND_OFF_DIGITS
     * @author Nishi Inc.
     * @since August 8, 2013, v0.1.0
     */
    public void roundOff();

    /**
     * Changes the digit at the given index to the given digit
     * @param index
     * @param newDigit
     * @author Nishi Inc.
     * @since v0.1.0
     */
    public void modify(int index, int newDigit);

    /**
     * Changes the digit at the given index to the given digit
     * @param index
     * @param newDigit
     * @author Nishi Inc.
     * @since v0.1.0
     */
    public void modify(int index, char newDigit);

    /**
     * Inserts a new digit at the given index
     * @param index
     * @param digit
     * @author Nishi Inc.
     * @since v0.1.0
     */
    public void insert(int index, char digit);

    /**
     * Inserts a new digit at the given index
     * @param index
     * @param digit
     * @author Nishi Inc.
     * @since v0.1.0
     */
    public void insert(int index, int digit);

    /**
     * Puts the given digit at the beginning of the given number
     * @param digit An int
     * @author Nishi Inc.
     * @since August 8, 2013, v0.1.0
     */
    public void putAtFirst(int digit);

    /**
     * Puts the given character at the beginning of the given number
     * @param character A char
     * @author Nishi Inc.
     * @since August 8, 2013, v0.1.0
     */
    public void putAtFirst(char character);

    /**
     * Reverses the BigNumber
     * @author Nishi Inc.
     * @since v0.1.0
     */
    public void reverse();

    /**
     * Removes leading zeroes as well as trailing zeroes in case of fraction
     * @author Nishi Inc.
     * @since v1.0.0
     */
    public void consolidate();

    /**
     * Appends the given number to the calling number
     * @param number
     * @author Nishi Inc.
     * @since v0.1.0
     */
    public void append(BigNumber number);

    /**
     * Appends the given positive number to the BigNumber number
     * @param number
     * @author Nishi Inc.
     * @since v0.1.0
     */
    public void append (int number);

    /**
     * Does nothing if <i>this</i> is already fractional else appends ".00"
     * @since v1.0.0
     */
    public void makeFractional();

    /**
     *
     * @return A String containing BigNumber in JSON format
     */
    public String toJSON();
}
