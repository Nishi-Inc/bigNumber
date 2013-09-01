package org.bigNumber;

import java.util.LinkedList;
import java.util.List;
import org.bigNumber.BigNumber;
import org.bigNumber.common.models.BigNumberFactory;

public class BigNumberPool implements BigNumberFactory {
	
	private List<BigNumber> allotted;
	private List<BigNumber> free;
	private int				capacity;
	private int				loadFactor;
	
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
		this.getAllotted().remove(bignum);
		this.getFree().add(bignum);
		this.managePool();
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
		if(loadFactor == 0) {
			this.setLoadFactor(40);
		}
		return loadFactor;
	}

	private void setLoadFactor(int loadFactor) {
		this.loadFactor = loadFactor;
	}

	private int getCapacity() {
		if(capacity == 0) {
			this.setCapacity(10);
		}
		return capacity;
	}

	private void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
}
