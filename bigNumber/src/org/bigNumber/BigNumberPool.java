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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.bigNumber.BigNumber;
import org.bigNumber.common.models.BigNumberFactory;
import org.bigNumber.common.services.Constants;
import org.bigNumber.common.services.exceptions.IncompatibleCharacterException;

/**
 * A BigNumberPool which keeps BigNumbers and handles allocation
 * @author Nishi Incorporation
 * @since v1.0.0
 */
public class BigNumberPool implements BigNumberFactory {
	
	private List<BigNumber> 			allotted;
	private List<BigNumber> 			free;
	private HashMap<Integer, BigNumber> hold = new HashMap<Integer, BigNumber>();
	private Integer						capacity;
	private Integer						loadFactor;
	private Integer						limit;
	private Integer						key;
	
	private static int			minCap				=	1;
	private static int			maxCap				=	100;
	private static int			minLoadFactor		=	0;
	private static int			maxLoadFactor		=	100;
	
	/**
	 * Constructs a pool with Constants.DEFAULT_CAPACITY and Constants.DEFAULT_LOAD_FACOTR<br/>
	 * However, this pool can intelligently modify its <b>capacity</b> and <b>loadFactor</b> in runtime
	 * @author Nishi Inc.
	 * @since v1.0.0
	 */
	public BigNumberPool(){}
	
	/**
	 * Constructs a pool with given capacity and loadFactor<br/>
	 * However, this pool can intelligently modify its <b>capacity</b> and <b>loadFactor</b> 
	 * in runtime but would not go below the provided capacity and loadFactor
	 * @author Nishi Inc.
	 * @since v1.0.0
	 */
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
			this.updateIndex();
			return bignum;
		}
		return null;
	}
	
	@Override
	public BigNumber getBigNumber(Integer key) {
		if(this.getHold().containsKey(key)) {
			return this.getHold().get(key);
		}
		return getBigNumber();
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
	 * Puts given BigNumber on-hold
	 * @author Nishi Inc.
	 * @since v1.1.0
	 * @param bignum
	 * @return A 'key' by which this particular number can be grabbed from the pool<br>
	 * Just call getBigNumber(key)
	 */
	public Integer hold(BigNumber bignum) {
		Integer key = nextKey();
		this.getAllotted().remove(bignum);
		this.getHold().put(key, bignum);
		return key;
	}
	
	/**
	 * Manages BigNumber pool as per defined capacity,
	 * <br/>loadFactor and currently allotted and free BigNumbers.
	 */
	private void managePool() {
		this.updateIndex();
		for(;this.getFree().size() > this.getCapacity();) {
			for(BigNumber bignum : this.getFree()) {
				this.getFree().remove(bignum);
				break;
			}
		}
		
		int sizeOfAllotted = this.getAllotted().size();
		for(;(this.getFree().size()+sizeOfAllotted > this.getLimit()) && (!this.getFree().isEmpty());) {
			for(BigNumber bignum : this.getFree()) {
				this.getFree().remove(bignum);
				break;
			}
		}
		this.updateIndex();
	}
	
	/**
	 * This determines appropriate values of <b>capacity</b> and <b>loadFactor</b> in run-time and sets them up.<br/>
	 * It's the reason why <b>capacity</b> and <b>loadFactor</b> are not declared as constants.<br/><br/>
	 * Updates index as per current status of pool<br/>
	 * Frequent calls to this method ensure proper value of index variable
	 */
	private void updateIndex() {
		int freeSize 		= this.getFree().size();
		int allottedSize	= this.getAllotted().size() + this.getHold().size();
		
		if(allottedSize > this.getCapacity()) {
			if((freeSize+allottedSize)*5 > maxCap) {
				maxCap = (freeSize+allottedSize)*10;
			}
			this.setCapacity((freeSize+allottedSize)*5);
			this.setLoadFactor(Constants.DEFAULT_LOAD_FACTOR);
		}
		
		if(allottedSize+1 < this.getCapacity()*0.2) {
			if((allottedSize+1)*5 > maxCap) {
				maxCap = allottedSize*10;
			}
			this.setCapacity((allottedSize+1)*5);
			this.setLoadFactor(Constants.DEFAULT_LOAD_FACTOR);
		}
		
		if(allottedSize+freeSize > this.getLimit()*0.9) {
			if(this.getLimit() > maxCap) {
				maxCap = this.getLimit()*2;
			}
			this.setLoadFactor(this.getLoadFactor()+10);
			this.setCapacity(this.getLimit());
		}
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
			this.setLoadFactor(Constants.DEFAULT_LOAD_FACTOR);
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
			this.setCapacity(Constants.DEFAULT_CAPACITY);
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

	private Integer getLimit() {
		if(limit == null) {
			this.setLimit(this.getCapacity() * (1 + this.getLoadFactor()/100) + 1);
		}
		return limit;
	}

	private void setLimit(Integer limit) {
		this.limit = limit;
	}
	
	private HashMap<Integer, BigNumber> getHold() {
		if(hold == null) {
			hold = new HashMap<Integer, BigNumber>();
		}
		return hold;
	}
	
	private Integer nextKey() {
		return ++key;
	}
	
}
