package position;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PositionTest {
	private Position position; // SUT

	@BeforeEach
	void setUp() {
		position = new Position(-34.5795823299825, -58.373877081937);
	}

	@Test
	void x() {
		assertEquals(-34.5795823299825, position.getLatitude());
	}

	@Test
	void x1() {
		assertEquals(-58.373877081937, position.getLongitude());
	}

	@Test
	void x2() {
		// Exercise
		position.setPosition(-34.90367464769443, -56.21226754075775);
		// Assert
		assertEquals(-34.90367464769443, position.getLatitude());
		assertEquals(-56.21226754075775, position.getLongitude());
	}

	@Test
	void x3() {
		// Set Up
		final Position POSITION_BUENOS_AIRES = new Position(-34.5795823299825, -58.373877081937);
		final Position POSITION_MONTEVIDEO = new Position(-34.90367464769443, -56.21226754075775);
		// Assert
		assertEquals(243, position.distanceInKilometersBetween(POSITION_BUENOS_AIRES, POSITION_MONTEVIDEO));
	}
}
