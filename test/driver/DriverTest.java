package driver;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DriverTest {
	
	private Driver yoel;

	@BeforeEach
	void setUp() {
		yoel = new Driver("42341174", "Yoel");
	}

	@Test
	void testADriverIsCreated() {
		// Assert
		assertEquals("42341174", yoel.getDni());
		assertEquals("Yoel", yoel.getName());
	}

}
