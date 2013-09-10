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

import java.io.Serializable;
import org.bigNumber.BigNumber;

/**
 * Root class for all the children class up to BigNumber
 * @author Nishi Inc.
 * @since v1.1.0
 */
public abstract class Root implements Serializable, Comparable<BigNumber> {

	private static final long serialVersionUID = 1L;

	/**
	 * @return 0 if equal, +1 if greater and -1 if less
	 * @author Nishi Inc.
	 * @since v0.1.0
	 */
	@Override
	public abstract int compareTo(BigNumber number);
	
	public abstract int hashCode();
	
	public abstract boolean equals(Object number);
	
	/**
	 * Returns a plain String containing value of the BigNumber Number
	 * @author Nishi Inc.
	 * @since v0.1.0
	 */
	public abstract String toString();

}
