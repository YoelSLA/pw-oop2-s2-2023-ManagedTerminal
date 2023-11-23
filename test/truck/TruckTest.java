package truck;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TruckTest {

	private Truck truck;
	
	@BeforeEach
	void setUp() {
		truck = new Truck("Ford", "Fiesta","ABC123");	
	}
	
	@Test
	void getBrand() {
		assertEquals("Ford", truck.getBrand());
	}
	
	@Test
	void getModel() {
		assertEquals("Fiesta", truck.getModel());
	}
	
	@Test
	void getPatent() {
		assertEquals("ABC123", truck.getPatent());
	}

}
