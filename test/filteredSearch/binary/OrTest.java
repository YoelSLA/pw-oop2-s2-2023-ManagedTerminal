package filteredSearch.binary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import filteredSearch.criteria.DestinationTerminal;
import maritimeCircuit.MaritimeCircuit;

/**
 * Test de unidad para la clase Or Operator (SUT).
 * Interactua con la clase DestinationTerminal, MaritimeCircuit (DOCs);
 * @author Gabriela Fascetta
 */
class OrTest {

	private DestinationTerminal filter1 = mock(DestinationTerminal.class);
	private DestinationTerminal filter2 = mock(DestinationTerminal.class);
	private DestinationTerminal filter3 = mock(DestinationTerminal.class);
	private DestinationTerminal filter4 = mock(DestinationTerminal.class);
	private BinaryOperator OrSearchX = new Or(filter1, filter2);
	private BinaryOperator OrSearchY = new Or(filter3, filter4);
	
	@Test
	void classOrOperatorInitializationTest() {
		assertEquals(filter1, OrSearchX.getLeftFilter());
		assertEquals(filter2, OrSearchX.getRightFilter());
		assertEquals(filter3, OrSearchY.getLeftFilter());
		assertEquals(filter4, OrSearchY.getRightFilter());
	}
	
	@Test
	void testGivenAMaritimeCircuitList_WhenFilterOrIsApplied_returnListThatSatifyAnyFilter() {
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
		
		List<MaritimeCircuit> expectedListFilter2 = new ArrayList<MaritimeCircuit>();
		expectedListFilter2.add(mc2);
		expectedListFilter2.add(mc3);
		
		List<MaritimeCircuit> expectedListOr = new ArrayList<MaritimeCircuit>();
		expectedListOr.add(mc2);
		expectedListOr.add(mc3);
		
		when(filter1.filterCircuits(listMC)).thenReturn(expectedListFilter1);
		when(filter2.filterCircuits(listMC)).thenReturn(expectedListFilter2);
		var ls = OrSearchX.filterCircuits(listMC);
		assertEquals(2, ls.size());
		assertTrue(ls.contains(mc2));
		assertTrue(ls.contains(mc3));
	}

	@Test
	void testGivenAMaritimeCircuitListWithNoMatch_WhenFilterOrIsApplied_returnEmptyList() {
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
		
		List<MaritimeCircuit> expectedListFilter2 = new ArrayList<MaritimeCircuit>();
		
		List<MaritimeCircuit> expectedListOr = new ArrayList<MaritimeCircuit>();
		
		when(filter1.filterCircuits(listMC)).thenReturn(expectedListFilter1);
		when(filter2.filterCircuits(listMC)).thenReturn(expectedListFilter2);
		var ls = OrSearchX.filterCircuits(listMC);
		assertEquals(expectedListOr.size(), ls.size());
	}
}
