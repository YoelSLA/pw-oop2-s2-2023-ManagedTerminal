package filteredSearch.binary;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import filteredSearch.Filter;
import maritimeCircuit.MaritimeCircuit;

/**
 * Clase que representa al Operador Logico AND.
 * Extiende la superclase BinaryOperator.
 * Su constructor requiere 2 partes: leftFilter y rightFilter.
 * 
 * @author Gabriela Fascetta
 */
public class And extends BinaryOperator {

	public And(Filter leftFilter, Filter rightFilter) {
		super(leftFilter, rightFilter);
	}

	@Override
	public List<MaritimeCircuit> filterCircuits(List<MaritimeCircuit> maritimeCircuits) {
		List<MaritimeCircuit> leftFilterApplied = this.leftFilter.filterCircuits(maritimeCircuits);
		List<MaritimeCircuit> rightFilterApplied = this.rightFilter.filterCircuits(maritimeCircuits);
		
		
		Set<MaritimeCircuit> result = leftFilterApplied.stream()
				  .distinct()
				  .filter(rightFilterApplied::contains)
				  .collect(Collectors.toSet());
		
		
		return new ArrayList<MaritimeCircuit>(result);
	}

}
