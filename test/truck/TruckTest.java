package truck;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TruckTest {

	private Truck scaniaR;
	
	@BeforeEach
	void setUp() {
		scaniaR = new Truck("R", "Scania","ABC123");	
	}
	
	@Test
	void testATruckIsCreated() {
		assertEquals("R", scaniaR.getBrand());
		assertEquals("Scania", scaniaR.getModel());
		assertEquals("ABC123", scaniaR.getPatent());
	}

}
