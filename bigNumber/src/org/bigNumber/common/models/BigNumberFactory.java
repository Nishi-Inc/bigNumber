package org.bigNumber.common.models;

import org.bigNumber.BigNumber;

/**
 * A factory to generate BigNumber objects
 * @author Nishi Inc.
 * @since August 26, 2013
 */
public interface BigNumberFactory {
	
	/**
	 * @author Nishi Inc.
	 * @return A BigNumber object
	 */
	public BigNumber getBigNumber();
	
	/**
	 * Returns the BigNumber object back to factory
	 * @param bignum
	 */
	public void destroy(BigNumber... bignums);
	
}
