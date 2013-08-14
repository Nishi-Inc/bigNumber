package org.bigNumber.common.models;

import java.util.ArrayList;
import java.util.List;

import org.bigNumber.common.services.exceptions.IncompatibleCharacterException;
import org.junit.Test;
import junit.framework.TestCase;

public class BigTest extends TestCase {
	
	/*private Big no1 = null;
	private Big no2 = null;
	
	protected Big getNo1() {
		if(no1 == null)
			return new Big();
		return no1;
	}
	
	protected Big getNo2() {
		if(no2 == null)
			return new Big();
		return no2;
	}
	
	protected Big setNo1() {
		if(no1 == null)
			return new Big();
		return no1;
	}*/
	
	@Test
	public void roundOffTest() {
		Big no = new Big();
		try {
			no.setValue("34.2896");
		} catch (IncompatibleCharacterException e) {
			e.printStackTrace();
		}
		List<Character> result        = no.getValue();
		List<Character> correctResult = new ArrayList<Character>();
		correctResult.add('3');
		correctResult.add('4');
		correctResult.add('.');
		correctResult.add('2');
		correctResult.add('9');
		assertTrue(result == correctResult);
	}
	
}
