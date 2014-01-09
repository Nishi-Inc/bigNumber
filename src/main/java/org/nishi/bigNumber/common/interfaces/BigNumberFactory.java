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

package org.nishi.bigNumber.common.interfaces;

import org.nishi.bigNumber.models.BigNumber;

/**
 * A factory to generate BigNumber objects
 * @author Nishi Inc.
 * @since August 26, 2013
 */
public interface BigNumberFactory {
	
	/**
	 * @author Nishi Inc.
	 * @since v1.0.0
	 * @return A BigNumber object
	 */
	public BigNumber getBigNumber();
	
	/**
     * It returns the BigNumber which was put on-hold respective to the provided key<br/>
     * In case the key is wrong and/or no BigNumber exists corresponding to it a new BigNumber would be returned.<br/>
     * Make sure that you do handle it as well.
	 * @author Nishi Inc.
	 * @since v1.1.0
	 * @param key Integer to get corresponding BigNumber
	 * @return A BigNumber object which is on-hold corresponding to the given key
	 */
	public BigNumber getBigNumber(Integer key);
	
	/**
	 * Returns the BigNumber object back to factory
	 * @since v1.0.0
	 * @param bignums BigNumbers to return back to pool
	 */
	public void destroy(BigNumber... bignums);

    /**
     * Puts given BigNumber on-hold.<br/>
     * If the given number was not initially allocated, it's added to pool and put at hold.<br/>
     * DO NOT FORGET TO DESTROY NUMBERS ON-HOLD.
     * @author Nishi Inc.
     * @since v1.1.0
     * @param bignum Put this bigNumber on-hold
     * @return A 'key' by which this particular number can be grabbed from the pool<br>
     * Just call getBigNumber(key)
     */
    public Integer hold(BigNumber bignum);
	
}
