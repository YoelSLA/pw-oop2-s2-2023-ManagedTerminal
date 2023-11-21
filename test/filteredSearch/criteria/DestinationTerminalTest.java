package filteredSearch.criteria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import maritimeCircuit.MaritimeCircuit;
import terminal.Terminal;


/**
 * Test de unidad para la clase DestinationTerminal (SUT).
 * Interactua con la clase MaritimeCircuit y Terminal (DOCs);
 * @author Gabriela Fascetta
 */
class DestinationTerminalTest {

	private DestinationTerminal criteriaA;
	private Terminal terminalA;
	
	private DestinationTerminal criteriaB;
	private Terminal terminalB;
	
	@BeforeEach
	void setUpBeforeClass(){
		criteriaA = new DestinationTerminal(terminalA);
		criteriaB = new DestinationTerminal(terminalB);
	}

	@Test
	void testClassInitialization() {
		assertEquals(terminalA, criteriaA.getDestinationTerminal());
		assertEquals(terminalB, criteriaB.getDestinationTerminal());
	}
	
	@Test
	void givenAMaritimeCircuitsList_whenFilterIsApplied_returnAListOfCircuitsContainingDestinationTerminal() {
		//set up
		MaritimeCircuit mc1 = mock(MaritimeCircuit.class);
		MaritimeCircuit mc2 = mock(MaritimeCircuit.class);
		MaritimeCircuit mc3 = mock(MaritimeCircuit.class);
		MaritimeCircuit mc4 = mock(MaritimeCircuit.class);
		MaritimeCircuit mc5 = mock(MaritimeCircuit.class);
		
		List<MaritimeCircuit> listMC = new ArrayList<MaritimeCircuit>();
		listMC.add(mc1);
		listMC.add(mc2);
		listMC.add(mc3);
		listMC.add(mc4);
		listMC.add(mc5);
		
		List<MaritimeCircuit> expectedListA = new ArrayList<MaritimeCircuit>();
		expectedListA.add(mc2);
		expectedListA.add(mc3);
				
		when(mc2.itHasASectionWhereItIs(terminalA)).thenReturn(true);
		when(mc3.itHasASectionWhereItIs(terminalA)).thenReturn(true);
		
		//execute and verify
		assertEquals(expectedListA,criteriaA.filterCircuits(listMC));
	}
	
	@Test
	void givenAMaritimeCircuitsEmptyList_whenFilterIsApplied_returnAnEmptyListOfCircuits() {
		//set up
		List<MaritimeCircuit> listMC = new ArrayList<MaritimeCircuit>();
		
		List<MaritimeCircuit> expectedListA = new ArrayList<MaritimeCircuit>();
		
		//execute and verify
		assertEquals(expectedListA,criteriaA.filterCircuits(listMC));
	}
	
	@Test
	void givenAMaritimeCircuitsListNotContainingDestinationTerminal_whenFilterIsApplied_returnAnEmptyListOfCircuit() {
		//set up
		MaritimeCircuit mc1 = mock(MaritimeCircuit.class);
		MaritimeCircuit mc2 = mock(MaritimeCircuit.class);
		MaritimeCircuit mc3 = mock(MaritimeCircuit.class);
		MaritimeCircuit mc4 = mock(MaritimeCircuit.class);
		MaritimeCircuit mc5 = mock(MaritimeCircuit.class);
		
		List<MaritimeCircuit> listMC = new ArrayList<MaritimeCircuit>();
		listMC.add(mc1);
		listMC.add(mc2);
		listMC.add(mc3);
		listMC.add(mc4);
		listMC.add(mc5);
		
		List<MaritimeCircuit> expectedListA = new ArrayList<MaritimeCircuit>();
		
		//execute and verify
		assertEquals(expectedListA,criteriaA.filterCircuits(listMC));
	}
}
