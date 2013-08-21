package org.bigNumber;

import java.util.ArrayList;
import java.util.List;

import org.bigNumber.Big;
import org.bigNumber.common.services.exceptions.IncompatibleCharacterException;
import org.junit.Test;
import junit.framework.TestCase;

public class BigTest extends TestCase {
	
	@Test
	public void testGetValue() {
		Big no = new Big();
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
		Big no = new Big();
		try {
			no.setValue("-34.2896");
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}
		no.roundOff(2);
		List<Character> result        = no.getValue();
		List<Character> correctResult = new ArrayList<Character>();
		correctResult.add('-');
		correctResult.add('3');
		correctResult.add('4');
		correctResult.add('.');
		correctResult.add('2');
		correctResult.add('9');
		assertTrue(isEqual(result, correctResult));
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
	
}
