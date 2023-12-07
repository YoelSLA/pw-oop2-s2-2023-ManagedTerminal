package routing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import stretch.Stretch;
import terminal.Terminal;

class FewerIntemediateTerminals extends RoutingTest {

	private Terminal antofagasta;
	// ------------------------------------------------------------
	private Stretch valparaisoAntofagasta;
	private Stretch antofagastaLima;
	// -------------------------------------------------------------
	private FewerIntermediateTerminals fewer;

	@BeforeEach
	void setUp() throws Exception {
		super.setUp();
		// ------------------------------------------------------------------------------------------
		antofagasta = mock(Terminal.class);
		// ------------------------------------------------------------------------------------------
		valparaisoAntofagasta = mock(Stretch.class);
		when(valparaisoAntofagasta.getOrigin()).thenReturn(valparaiso);
		when(valparaisoAntofagasta.getDestiny()).thenReturn(antofagasta);

		antofagastaLima = mock(Stretch.class);
		when(antofagastaLima.getOrigin()).thenReturn(antofagasta);
		when(antofagastaLima.getDestiny()).thenReturn(lima);
		// ------------------------------------------------------------------------------------------
		when(maritimeCircuitOne.getStretches()).thenReturn(List.of(montevideoBuenosAires, buenosAiresValparaiso,
				valparaisoAntofagasta, antofagastaLima, limaMontevideo));
		when(maritimeCircuitOne.getPositionOf(buenosAires)).thenReturn(1);
		when(maritimeCircuitOne.getPositionOf(lima)).thenReturn(4);

		when(maritimeCircuitTwo.getPositionOf(buenosAires)).thenReturn(0);
		when(maritimeCircuitTwo.getPositionOf(lima)).thenReturn(2);
		// ------------------------------------------------------------------------------------------
		fewer = new FewerIntermediateTerminals();
	}

	@Test
	void bestCircuitBetween_ReturnsCircuitWithLowestIntermediateTerminals() throws Exception {
		assertEquals(maritimeCircuitTwo,
				fewer.bestCircuitBetween(buenosAires, lima, List.of(maritimeCircuitOne, maritimeCircuitTwo)));
	}

}
