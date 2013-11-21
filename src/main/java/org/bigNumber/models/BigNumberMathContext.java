package org.bigNumber.models;

import org.bigNumber.common.services.GlobalConstants;

import java.math.MathContext;
import java.math.RoundingMode;

/**
 * @author aloks
 * Date: 21/11/13
 * Time: 3:27 PM
 * @since 2.0.0
 */
public class BigNumberMathContext {

    private static final int DEFAULT_NUMBER_OF_DIGITS_AFTER_DECIMAL = 6;

    private RoundingMode roundingMode;
    private Integer numberOfDigitsAfterDecimal;
    private int precision;

    /**
     *
     * @param roundingMode To set roundingMode
     */
    public BigNumberMathContext(RoundingMode roundingMode) {
        this.roundingMode = roundingMode;
        this.precision = MathContext.UNLIMITED.getPrecision();
        this.setNumberOfDigitsAfterDecimal(BigNumberMathContext.DEFAULT_NUMBER_OF_DIGITS_AFTER_DECIMAL);
    }

    public Integer getNumberOfDigitsAfterDecimal() {
        return numberOfDigitsAfterDecimal;
    }

    public void setNumberOfDigitsAfterDecimal(Integer numberOfDigitsAfterDecimal) {
        this.numberOfDigitsAfterDecimal = numberOfDigitsAfterDecimal;
    }

    public RoundingMode getRoundingMode() {
        return roundingMode;
    }

    public int getPrecision() {
        return precision;
    }

}
