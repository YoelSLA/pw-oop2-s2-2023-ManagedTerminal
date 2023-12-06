package trip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import maritimeCircuit.MaritimeCircuit;
import ship.Ship;
import stretch.Stretch;
import terminal.ManagedTerminal;
import terminal.Terminal;

class TripTest {

	private ManagedTerminal buenosAires;
	// -------------------------------------------------------------
	private Terminal valparaiso;
	private Terminal lima;
	private Terminal guayaquil;
	// -------------------------------------------------------------
	private Stretch buenosAiresValparaiso;
	private Stretch valparaisoLima;
	private Stretch limaGuayaquil;
	private Stretch guayaquilBuenosAires;
	// -------------------------------------------------------------
	private MaritimeCircuit maritimeCircuitOne;
	// -------------------------------------------------------------
	private Ship bismarck;
	// -------------------------------------------------------------
	private LocalDateTime startDate;
	// -------------------------------------------------------------
	private Trip tripOne; // SUT

	@BeforeEach
	void setUp() {

		buenosAires = mock(ManagedTerminal.class);
		// -------------------------------------------------------------------------------------------
		valparaiso = mock(Terminal.class);
		lima = mock(Terminal.class);
		guayaquil = mock(Terminal.class);
		// -------------------------------------------------------------------------------------------
		buenosAiresValparaiso = mock(Stretch.class);
		when(buenosAiresValparaiso.getOrigin()).thenReturn(buenosAires);
		when(buenosAiresValparaiso.getDestiny()).thenReturn(valparaiso);
		when(buenosAiresValparaiso.getPrice()).thenReturn(1.040);
		when(buenosAiresValparaiso.getTime()).thenReturn(Duration.ofHours(13));

		valparaisoLima = mock(Stretch.class);
		when(valparaisoLima.getOrigin()).thenReturn(valparaiso);
		when(valparaisoLima.getDestiny()).thenReturn(lima);
		when(valparaisoLima.getPrice()).thenReturn(2.024);
		when(valparaisoLima.getTime()).thenReturn(Duration.ofHours(9));

		limaGuayaquil = mock(Stretch.class);
		when(limaGuayaquil.getOrigin()).thenReturn(lima);
		when(limaGuayaquil.getDestiny()).thenReturn(guayaquil);
		when(limaGuayaquil.getPrice()).thenReturn(1.821);
		when(limaGuayaquil.getTime()).thenReturn(Duration.ofHours(6));

		guayaquilBuenosAires = mock(Stretch.class);
		when(guayaquilBuenosAires.getOrigin()).thenReturn(guayaquil);
		when(guayaquilBuenosAires.getDestiny()).thenReturn(buenosAires);
		when(guayaquilBuenosAires.getPrice()).thenReturn(2.192);
		when(guayaquilBuenosAires.getTime()).thenReturn(Duration.ofHours(36));
		// -------------------------------------------------------------------------------------------
		maritimeCircuitOne = mock(MaritimeCircuit.class);
		when(maritimeCircuitOne.getStretches())
				.thenReturn(List.of(buenosAiresValparaiso, valparaisoLima, limaGuayaquil, guayaquilBuenosAires));
		when(maritimeCircuitOne.originTerminal()).thenReturn(buenosAires);
		// -------------------------------------------------------------------------------------------
		bismarck = mock(Ship.class);
		// -------------------------------------------------------------------------------------------
		startDate = LocalDateTime.of(2023, Month.NOVEMBER, 12, 12, 00); // 12-11-23 | 12:00 Hs.
		// -------------------------------------------------------------------------------------------
		tripOne = new Trip(maritimeCircuitOne, bismarck, startDate);
	}

	@Test
	void testGetMaritimeCircuit_ReturnsCorrectMaritimeCircuit() {
		assertEquals(maritimeCircuitOne, tripOne.getMaritimeCircuit());
	}

	@Test
	void testGetShip_ReturnsCorrectShip() {
		assertEquals(bismarck, tripOne.getShip());
	}

	@Test
	void testGetStartDate_ReturnsCorrectStartDate() {
		assertEquals(startDate, tripOne.getStartDate());
	}

	@Test
	void testCalculateEstimatedArrivalDateToTerminal_ReturnsCorrectEstimatedArrivalDate() {
		// SetUp
		when(maritimeCircuitOne.calculateTotalHoursBetweenTerminals(buenosAires, valparaiso)).thenReturn(13);
		when(maritimeCircuitOne.getPositionOf(buenosAires)).thenReturn(0);
		when(maritimeCircuitOne.getPositionOf(valparaiso)).thenReturn(1);
		
		// Assert
		LocalDateTime arrivalDate = LocalDateTime.of(2023, Month.NOVEMBER, 13, 01, 00);
		assertEquals(arrivalDate, tripOne.calculateEstimatedArrivalDateToTerminal(valparaiso)); 
		// 13-11-23 | 01:00 Hs.
	}
	
	@Test
	void getOriginTerminal_ReturnsCorrectOriginTerminal() {
		assertEquals(buenosAires, tripOne.getOriginTerminal());
	}
	
	@Test
	void getNextTerminal_ReturnsCorrectNextTerminal() {
		// Set Up
		when(maritimeCircuitOne.getNextTerminalInCircuit(buenosAires)).thenReturn(valparaiso);
		
		// Assert
		assertEquals(valparaiso, tripOne.getNextTerminal(buenosAires));
	}
	
	@Test
	void hasTerminal_ReturnsTrueForExistingTerminal() {
		// Set Up
		when(maritimeCircuitOne.hasATerminal(buenosAires)).thenReturn(true);
		
		// Assert
		assertTrue(tripOne.hasTerminal(buenosAires));
	}
	
	
}
