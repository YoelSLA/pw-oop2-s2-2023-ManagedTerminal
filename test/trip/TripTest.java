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
import org.mockito.Mockito;

import maritimeCircuit.MaritimeCircuit;
import ship.Ship;
import stretch.Stretch;
import terminal.ManagedTerminal;
import terminal.Terminal;

class TripTest {

	private ManagedTerminal buenosAires;
//-------------------------------------------------------------
	private Terminal valparaiso;
	private Terminal montevideo;
//-------------------------------------------------------------
	private Stretch valparaisoBuenosAires;
	private Stretch buenosAiresMontevideo;
	private Stretch montevideoValparaiso;
//-------------------------------------------------------------
	private MaritimeCircuit circuitValparaisoToMontevideo;
//-------------------------------------------------------------
	private Ship bismarck;
//-------------------------------------------------------------
	private LocalDateTime startDate;
//-------------------------------------------------------------
	private Trip tripValparaisoToMontevideo; // SUT
//-------------------------------------------------------------

	@BeforeEach
	void setUp() {
		// MANAGED TERMINAL
		buenosAires = mock(ManagedTerminal.class);
		when(buenosAires.getName()).thenReturn("Puerto de Buenos Aires");
//-------------------------------------------------------------		
		// TERMINAL
		valparaiso = mock(Terminal.class);
		when(valparaiso.getName()).thenReturn("Puerto de Valparaiso");

		montevideo = mock(Terminal.class);
		when(montevideo.getName()).thenReturn("Puerto de Montevideo");
//-------------------------------------------------------------		
		// STRETCH
		valparaisoBuenosAires = mock(Stretch.class);
		when(valparaisoBuenosAires.getOrigin()).thenReturn(valparaiso);
		when(valparaisoBuenosAires.getDestiny()).thenReturn(buenosAires);
		when(valparaisoBuenosAires.getTime()).thenReturn(Duration.ofHours(12));

		buenosAiresMontevideo = mock(Stretch.class);
		when(buenosAiresMontevideo.getOrigin()).thenReturn(buenosAires);
		when(buenosAiresMontevideo.getDestiny()).thenReturn(montevideo);
		when(buenosAiresMontevideo.getTime()).thenReturn(Duration.ofHours(3));

		montevideoValparaiso = mock(Stretch.class);
		when(montevideoValparaiso.getOrigin()).thenReturn(montevideo);
		when(montevideoValparaiso.getDestiny()).thenReturn(valparaiso);
		when(montevideoValparaiso.getTime()).thenReturn(Duration.ofHours(22));
//-------------------------------------------------------------		
		// MARITIME CIRCUIT
		circuitValparaisoToMontevideo = mock(MaritimeCircuit.class);
		when(circuitValparaisoToMontevideo.getStretchs())
				.thenReturn(List.of(valparaisoBuenosAires, buenosAiresMontevideo, montevideoValparaiso));
		when(circuitValparaisoToMontevideo.originTerminals())
				.thenReturn(List.of(valparaiso, buenosAires, montevideo, valparaiso));
		when(circuitValparaisoToMontevideo.originTerminal()).thenReturn(valparaiso);
//-------------------------------------------------------------		
		// SHIP
		bismarck = mock(Ship.class);
//-------------------------------------------------------------		
		// TRIP
		startDate = LocalDateTime.of(2023, Month.DECEMBER, 01, 10, 0); // 01-12-23 | 10:00 Hs.
		tripValparaisoToMontevideo = new Trip(circuitValparaisoToMontevideo, bismarck, startDate);
	}

	@Test
	void getMaritimeCircuit_ShouldReturnCorrectCircuit_ForNewlyCreatedTripValparaisoToMontevideo() {
		assertEquals(circuitValparaisoToMontevideo, tripValparaisoToMontevideo.getMaritimeCircuit());
	}

	@Test
	void getShip_ShouldReturnCorrectShip_ForNewlyCreatedTripValparaisoToMontevideo() {
		assertEquals(bismarck, tripValparaisoToMontevideo.getShip());
	}

	@Test
	void getStartDate_ShouldReturnCorrectStartDate_ForNewlyCreatedTripValparaisoToMontevideo() {
		assertEquals(startDate, tripValparaisoToMontevideo.getStartDate());
	}

	@Test
	void testHasATerminal_ShouldReturnTrue_ForTripValparaisoToMontevideoWhenLookingForMontevideo() {
		// SetUp
		when(circuitValparaisoToMontevideo.hasATerminal(Mockito.eq(montevideo))).thenReturn(true);
		// Assert
		assertTrue(tripValparaisoToMontevideo.hasATerminal(montevideo));
	}

	@Test
	void testDateArrivedToDestiny_ShouldReturnCorrectDateTime_ForTripValparaisoToMontevideo() {
		// SetUp
		when(circuitValparaisoToMontevideo.originTerminal()).thenReturn(valparaiso);
		when(circuitValparaisoToMontevideo.calculateTimeBetween(valparaiso, buenosAires)).thenReturn(12);
		// Assert
		assertEquals(LocalDateTime.of(2023, Month.DECEMBER, 01, 22, 0),
				tripValparaisoToMontevideo.dateArrivedToDestiny(buenosAires)); // 01-12-23 | 22:00 Hs.
	}

	@Test
	void testOriginTerminal_ShouldReturnValparaiso_ForTripValparaisoToMontevideo() {
		assertEquals(valparaiso, tripValparaisoToMontevideo.originTerminal());
	}

	@Test
	void nextTerminalOf_ShouldReturnBuenosAires_ForValparaiso_ForTripValparaisoToMontevideo() {
		assertEquals(buenosAires, tripValparaisoToMontevideo.nextTerminalOf(valparaiso));
	}

}
