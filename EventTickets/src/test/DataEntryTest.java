package test;

import static org.junit.Assert.*;

import org.junit.Test;

import views.DataEntryAndDisplay;

public class DataEntryTest {

	@Test
	public void testEnteredValueX() {

		DataEntryAndDisplay objDataEntryAndDisplay = new DataEntryAndDisplay();
		int resultValueX = objDataEntryAndDisplay.getInitialXAxisValue();
		assertTrue("x-axis value is out of range: " + resultValueX, -20 <= resultValueX && resultValueX <= 20);
		
	}
	
	@Test
	public void testEnteredValueY() {

		DataEntryAndDisplay objDataEntryAndDisplay = new DataEntryAndDisplay();
		int resultValueY = objDataEntryAndDisplay.getInitialYAxisValue();
		assertTrue("y-axis value is out of range: " + resultValueY, -20 <= resultValueY && resultValueY <= 20);
		
	}

}
