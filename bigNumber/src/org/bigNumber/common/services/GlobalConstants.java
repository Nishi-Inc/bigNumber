package org.bigNumber.common.services;

import org.bigNumber.models.BigNumber;

/**
 * Objects of this class are not to be created.
 * @author Nishi Inc.
 * @since v0.1.0
 */
public final class GlobalConstants {
    private static BigNumber one = new BigNumber(1);
    public static final BigNumber UNITY         =   one;

    public static final int ASCII_ZERO          =   48;

    public static final char ZERO               =   '0';
    public static final char ONE                =   '1';
    public static final char TWO                =   '2';
    public static final char THREE              =   '3';
    public static final char FOUR               =   '4';
    public static final char FIVE               =   '5';
    public static final char SIX                =   '6';
    public static final char SEVEN              =   '7';
    public static final char EIGHT              =   '8';
    public static final char NINE               =   '9';
    public static final char DECIMAL_POINT      =   '.';
    public static final char MINUS              =   '-';
    public static final char PLUS               =   '+';

    public static final String ZERO_STR         =   "0";
    public static final String ONE_STR          =   "1";
    public static final String MINUS_STR        =   "-";

    public static final String AT               =   "@";
    public static final String COLON            =   ":";
    public static final String SPACE            =   " ";
    public static final String BLANK            =   "";
    public static final String BACK_SLACE       =   "\\";
    public static final String LEFT_BRACKET     =   "(";
    public static final String RIGHT_BRACKET    =   ")";
    public static final String DOUBLE_QUOTE     =   "\"";
    public static final String SINGLE_QUOTE     =   "\'";

    public static final String DECIMAL_POINT_STR    =   ".";
    public static final String ENGINEERING_SYMBOL   =   "E";

}
