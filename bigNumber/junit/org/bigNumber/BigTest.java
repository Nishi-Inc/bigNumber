package org.bigNumber;

import java.util.ArrayList;
import java.util.List;

import org.bigNumber.BigNumber;
import org.bigNumber.common.services.exceptions.IncompatibleCharacterException;
import org.junit.Test;
import junit.framework.TestCase;

public class BigTest extends TestCase {
	
/*	@Test
	public void testGetValue() {
		BigNumber no = new BigNumber();
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
	}

	@Test
	public void testRoundOff() {
		BigNumber no = new BigNumber();
		try {
			no.setValue("-34.2896");
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}
		no.roundOff(3);
		assertTrue(no.toString().contentEquals("-34.290"));
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
		BigNumber num = new BigNumber();
		try {
			num = new BigNumber("34.1");
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}
		num.pow(2);
		assertTrue(num.toString().contentEquals("1162.81"));
	}
	
	public void testCompareTo() {
		BigNumber num1 = new BigNumber();
		BigNumber num2 = new BigNumber();
		try {
			num1 = new BigNumber("-34.1");
			num2 = new BigNumber("33.1123");
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}
		assertTrue(num1.compareTo(num2) == -1);
	}*/
	
	/*public void testAbsolute() {
		BigNumber num1 = new BigNumber();
		BigNumber num2 = new BigNumber();
		try {
			num1 = new BigNumber("1111");
			num2 = new BigNumber("-2.0");
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}
		num1.absolute();
		num2.absolute();
		assertTrue(num1.toString().contentEquals("1111"));
		assertTrue(num2.toString().contentEquals("2.0"));
	}*/
	
	public void testModulus() {
		BigNumber num1 = new BigNumber();
		BigNumber num2 = new BigNumber();
		try {
			num1 = new BigNumber("-34");
			num2 = new BigNumber("3");
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}
		num1.modulus(num2);
		assertTrue(num1.toString().contentEquals("1"));
	}
	
}
