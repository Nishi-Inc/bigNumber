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


package org.bigNumber.interfaces;

import org.bigNumber.BigNumber;

/**
 * 
 * @author Nishi Inc.
 * @since v1.1.0
 */
public interface MathematicalMethods {

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

}
