package client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class ShipperTest {
	
	private Shipper shipper;
	
	@BeforeEach
	void setUp() {
		shipper = new Shipper(12567892, "Carla Ruiz");
	}
	
	@Test
	void getDni() {
		assertEquals(12567892, shipper.getDni());
	}
	
	@Test
	void getName() {
		assertEquals("Carla Ruiz", shipper.getName());
	}

}
