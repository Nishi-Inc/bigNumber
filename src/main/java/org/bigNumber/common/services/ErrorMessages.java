package org.bigNumber.common.services;

/**
 * @author Nishi Inc.
 * Date: 11/16/13
 * Time: 11:03 PM
 * @since v1.1.0
 */
public enum ErrorMessages {
    INCOMPATIBLE_VALUE_MSG("Value is incompatible."),
    TWO_DECIMAL_POINTS_MSG("A number cannot have two decimal points."),
    NEW_DIGIT_INCOMPATIBLE_MSG("newDigit is incompatible."),
    SECOND_NUMBER_NEGATIVE_MSG("Second number should not be negative.");

    private static final String PREPEND = GlobalConstants.LEFT_BRACKET + GlobalConstants.RIGHT_BRACKET + GlobalConstants.COLON + GlobalConstants.SPACE;
    private String value;

    private ErrorMessages(String value) {
        this.value = value;
    }

    public String getValue() {
        return PREPEND + this.value;
    }

}
