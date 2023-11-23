package driver;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import truck.Truck;

class DriverTest {

	private Driver driver;
	
	@BeforeEach
	void setUp() {
		driver = new Driver(21345856, "Dario Paz");	
	}
	
	void getDni() {
		assertEquals(21345856, driver.getDni());
	}
	
	void getName() {
		assertEquals("Dario Paz", driver.getName());
	}

}
