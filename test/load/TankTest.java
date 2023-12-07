package load;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TankTest {

	private Double widthA = 5.0;
	private Double heightA = 2.0;
	private Double lengthA = 8.0;
	private Double weightA = 50.0;
	private Tank tankA;

	private Double widthB = 4.0;
	private Double heightB = 3.0;
	private Double lengthB = 10.0;
	private Double weightB = 120.0;
	private Tank tankB;

	@BeforeEach
	void setUp() {
		tankA = new Tank(lengthA, heightA, widthA, weightA);
		tankB = new Tank(lengthB, heightB, widthB, weightB);
	}

//	@Test
//	void testInicializationClassTank() {
//		assertEquals(heightA, tankA.getHeight());
//		assertEquals(heightB, tankB.getHeight());
//	}

	@Test
	void testGetWidth() {
		assertEquals(widthA, tankA.getWidth());
		assertEquals(widthB, tankB.getWidth());
	}

//	@Test
//	void testGetLength() {
//		assertEquals(lengthA, tankA.getLength());
//		assertEquals(lengthB, tankB.getLength());
//	}

	@Test
	void testGetWeight() {
		assertEquals(weightA, tankA.getWeight());
		assertEquals(weightB, tankB.getWeight());
	}

	@Test
	void testGetName() {
		assertEquals("Tank", tankB.getName());
	}

	@Test
	void testGetVolume() {
		// TankA
		Double expectedValueA = tankA.getWidth() * tankA.getLength() * tankA.getHeight();
		assertEquals(expectedValueA, tankA.getVolume());
		// TankB
		Double expectedValueB = tankB.getWidth() * tankB.getLength() * tankB.getHeight();
		assertEquals(expectedValueB, tankB.getVolume());
	}
}
