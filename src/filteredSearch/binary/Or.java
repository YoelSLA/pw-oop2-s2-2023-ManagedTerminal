package filteredSearch.binary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import filteredSearch.Filter;
import maritimeCircuit.MaritimeCircuit;

/**
 * Clase que representa al Operador Logico OR.
 * Extiende la superclase BinaryOperator.
 * Su constructor requiere 2 partes: leftFilter y rightFilter.
 * 
 * @author Gabriela Fascetta
 */
public class Or extends BinaryOperator {

	public Or(Filter leftFilter, Filter rightFilter) {
		super(leftFilter, rightFilter);
	}

	@Override
	public List<MaritimeCircuit> filterCircuits(List<MaritimeCircuit> maritimeCircuits) {
		
		List<MaritimeCircuit> leftFilterApplied = this.leftFilter.filterCircuits(maritimeCircuits);
		List<MaritimeCircuit> rightFilterApplied = this.rightFilter.filterCircuits(maritimeCircuits);
		
		
		Set<MaritimeCircuit> result = new HashSet<MaritimeCircuit>();
		result.addAll(leftFilterApplied);
		result.addAll(rightFilterApplied);
		
		
		return new ArrayList<MaritimeCircuit>(result);
	}

}
