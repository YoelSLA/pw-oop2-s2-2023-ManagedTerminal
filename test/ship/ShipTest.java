package ship;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import phase.*;
import position.GeographicalPosition;

class ShipTest {
	
	private Ship ship; //SUT
	private Phase phaseArrived; //DOC
	private Phase phaseWorking;
	private GeographicalPosition geographicalPosition; //SUT
	
	@BeforeEach
	void setUp() {
		phaseArrived = mock(Phase.class);
		phaseWorking = mock(Phase.class);
		geographicalPosition = mock(GeographicalPosition.class);
		ship = new Ship("La Perla Negra", "IMO1234567", phaseArrived, geographicalPosition);
		
	}
	
	@Test
	void getShipName() {
		assertEquals("La Perla Negra", ship.getName());
	}
	
	@Test
	void getShipImo() {
		assertEquals("IMO1234567", ship.getImo());
	}
	
	@Test
	void getShipPhase() {
		assertEquals(phaseArrived, ship.getPhase());
	}
	
	@Test
	void getGeographicalPosition() {
		assertEquals(geographicalPosition, ship.getPosition());
	}

}
