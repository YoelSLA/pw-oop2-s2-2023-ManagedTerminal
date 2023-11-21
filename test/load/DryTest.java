package load;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test de unidad para la clase Dry (SUT).
 * @author Gabriela Fascetta
 */
class DryTest {

	Double widthA = 5.0;
	Double heightA = 2.0;
	Double lengthA = 8.0;
	Double weightA = 50.0;
	Dry dryA = new Dry(widthA,heightA,lengthA,weightA);
	
	
	Double widthB = 4.0;
	Double heightB = 3.0;
	Double lengthB = 10.0;
	Double weightB = 120.0;
	Dry dryB = new Dry(widthB,heightB,lengthB,weightB);
	
	
	@Test
	void testInicializationClassReefer() {
		//reeferA
		assertEquals(widthA, dryA.getWidth());
		assertEquals(heightA, dryA.getHeight());
		assertEquals(lengthA, dryA.getLength());
		assertEquals(weightA, dryA.getWeight());
		//reeferB
		assertEquals(widthB, dryB.getWidth());
		assertEquals(heightB, dryB.getHeight());
		assertEquals(lengthB, dryB.getLength());
		assertEquals(weightB, dryB.getWeight());
	}
	
	@Test
	void testVolume() {
		Double expectedValueA = dryA.getWidth()*dryA.getLength()*dryA.getHeight();
		assertEquals(expectedValueA, dryA.getVolume());
		Double expectedValueB = dryB.getWidth()*dryB.getLength()*dryB.getHeight();
		assertEquals(expectedValueB, dryB.getVolume());
	}

}
