package load;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReeferTest {

	private Double widthA = 5.0;
	private Double heightA = 2.0;
	private Double lengthA = 8.0;
	private Double weightA = 50.0;
	private Double energyA = 100.0;
	private Reefer reeferA;

	private Double widthB = 4.0;
	private Double heightB = 3.0;
	private Double lengthB = 10.0;
	private Double weightB = 120.0;
	private Double energyB = 150.0;
	private Reefer reeferB;

	@BeforeEach
	void setUp() {
		reeferA = new Reefer(lengthA, heightA, widthA, weightA, energyA);
		reeferB = new Reefer(lengthB, heightB, widthB, weightB, energyB);
	}

//	@Test
//	void testInicializationClassReefer() {
//		assertEquals(heightA, reeferA.getHeight());
//		assertEquals(heightB, reeferB.getHeight());
//
//	}

	@Test
	void getWidth() {
		assertEquals(widthA, reeferA.getWidth());
		assertEquals(widthB, reeferB.getWidth());
	}

//	@Test
//	void getLength() {
//		assertEquals(lengthA, reeferA.getLength());
//		assertEquals(lengthB, reeferB.getLength());		
//	}

	@Test
	void getWeight() {
		assertEquals(weightA, reeferA.getWeight());
		assertEquals(weightB, reeferB.getWeight());
	}

	@Test
	void testGetName() {
		assertEquals("Reefer", reeferA.getName());
	}

	@Test
	void getConsumption() {
		assertEquals(energyA, reeferA.getConsumptionkWh());
		assertEquals(energyB, reeferB.getConsumptionkWh());
	}
	
	@Test
	void setConsumption() {
		reeferA.setConsumption(energyA);
		assertEquals(energyA, reeferA.getConsumptionkWh());
	}
	
	@Test
	void consumesEnergy() {
		assertTrue(reeferA.consumesElectricity());
	}

	@Test
	void testVolume() {
		// reeferA
		Double expectedValueA = reeferA.getWidth() * reeferA.getLength() * reeferA.getHeight();
		assertEquals(expectedValueA, reeferA.getVolume());
		// reeferB
		Double expectedValueB = reeferB.getWidth() * reeferB.getLength() * reeferB.getHeight();
		assertEquals(expectedValueB, reeferB.getVolume());
	}
}
