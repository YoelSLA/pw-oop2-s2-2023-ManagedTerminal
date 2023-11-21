package trip;
import maritimeCircuit.MaritimeCircuit;
import terminal.Terminal;
import terminal.ManagedTerminal;
import section.Section;
import ship.Ship;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TripTest {
	
	private Trip trip; //SUT
	private MaritimeCircuit maritimeCircuit; //DOC
	private Terminal terminalBuenosAires; //DOC
	private Terminal terminalMontevideo; //DOC
	private Section sectionBuenosAiresMontevideo; //DOC
	private Ship ship; //DOC
	
	@BeforeEach
	void setUp() {
		Date fechaActual = new Date(24, 5, 23);
		maritimeCircuit = mock(MaritimeCircuit.class);
		ship = mock(Ship.class);
		terminalBuenosAires = mock(Terminal.class);
		terminalMontevideo = mock(Terminal.class);
		sectionBuenosAiresMontevideo = new Section(terminalBuenosAires, terminalMontevideo, 50.0, Duration.ofHours(2));
		trip = new Trip(maritimeCircuit, ship, fechaActual);
		
	}
	
	
	@Test
	void getTripMaritimeCircuit() {
		// Verify
		assertEquals(maritimeCircuit, trip.getMaritimeCircuit());
	}

	@Test
	void getTripShip() {
		// Verify
		assertEquals(ship, trip.getShip());
	}
	
	@Test
	void getNextTerminalFrom() {
		//Exercise
		
		maritimeCircuit.addSection(sectionBuenosAiresMontevideo);
		
		// Verify
		assertEquals(terminalMontevideo, trip.nextTerminalFrom(terminalBuenosAires));
	}

}
