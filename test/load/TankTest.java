package load;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test de unidad para la clase Tank (SUT).
 * @author Gabriela Fascetta
 */
class TankTest {

	Double widthA = 5.0;
	Double heightA = 2.0;
	Double lengthA = 8.0;
	Double weightA = 50.0;
	Tank tankA = new Tank(widthA,heightA,lengthA,weightA);
	
	
	Double widthB = 4.0;
	Double heightB = 3.0;
	Double lengthB = 10.0;
	Double weightB = 120.0;
	Tank tankB = new Tank(widthB,heightB,lengthB,weightB);
	
	
	@Test
	void testInicializationClassReefer() {
		//reeferA
		assertEquals(widthA, tankA.getWidth());
		assertEquals(heightA, tankA.getHeight());
		assertEquals(lengthA, tankA.getLength());
		assertEquals(weightA, tankA.getWeight());
		assertEquals(0, tankA.getEnergyConsumption());
		//reeferB
		assertEquals(widthB, tankB.getWidth());
		assertEquals(heightB, tankB.getHeight());
		assertEquals(lengthB, tankB.getLength());
		assertEquals(weightB, tankB.getWeight());
		assertEquals(0, tankB.getEnergyConsumption());
	}
	
	@Test
	void testVolume() {
		Double expectedValueA = tankA.getWidth()*tankA.getLength()*tankA.getHeight();
		assertEquals(expectedValueA, tankA.getVolume());
		Double expectedValueB = tankB.getWidth()*tankB.getLength()*tankB.getHeight();
		assertEquals(expectedValueB, tankB.getVolume());
	}
}
