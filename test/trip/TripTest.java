package trip;
import maritimeCircuit.MaritimeCircuit;
import terminal.Terminal;
import terminal.ManagedTerminal;
import section.Section;
import ship.Ship;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
		LocalDate fechaActual = LocalDate.of(2023, 5, 24);
		maritimeCircuit = mock(MaritimeCircuit.class);
		ship = mock(Ship.class);
		terminalBuenosAires = mock(Terminal.class);
		terminalMontevideo = mock(Terminal.class);
		sectionBuenosAiresMontevideo = new Section(terminalBuenosAires, terminalMontevideo, 50.0, Duration.ofHours(2));
		trip = new Trip(maritimeCircuit, ship, fechaActual, terminalBuenosAires);
		
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
		List<Section> sectionsBAM = new ArrayList<Section>();
		sectionsBAM.add(sectionBuenosAiresMontevideo);
		//Exercise
		when(maritimeCircuit.getSections()).thenReturn(sectionsBAM);
		// Verify
		assertEquals(terminalBuenosAires, trip.nextTerminalFrom(terminalMontevideo));
	}

}
