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

package org.bigNumber.common.interfaces;

import org.bigNumber.models.BigNumber;

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
	 * @author Nishi Inc.
	 * @since v1.1.0
	 * @param key
	 * @return A BigNumber object which is on-hold corresponding to the given key
	 */
	public BigNumber getBigNumber(Integer key);
	
	/**
	 * Returns the BigNumber object back to factory
	 * @since v1.0.0
	 * @param bignums BigNumbers to return back to pool
	 */
	public void destroy(BigNumber... bignums);
	
}
