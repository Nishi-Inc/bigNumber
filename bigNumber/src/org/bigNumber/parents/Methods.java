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

package org.bigNumber.parents;

import java.util.List;
import org.bigNumber.BigNumber;

public abstract class Methods extends Root {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Sets all the field variables as per the value of bigNumber.
	 */
	protected abstract void syncFromValue();
	
	/**
	 * Sets all the field variables as per the value of bigInteger
	 */
	protected abstract void syncFromInteger();
	
	/**
	 * Sets all the field variables as per the value of bigDecimal
	 */
	protected abstract void syncFromDecimal();

	/**
	 * It puts the provided number in the BigNumber variable
	 * toString() method on the provided number will be called hence do not use primitive types
	 * @param number
	 * As String, Integer, Float, Double, Long etc.
	 * @author Nishi Inc.
	 * @since August 6, 2013, v0.1.0
	 */
	public abstract <E extends Comparable<E>> void setValue(E number);
	
	/**
	 * It puts the provided number in the BigNumber variable
	 * @param number
	 * BigNumber number
	 * @author Nishi Inc.
	 * @since v0.1.0
	 */
	public abstract void setValue(BigNumber number);

	/**
	 * It puts the provided number in the BigNumber variable
	 * @param number As String which may contain only numbers 0 to 9, a leading -ve sign '-' and a decimal point  
	 * @author Nishi Inc.
	 * @since August 6, 2013, v0.1.0
	 */
	public abstract void setValue(List<Character> number);

	/**
	 * <ul><li>No character accept '0' to '9', '.' and '-' is allowed.</li>
	 * <li>No two '.' are allowed.</li>
	 * <li>Spaces (' ' or " ") in the provided String are removed before setting the value so no problem with spaces.</li>
	 * <li>'-' is allowed only as first character</li></ul>
	 * @param value
	 */
	public abstract void setValue(String value);
	
	/**
	 * @return position of decimal point in the fractional number,
	 * -1 if it's not a fractional number
	 * @author Nishi Inc.
	 * @since v0.1.0
	 */
	public abstract int locationOfDecimal();
	
	/**
	 * 
	 * @return true if the number is a fraction else false
	 * @author Nishi Inc.
	 * @since v0.1.0
	 */
	public abstract boolean isFractional();
	
	/**
	 * @return true if the number is negative else false
	 * @author Nishi Inc.
	 * @since v0.1.0
	 */
	public abstract boolean isNegative();
	
	/**
	 * @return true if the number has value = 0 or "0" else false
	 * @author Nishi Inc.
	 * @since v0.1.0
	 */
	public abstract boolean isZero();

}
