package geographicalPosition;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import position.Position;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GeographicalPositionTest {
	private Position geographicalPosition;
	
	@BeforeEach
	void setUp() {
		geographicalPosition = new Position(-34.61315, -58.37723);
	}
	
	@Test
	void getShipGeographicalPosition() {
		assertEquals(-34.61315, geographicalPosition.getLatitude());
		assertEquals(-58.37723, geographicalPosition.getLongitude());
	}
	
	@Test
	void updateGeographicalPosition() {
		//excercise
		geographicalPosition.updateGeographicalPosition(12.345, 23.456);
		//verify
		assertEquals(12.345, geographicalPosition.getLatitude());
		assertEquals(23.456, geographicalPosition.getLongitude());

	}
}
