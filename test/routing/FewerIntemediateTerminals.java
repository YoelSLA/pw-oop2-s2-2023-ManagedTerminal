package routing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import maritimeCircuit.MaritimeCircuit;
import stretch.Stretch;
import terminal.ManagedTerminal;
import terminal.Terminal;

class FewerIntemediateTerminals {

	// TODO: REFACTORIZAR POR HERENCIA

	private ManagedTerminal buenosAires;
	// -------------------------------------------------------------
	private Terminal guayaquil;
	private Terminal montevideo;
	private Terminal lima;
	private Terminal valparaiso;
	private Terminal antofagasta;
	// ------------------------------------------------------------
	private Stretch buenosAiresValparaiso;
	private Stretch valparaisoLima;
	private Stretch limaGuayaquil;
	private Stretch guayaquilBuenosAires;
	private Stretch montevideoBuenosAires;
	private Stretch limaMontevideo;
	private Stretch valparaisoAntofagasta;
	private Stretch antofagastaLima;
	// -------------------------------------------------------------
	private MaritimeCircuit maritimeCircuitOne;
	private MaritimeCircuit maritimeCircuitTwo;
	// -------------------------------------------------------------
	private FewerIntermediateTerminals fewer; // SUT

	@BeforeEach
	void setUp() {
		// MANAGED TERMINAL
		buenosAires = mock(ManagedTerminal.class);
		// ------------------------------------------------------------------------------------------
		// TERMINAL
		guayaquil = mock(Terminal.class);
		montevideo = mock(Terminal.class);
		lima = mock(Terminal.class);
		valparaiso = mock(Terminal.class);
		antofagasta = mock(Terminal.class);
		// ------------------------------------------------------------------------------------------
		// STRETCH
		buenosAiresValparaiso = mock(Stretch.class);
		when(buenosAiresValparaiso.getOrigin()).thenReturn(buenosAires);
		when(buenosAiresValparaiso.getDestiny()).thenReturn(valparaiso);

		valparaisoLima = mock(Stretch.class);
		when(valparaisoLima.getOrigin()).thenReturn(valparaiso);
		when(valparaisoLima.getDestiny()).thenReturn(lima);

		limaGuayaquil = mock(Stretch.class);
		when(limaGuayaquil.getOrigin()).thenReturn(lima);
		when(limaGuayaquil.getDestiny()).thenReturn(guayaquil);

		guayaquilBuenosAires = mock(Stretch.class);
		when(guayaquilBuenosAires.getOrigin()).thenReturn(guayaquil);
		when(guayaquilBuenosAires.getDestiny()).thenReturn(buenosAires);

		montevideoBuenosAires = mock(Stretch.class);
		when(montevideoBuenosAires.getOrigin()).thenReturn(montevideo);
		when(montevideoBuenosAires.getDestiny()).thenReturn(buenosAires);

		limaMontevideo = mock(Stretch.class);
		when(limaMontevideo.getOrigin()).thenReturn(lima);
		when(limaMontevideo.getDestiny()).thenReturn(montevideo);

		valparaisoAntofagasta = mock(Stretch.class);
		when(valparaisoAntofagasta.getOrigin()).thenReturn(valparaiso);
		when(valparaisoAntofagasta.getDestiny()).thenReturn(antofagasta);

		antofagastaLima = mock(Stretch.class);
		when(antofagastaLima.getOrigin()).thenReturn(antofagasta);
		when(antofagastaLima.getDestiny()).thenReturn(lima);
		// ------------------------------------------------------------------------------------------
		// MARITIME CIRCUIT
		maritimeCircuitOne = mock(MaritimeCircuit.class);
		when(maritimeCircuitOne.getStretches()).thenReturn(List.of(montevideoBuenosAires, buenosAiresValparaiso,
				valparaisoAntofagasta, antofagastaLima, limaMontevideo));
		when(maritimeCircuitOne.getPositionOf(buenosAires)).thenReturn(1);
		when(maritimeCircuitOne.getPositionOf(lima)).thenReturn(4);

		maritimeCircuitTwo = mock(MaritimeCircuit.class);
		when(maritimeCircuitTwo.getStretches())
				.thenReturn(List.of(buenosAiresValparaiso, valparaisoLima, limaGuayaquil, guayaquilBuenosAires));
		when(maritimeCircuitTwo.getPositionOf(buenosAires)).thenReturn(0);
		when(maritimeCircuitTwo.getPositionOf(lima)).thenReturn(2);
		// ------------------------------------------------------------------------------------------
		// FEWER INTERMEDIATE TERMINALS
		fewer = new FewerIntermediateTerminals();
	}

	@Test
	void bestCircuitBetween_ReturnsCircuitWithLowestIntermediateTerminals() throws Exception {
		assertEquals(maritimeCircuitTwo,
				fewer.bestCircuitBetween(buenosAires, lima, List.of(maritimeCircuitOne, maritimeCircuitTwo)));
	}

}
