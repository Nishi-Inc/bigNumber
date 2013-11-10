package org.bigNumber.common.services;

import org.bigNumber.BigNumber;

/**
 * Objects of this class are not to be created.
 * @author Nishi Inc.
 * @since v0.1.0
 */
public final class Constants {
	/**
	 * For BigNumber.roundOff()
	 */
	public final static Integer		DEFAULT_ROUND_OFF_DIGITS		=	2;
	
	/**
	 * For BigNumberPool
	 */
	public static final int			DEFAULT_LOAD_FACTOR				=	40;
	
	/**
	 * For BigNumberPool
	 */
	public static final int			DEFAULT_CAPACITY				=	5;


    public static final BigNumber UNITY         =   new BigNumber(1);

    public static final String AT               =   "@";
    public static final String COLON            =   ":";
    public static final String DOUBLE_QUOTE     =   "\"";
    public static final String SINGLE_QUOTE     =   "\'";
}
