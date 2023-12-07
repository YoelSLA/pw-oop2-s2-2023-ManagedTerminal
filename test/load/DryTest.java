package load;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DryTest {

	private Dry dryA;
	private Dry dryB;

	private Double widthA = 5.0;
	private Double heightA = 2.0;
	private Double lengthA = 8.0;
	private Double weightA = 50.0;

	private Double widthB = 4.0;
	private Double heightB = 3.0;
	private Double lengthB = 10.0;
	private Double weightB = 120.0;

	@BeforeEach
	void setUp() {
		dryA = new Dry(heightA, lengthA, widthA, weightA);
		dryB = new Dry(heightB, lengthB, widthB, weightB);
	}

	@Test
	void testGetWidth() {
		assertEquals(widthA, dryA.getWidth());
		assertEquals(widthB, dryB.getWidth());
	}

	@Test
	void testGetLength() {
		assertEquals(lengthA, dryA.getLength());
		assertEquals(lengthB, dryB.getLength());
	}

	@Test
	void testGetHeigth() {
		assertEquals(heightA, dryA.getHeight());
		assertEquals(heightB, dryB.getHeight());
	}

	@Test
	void testGetWeight() {
		assertEquals(weightA, dryA.getWeight());
		assertEquals(weightB, dryB.getWeight());
	}

	@Test
	void testGetName() {
		assertEquals("Dry", dryA.getName());
	}

	@Test
	void testGetVolume() {
		// reeferA
		Double expectedValueA = dryA.getWidth() * dryA.getLength() * dryA.getHeight();
		assertEquals(expectedValueA, dryA.getVolume());

		// reeferB
		Double expectedValueB = dryB.getWidth() * dryB.getLength() * dryB.getHeight();
		assertEquals(expectedValueB, dryB.getVolume());
	}
	@Test
	void testConsumesEnergyFalse() {
		assertFalse(dryA.consumesElectricity());
	}
}
