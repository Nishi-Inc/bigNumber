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


package org.bigNumber;

import java.util.LinkedList;
import java.util.List;
import org.bigNumber.BigNumber;
import org.bigNumber.common.models.BigNumberFactory;

/**
 * A BigNumberPool which keeps BigNumbers and handles allocation
 * @author Nishi Incorporation
 * @since v1.0.0
 */
public class BigNumberPool implements BigNumberFactory {
	
	private List<BigNumber> 	allotted;
	private List<BigNumber> 	free;
	private Integer				capacity;
	private Integer				loadFactor;
	
	private static int			index				=	0;
	private static int			minCap				=	1;
	private static int			maxCap				=	100;
	private static int			minLoadFactor		=	0;
	private static int			maxLoadFactor		=	100;
	
	private static final int	DEFAULT_LOAD_FACTOR	=	40;
	private static final int	DEFAULT_CAPACITY	=	5;
	
	
	public BigNumberPool(){}
	
	public BigNumberPool(int capacity, int loadFactor) {
		this.setCapacity(capacity);
		minCap = capacity;
		this.setLoadFactor(loadFactor);
		minLoadFactor = loadFactor;
	}
	
	@Override
	public BigNumber getBigNumber() {
		
		if(this.getFree().isEmpty()) {
			this.getFree().add(new BigNumber());
			return getBigNumber();
		}
		for(BigNumber bignum : this.getFree()) {
			this.getFree().remove(bignum);
			this.getAllotted().add(bignum);
			return bignum;
		}
		return null;
	}
	
	@Override
	public void destroy(BigNumber... bignums) {
		int size = bignums.length;
		for(int i=0; i<size; i++) {
			this.destroy(bignums[i]);
		}
	}
	
	private void destroy(BigNumber bignum) {
		boolean flag = this.getAllotted().remove(bignum);
		if(flag) {
			this.getFree().add(bignum);
			this.managePool();
		}
	}
	
	/**
	 * Manages BigNumber pool as per defined capacity,
	 * <br/>loadFactor and currently allotted and free BigNumbers.
	 */
	private void managePool() {
		for(;this.getFree().size() > this.getCapacity();) {
			for(BigNumber bignum : this.getFree()) {
				this.getFree().remove(bignum);
				break;
			}
		}
		
		int sizeOfAllotted = this.getAllotted().size();
		int limit          = this.getCapacity() * (1 + this.getLoadFactor()/100) + 1;
		for(;(this.getFree().size()+sizeOfAllotted > limit) && (!this.getFree().isEmpty());) {
			for(BigNumber bignum : this.getFree()) {
				this.getFree().remove(bignum);
				break;
			}
		}
	}
	
	/**
	 * This determines appropriate values of <b>capacity</b> and <b>loadFactor</b> in run-time and sets them up.
	 * It's the reason why <b>capacity</b> and <b>loadFactor</b> are not declared as constants.
	 */
	@SuppressWarnings("unused")
	private void managePoolCapacityAndLoadFactor() {
		// TODO Write logic
		/* There should be some number(quotient, index, parameter) to
		 * control modification and level of modification of capacity
		 * and loadFactor. index is used here.
		 */
	}

	private List<BigNumber> getFree() {
		if(free == null) {
			free = new LinkedList<BigNumber>();
		}
		return free;
	}

	private List<BigNumber> getAllotted() {
		if(allotted == null) {
			allotted = new LinkedList<BigNumber>();
		}
		return allotted;
	}

	/**
	 * loadFactor cannot be equal to 0
	 * @return loadFactor of BigNumber pool as an <i>int</i> (as percentage: a number between 1 to 100)
	 */
	private int getLoadFactor() {
		if(loadFactor == null) {
			this.setLoadFactor(DEFAULT_LOAD_FACTOR);
		}
		
		if(loadFactor < minLoadFactor) {
			this.setLoadFactor(minLoadFactor);
		} else if(loadFactor > maxLoadFactor) {
			this.setLoadFactor(maxLoadFactor);
		}
		
		return loadFactor;
	}

	private void setLoadFactor(int loadFactor) {
		this.loadFactor = loadFactor;
	}

	private int getCapacity() {
		if(capacity == null) {
			this.setCapacity(DEFAULT_CAPACITY);
		}
		
		if(capacity < minCap) {
			this.setCapacity(minCap);
		} else if(capacity > maxCap) {
			this.setCapacity(maxCap);
		}
		
		return capacity;
	}

	private void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
}
