package routing;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;

import maritimeCircuit.MaritimeCircuit;
import stretch.Stretch;
import terminal.ManagedTerminal;
import terminal.Terminal;

class RoutingTest {

	protected ManagedTerminal buenosAires;
	// -------------------------------------------------------------
	protected Terminal guayaquil;
	protected Terminal montevideo;
	protected Terminal lima;
	protected Terminal valparaiso;
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

	@BeforeEach
	void setUp() throws Exception {
		buenosAires = mock(ManagedTerminal.class);
		// ------------------------------------------------------------------------------------------
		guayaquil = mock(Terminal.class);
		montevideo = mock(Terminal.class);
		lima = mock(Terminal.class);
		valparaiso = mock(Terminal.class);
		// ------------------------------------------------------------------------------------------
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
		// ------------------------------------------------------------------------------------------
		maritimeCircuitOne = mock(MaritimeCircuit.class);
		when(maritimeCircuitOne.getStretches())
				.thenReturn(List.of(montevideoBuenosAires, buenosAiresValparaiso, valparaisoLima, limaMontevideo));
		when(maritimeCircuitOne.hasATerminal(lima)).thenReturn(true);

		maritimeCircuitTwo = mock(MaritimeCircuit.class);
		when(maritimeCircuitTwo.getStretches())
				.thenReturn(List.of(buenosAiresValparaiso, valparaisoLima, limaGuayaquil, guayaquilBuenosAires));
		when(maritimeCircuitTwo.hasATerminal(lima)).thenReturn(true);
	}

}
