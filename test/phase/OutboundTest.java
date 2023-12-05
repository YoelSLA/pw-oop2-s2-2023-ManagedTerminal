package phase;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import geographicalPosition.GeographicalPosition;
import routing.Routing;
import ship.Ship;
import terminal.ManagedTerminal;
import static org.mockito.Mockito.*;
class OutboundTest {

	private Outbound currentPhase; //SUT
	private Inbound nextPhase; //DOC
	private GeographicalPosition shipOneGeographicalPosition;
	private GeographicalPosition shipTwoGeographicalPosition;
	private Ship shipOne;
	private Ship shipTwo;
	private ManagedTerminal managedTerminal;
	private Routing routing;
	
	
	
	@BeforeEach
	void setUp() {
		currentPhase = new Outbound();
		nextPhase = new Inbound();
		shipOneGeographicalPosition = new GeographicalPosition(-35.0289, -58.37723);
		shipTwoGeographicalPosition = new GeographicalPosition(-36.0289, -58.37723);
		shipOne = new Ship("La Perla Negra", "IMO1234567", currentPhase, shipOneGeographicalPosition);
		shipTwo = new Ship("La Perla Blanca", "IMO1234568", currentPhase, shipTwoGeographicalPosition);
		routing = mock(Routing.class);
		managedTerminal = new ManagedTerminal("Terminal Buenos Aires", routing);
	}
	
	@Test 
	void getDistanceBetweenShipAndTerminal() {
		assertEquals(49.96278321615795, currentPhase.getDistanceBetween(shipOne, managedTerminal));
	}
	
	@Test
	void updatePhaseToInbound() {
		assertEquals(nextPhase.getClass(), currentPhase.updatePhase(shipOne, managedTerminal).getClass());  
	}
	
	@Test
	void updatePhaseToOutbound() {
		assertEquals(currentPhase.getClass(), currentPhase.updatePhase(shipTwo, managedTerminal).getClass());  
	}
}
