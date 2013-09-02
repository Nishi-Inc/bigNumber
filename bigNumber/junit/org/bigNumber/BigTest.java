package org.bigNumber;

import java.util.ArrayList;
import java.util.List;
import org.bigNumber.BigNumber;
import org.bigNumber.common.services.exceptions.IncompatibleCharacterException;
import junit.framework.TestCase;

public class BigTest extends TestCase {
	
	private BigNumberPool bigPool;
	
	/**
	 * @return A pool of BigNumber variables
	 */
	private BigNumberPool getBigPool() {
		if(bigPool != null) {
			return bigPool;
		}
		bigPool = new BigNumberPool();
		return bigPool;
	}
	
	/*public void testGetValue() {
		BigNumber no = this.getBigPool().getBigNumber();
		try {
			no.setValue("6343");
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}
		List<Character> result        = no.getValue();
		List<Character> correctResult = new ArrayList<Character>();
		correctResult.add('6');
		correctResult.add('3');
		correctResult.add('4');
		correctResult.add('3');
		assertTrue(isEqual(result, correctResult));
		this.getBigPool().destroy(no);
	}

	public void testRoundOff() {
		BigNumber no = this.getBigPool().getBigNumber();
		try {
			no.setValue("-34.2896");
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}
		no.roundOff(3);
		assertTrue(no.toString().contentEquals("-34.290"));
		this.getBigPool().destroy(no);
	}
	
	private boolean isEqual(List<Character> result, List<Character> correctResult) {
		int size = result.size();
		if(size != correctResult.size()) {
			return false;
		}
		for(int i=0; i<size; i++) {
			if(result.get(i) != correctResult.get(i)) {
				return false;
			}
		}
		return true;
	}
	
	public void testPow() {
		BigNumber num = this.getBigPool().getBigNumber();
		BigNumber result = this.getBigPool().getBigNumber();
		try {
			num.setValue("34.1");
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}
		result = BigNumber.pow(num, 2);
		//num.pow(2);
		assertTrue(result.toString().contentEquals("1162.81"));
		this.getBigPool().destroy(num,result);
	}
	
	public void testCompareTo() {
		BigNumber num1 = this.getBigPool().getBigNumber();
		BigNumber num2 = this.getBigPool().getBigNumber();
		try {
			num1.setValue("-34.1");
			num2.setValue("33.1123");
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}
		assertTrue(num1.compareTo(num2) == -1);
		this.getBigPool().destroy(num1, num2);
	}
	
	public void testAbsolute() {
		BigNumber num1 = this.getBigPool().getBigNumber();
		BigNumber num2 = this.getBigPool().getBigNumber();
		try {
			num1.setValue("1111");
			num2.setValue("-2.0");
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}
		num1.absolute();
		num2.absolute();
		assertTrue(num1.toString().contentEquals("1111"));
		assertTrue(num2.toString().contentEquals("2.0"));
		this.getBigPool().destroy(num1, num2);
	}
	
	public void testModulus() {
		BigNumber num1 = this.getBigPool().getBigNumber();
		BigNumber num2 = this.getBigPool().getBigNumber();
		try {
			num1.setValue("-34");
			num2.setValue("3");
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}
		num1.modulus(num2);
		assertTrue(num1.toString().contentEquals("1"));
		this.getBigPool().destroy(num1, num2);
	}*/
	
	public void testSum() {
		BigNumber[] num = new BigNumber[5];
		for(int i=0; i<5; i++) {
			num[i] = this.getBigPool().getBigNumber();
		}
		BigNumber result;
		
		try {
			num[0].setValue("-34.1");
			num[1].setValue("33.1123");
			num[3].setValue("3");
			num[4].setValue("3");
			num[2].setValue("3");
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}
		result = BigNumber.sum(num);
		assertTrue(result.toString().contentEquals("8.0123"));
		this.getBigPool().destroy(num);
	}
	
	public void testMultiply() {
		BigNumber[] num = new BigNumber[5];
		for(int i=0; i<5; i++) {
			num[i] = this.getBigPool().getBigNumber();
		}
		BigNumber result;
		
		try {
			num[0].setValue("-34.1");
			num[1].setValue("33.1123");
			num[3].setValue("3");
			num[4].setValue("3");
			num[2].setValue("3");
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}
		result = BigNumber.multiply(num);
		assertTrue(result.toString().contentEquals("-30486.49461"));
		this.getBigPool().destroy(num);
	}
	
	public void testDivide() {
		BigNumber num1 = this.getBigPool().getBigNumber();
		BigNumber num2 = this.getBigPool().getBigNumber();
		BigNumber result;
		
		try {
			num1.setValue("33.1123");
			num2.setValue("4");
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}
		result = BigNumber.divide(num1, num2);
		assertTrue(result.toString().contentEquals("8.278075"));
		this.getBigPool().destroy(num1, num2);
	}
	
	public void testSub() {
		BigNumber num1 = this.getBigPool().getBigNumber();
		BigNumber num2 = this.getBigPool().getBigNumber();
		BigNumber result;
		
		try {
			num1.setValue("33.1123");
			num2.setValue("4");
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}
		result = BigNumber.sub(num1, num2);
		assertTrue(result.toString().contentEquals("29.1123"));
		this.getBigPool().destroy(num1, num2);
	}
	
}
