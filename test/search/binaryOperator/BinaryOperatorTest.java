package search.binaryOperator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;

import maritimeCircuit.MaritimeCircuit;
import search.criteria.Criteria;
import search.selection.DestinationTerminal;
import search.selection.selectionDate.ArrivalDate;
import search.selection.selectionDate.DepartureDate;
import stretch.Stretch;
import terminal.ManagedTerminal;
import terminal.Terminal;
import trip.Trip;

public class BinaryOperatorTest {

	protected ManagedTerminal buenosAires;
	// -------------------------------------------------------------
	protected Terminal guayaquil;
	protected Terminal montevideo;
	protected Terminal lima;
	protected Terminal valparaiso;
	protected Terminal rioDeJaneiro;
	// ------------------------------------------------------------
	protected Stretch buenosAiresValparaiso;
	protected Stretch valparaisoLima;
	protected Stretch limaGuayaquil;
	protected Stretch guayaquilBuenosAires;
	protected Stretch montevideoBuenosAires;
	protected Stretch limaMontevideo;
	// -------------------------------------------------------------
	protected MaritimeCircuit maritimeCircuitOne;
	protected MaritimeCircuit maritimeCircuitTwo;
	// -------------------------------------------------------------
	protected Trip tripOne;
	protected Trip tripTwo;
	// -------------------------------------------------------------
	protected ArrivalDate arrivalDate;
	// -------------------------------------------------------------
	protected DestinationTerminal destinationTerminal;
	// -------------------------------------------------------------
	protected DepartureDate departureDate;

	@BeforeEach
	void setUp() {
		buenosAires = mock(ManagedTerminal.class);
		// ------------------------------------------------------------------------------------------
		guayaquil = mock(Terminal.class);
		montevideo = mock(Terminal.class);
		lima = mock(Terminal.class);
		valparaiso = mock(Terminal.class);
		rioDeJaneiro = mock(Terminal.class);
		// ------------------------------------------------------------------------------------------
		buenosAiresValparaiso = mock(Stretch.class);
		when(buenosAiresValparaiso.getOrigin()).thenReturn(buenosAires);
		when(buenosAiresValparaiso.getDestiny()).thenReturn(valparaiso);
		when(buenosAiresValparaiso.getTime()).thenReturn(Duration.ofHours(13));

		valparaisoLima = mock(Stretch.class);
		when(valparaisoLima.getOrigin()).thenReturn(valparaiso);
		when(valparaisoLima.getDestiny()).thenReturn(lima);
		when(valparaisoLima.getTime()).thenReturn(Duration.ofHours(9));

		limaGuayaquil = mock(Stretch.class);
		when(limaGuayaquil.getOrigin()).thenReturn(lima);
		when(limaGuayaquil.getDestiny()).thenReturn(guayaquil);
		when(limaGuayaquil.getTime()).thenReturn(Duration.ofHours(6));

		guayaquilBuenosAires = mock(Stretch.class);
		when(guayaquilBuenosAires.getOrigin()).thenReturn(guayaquil);
		when(guayaquilBuenosAires.getDestiny()).thenReturn(buenosAires);
		when(guayaquilBuenosAires.getTime()).thenReturn(Duration.ofHours(36));

		montevideoBuenosAires = mock(Stretch.class);
		when(montevideoBuenosAires.getOrigin()).thenReturn(montevideo);
		when(montevideoBuenosAires.getDestiny()).thenReturn(buenosAires);
		when(montevideoBuenosAires.getTime()).thenReturn(Duration.ofHours(7));

		limaMontevideo = mock(Stretch.class);
		when(limaMontevideo.getOrigin()).thenReturn(lima);
		when(limaMontevideo.getDestiny()).thenReturn(montevideo);
		when(limaMontevideo.getTime()).thenReturn(Duration.ofHours(3));
		// ------------------------------------------------------------------------------------------
		maritimeCircuitOne = mock(MaritimeCircuit.class);
		when(maritimeCircuitOne.getStretches())
				.thenReturn(List.of(montevideoBuenosAires, buenosAiresValparaiso, valparaisoLima, limaMontevideo));

		maritimeCircuitTwo = mock(MaritimeCircuit.class);
		when(maritimeCircuitTwo.getStretches())
				.thenReturn(List.of(buenosAiresValparaiso, valparaisoLima, limaGuayaquil, guayaquilBuenosAires));
		// ------------------------------------------------------------------------------------------
		tripOne = mock(Trip.class);
		when(tripOne.getMaritimeCircuit()).thenReturn(maritimeCircuitOne);
		when(tripOne.getStartDate()).thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 01, 10, 00));
		// 01-11-23 | 10:00 Hs.

		tripTwo = mock(Trip.class);
		when(tripTwo.getMaritimeCircuit()).thenReturn(maritimeCircuitTwo);
		when(tripTwo.getStartDate()).thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 12, 12, 00));
		// 12-11-23 | 12:00 Hs.
		// ------------------------------------------------------------------------------------------
		departureDate = new DepartureDate(Criteria.EQUALS, LocalDate.of(2023, Month.NOVEMBER, 12), buenosAires); // 12-11-23
		// ------------------------------------------------------------------------------------------
		when(tripOne.hasTerminal(buenosAires)).thenReturn(true);
		when(tripOne.calculateEstimatedArrivalDateToTerminal(buenosAires))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 01, 14, 00)); // 01-11-23 | 14:00 Hs.

		when(tripTwo.calculateEstimatedArrivalDateToTerminal(buenosAires))
				.thenReturn(LocalDateTime.of(2023, Month.NOVEMBER, 12, 12, 00)); // 12-11-23 | 12:00 Hs.
	}

}
