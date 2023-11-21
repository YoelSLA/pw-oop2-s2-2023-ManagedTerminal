package load;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test de unidad para la clase Reefer (SUT).
 * @author Gabriela Fascetta
 */
class ReeferTest {
	
	Double widthA = 5.0;
	Double heightA = 2.0;
	Double lengthA = 8.0;
	Double weightA = 50.0;
	int energyA = 100;
	Reefer reeferA = new Reefer(widthA,heightA,lengthA,weightA, energyA);
	
	
	Double widthB = 4.0;
	Double heightB = 3.0;
	Double lengthB = 10.0;
	Double weightB = 120.0;
	int energyB = 150;
	Reefer reeferB = new Reefer(widthB,heightB,lengthB,weightB, energyB);
	
	
	@Test
	void testInicializationClassReefer() {
		//reeferA
		assertEquals(widthA, reeferA.getWidth());
		assertEquals(heightA, reeferA.getHeight());
		assertEquals(lengthA, reeferA.getLength());
		assertEquals(weightA, reeferA.getWeight());
		assertEquals((double)energyA, reeferA.getEnergyConsumption());
		//reeferB
		assertEquals(widthB, reeferB.getWidth());
		assertEquals(heightB, reeferB.getHeight());
		assertEquals(lengthB, reeferB.getLength());
		assertEquals(weightB, reeferB.getWeight());
		assertEquals((double)energyB, reeferB.getEnergyConsumption());
		
	}
	
	@Test
	void testVolume() {
		Double expectedValueA = reeferA.getWidth()*reeferA.getLength()*reeferA.getHeight();
		assertEquals(expectedValueA, reeferA.getVolume());
		Double expectedValueB = reeferB.getWidth()*reeferB.getLength()*reeferB.getHeight();
		assertEquals(expectedValueB, reeferB.getVolume());
	}
}
