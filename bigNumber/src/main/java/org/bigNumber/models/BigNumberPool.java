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


package org.bigNumber.models;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import org.bigNumber.common.interfaces.BigNumberFactory;

/**
 * A BigNumberPool which keeps BigNumbers and handles allocation
 * @author Nishi Incorporation
 * @since v1.0.0
 */
public class BigNumberPool implements BigNumberFactory {

    public static final int	DEFAULT_LOAD_FACTOR =	40;
    public static final int	DEFAULT_CAPACITY    =	5;

    private static int MIN_CAP                      =	1;
    private static int MAX_CAP                      =	100;
    private static int MIN_LOAD_FACTOR              =	0;
    private static int MAX_LOAD_FACTOR              =	100;
    private static int LIMIT_MULTIPLIER             =   2;
    private static int MIN_LOAD_PERCENTAGE          =   20;
    private static int MAX_LOAD_PERCENTAGE          =   90;
    private static int LOAD_FACTOR_INCREMENT        =   10;
    private static int MAX_CAP_INCREASE_MULTIPLIER  =   10;
    private static int MAX_CAP_TOLERANCE_MULTIPLIER =   5;

    private Integer						key;
	private Integer						limit;
    private Integer						capacity;
    private Integer						loadFactor;

    private List<BigNumber> 			free;
    private List<BigNumber> 			allotted;

    private TreeMap<Integer, BigNumber> hold;
	
	/**
	 * Constructs a pool with GlobalConstants.DEFAULT_CAPACITY and GlobalConstants.DEFAULT_LOAD_FACOTR<br/>
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
		BigNumberPool.MIN_CAP = capacity;
		this.setLoadFactor(loadFactor);
		BigNumberPool.MIN_LOAD_FACTOR = loadFactor;
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
			BigNumber result =  this.getHold().get(key);
			this.getHold().remove(key);
			this.getAllotted().add(result);
			return result;
		}
		return this.getBigNumber();
	}
	
	@Override
	public void destroy(BigNumber... bignums) {
		int size = bignums.length;
		for(int i=0; i<size; i++) {
			this.destroy(bignums[i]);
		}
	}
	
	/**
	 * Destroys BigNumber in pool
	 * @author Nishi Inc.
	 * @since v1.0.0
	 * @param bignum
	 */
	private void destroy(BigNumber bignum) {
		boolean removedFromAllotted = this.getAllotted().remove(bignum);
		boolean presentInHold;
		if(removedFromAllotted) {
			this.getFree().add(bignum);
			this.managePool();
            return;
		}
        presentInHold = this.getHold().containsValue(bignum);
        if(presentInHold) {
            Integer key = this.getKeyFromHold(bignum);
            this.getHold().remove(key);
            this.getFree().add(bignum);
            this.managePool();
        }
	}
	
	/**
	 * Puts given BigNumber on-hold.<br/>
	 * If the given number was not initially allocated, it's added to pool and put at hold.<br/>
	 * DO NOT FORGET TO DESTROY NUMBERS ON-HOLD.
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
		
		int sizeOfAllotted = this.getAllotted().size() + this.getHold().size();
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
			if((freeSize+allottedSize)*BigNumberPool.MAX_CAP_TOLERANCE_MULTIPLIER > MAX_CAP) {
				MAX_CAP = (freeSize+allottedSize)*BigNumberPool.MAX_CAP_INCREASE_MULTIPLIER;
			}
			this.setCapacity((freeSize+allottedSize)*BigNumberPool.MAX_CAP_TOLERANCE_MULTIPLIER);
			this.setLoadFactor(BigNumberPool.DEFAULT_LOAD_FACTOR);
		}
		
		if(allottedSize+1 < this.getCapacity()*(BigNumberPool.MIN_LOAD_PERCENTAGE/100)) {
			if((allottedSize+1)*BigNumberPool.MAX_CAP_TOLERANCE_MULTIPLIER > BigNumberPool.MAX_CAP) {
				BigNumberPool.MAX_CAP = allottedSize*BigNumberPool.MAX_CAP_INCREASE_MULTIPLIER;
			}
			this.setCapacity((allottedSize+1)*BigNumberPool.MAX_CAP_TOLERANCE_MULTIPLIER);
			this.setLoadFactor(BigNumberPool.DEFAULT_LOAD_FACTOR);
		}
		
		if(allottedSize+freeSize > this.getLimit()*(BigNumberPool.MAX_LOAD_PERCENTAGE/100)) {
			if(this.getLimit() > MAX_CAP) {
				MAX_CAP = this.getLimit()*BigNumberPool.LIMIT_MULTIPLIER;
			}
			this.setLoadFactor(this.getLoadFactor() + BigNumberPool.LOAD_FACTOR_INCREMENT);
			this.setCapacity(this.getLimit());
		}
	}
	
	/**
	 * Linear search
	 * @param bignum
	 * @return key corresponding to the given BigNumber
	 */
	private Integer getKeyFromHold(BigNumber bignum) {
		Iterator<Integer> iter = this.getHold().keySet().iterator();
		while(iter.hasNext()) {
			Integer SetElement = iter.next();
			if(this.getHold().get(SetElement).equals(bignum)) {
				return SetElement;
			}
		}
		return null;
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
			this.setLoadFactor(BigNumberPool.DEFAULT_LOAD_FACTOR);
		}
		
		if(loadFactor < MIN_LOAD_FACTOR) {
			this.setLoadFactor(MIN_LOAD_FACTOR);
		} else if(loadFactor > MAX_LOAD_FACTOR) {
			this.setLoadFactor(MAX_LOAD_FACTOR);
		}
		
		return loadFactor;
	}

	private void setLoadFactor(int loadFactor) {
		this.loadFactor = loadFactor;
	}

	private int getCapacity() {
		if(capacity == null) {
			this.setCapacity(BigNumberPool.DEFAULT_CAPACITY);
		}
		
		if(capacity < MIN_CAP) {
			this.setCapacity(MIN_CAP);
		} else if(capacity > MAX_CAP) {
			this.setCapacity(MAX_CAP);
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
	
	private TreeMap<Integer, BigNumber> getHold() {
		if(hold == null) {
			hold = new TreeMap<Integer, BigNumber>();
		}
		return hold;
	}
	
	private Integer nextKey() {
		return ++key;
	}
	
}
