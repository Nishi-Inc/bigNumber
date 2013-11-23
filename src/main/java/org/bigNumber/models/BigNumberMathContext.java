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
    private static final RoundingMode DEFUALT_ROUNDING_MODE         = RoundingMode.HALF_EVEN;

    private RoundingMode roundingMode;
    private Integer numberOfDigitsAfterDecimal;
    private int precision;

    /**
     * Defualt constructor
     */
    public BigNumberMathContext() {
        this.roundingMode = BigNumberMathContext.DEFUALT_ROUNDING_MODE;
        this.precision = MathContext.UNLIMITED.getPrecision();
        this.setNumberOfDigitsAfterDecimal(BigNumberMathContext.DEFAULT_NUMBER_OF_DIGITS_AFTER_DECIMAL);
    }

    /**
     *
     * @param roundingMode To set roundingMode
     */
    public BigNumberMathContext(RoundingMode roundingMode) {
        this.roundingMode = roundingMode;
        this.precision = MathContext.UNLIMITED.getPrecision();
        this.setNumberOfDigitsAfterDecimal(BigNumberMathContext.DEFAULT_NUMBER_OF_DIGITS_AFTER_DECIMAL);
    }

    /**
     *
     * @param mathContext mathContext object to instantiate BigNumberMathContext object
     */
    public BigNumberMathContext(MathContext mathContext) {
        this.roundingMode = mathContext.getRoundingMode();
        this.precision = mathContext.getPrecision();
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