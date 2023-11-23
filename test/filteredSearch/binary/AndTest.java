package filteredSearch.binary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import filteredSearch.criteria.DestinationTerminal;
import maritimeCircuit.MaritimeCircuit;


/**
 * Test de unidad para la clase And Operator (SUT).
 * Interactua con la clase DestinationTerminal, MaritimeCircuit (DOCs);
 * @author Gabriela Fascetta
 */
class AndTest {

	private DestinationTerminal filter1 = mock(DestinationTerminal.class);
	private DestinationTerminal filter2 = mock(DestinationTerminal.class);
	private DestinationTerminal filter3 = mock(DestinationTerminal.class);
	private DestinationTerminal filter4 = mock(DestinationTerminal.class);
	private BinaryOperator andSearchX = new And(filter1, filter2);
	private BinaryOperator andSearchY = new And(filter3, filter4);
	
	@Test
	void classAndOperatorInitializationTest() {
		assertEquals(filter1, andSearchX.getLeftFilter());
		assertEquals(filter2, andSearchX.getRightFilter());
		assertEquals(filter3, andSearchY.getLeftFilter());
		assertEquals(filter4, andSearchY.getRightFilter());
	}
	
	@Test
	void testGivenAMaritimeCircuitList_WhenFilterAndIsApplied_returnListThatSatifyBothFilters() {
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
		
		List<MaritimeCircuit> expectedListFilter1 = new ArrayList<MaritimeCircuit>();
		expectedListFilter1.add(mc2);
		expectedListFilter1.add(mc3);
		
		List<MaritimeCircuit> expectedListFilter2 = new ArrayList<MaritimeCircuit>();
		expectedListFilter2.add(mc2);
		expectedListFilter2.add(mc3);
		expectedListFilter2.add(mc5);
		
		List<MaritimeCircuit> expectedListAnd = new ArrayList<MaritimeCircuit>();
		expectedListAnd.add(mc2);
		expectedListAnd.add(mc3);
		
		when(filter1.filterCircuits(listMC)).thenReturn(expectedListFilter1);
		when(filter2.filterCircuits(listMC)).thenReturn(expectedListFilter2);
		var ls = andSearchX.filterCircuits(listMC);
		assertEquals(2, ls.size());
		assertTrue(ls.contains(mc2));
		assertTrue(ls.contains(mc3));
	}
}
