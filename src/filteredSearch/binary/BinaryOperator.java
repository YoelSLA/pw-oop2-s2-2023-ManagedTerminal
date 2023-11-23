package filteredSearch.binary;

import java.util.List;

import filteredSearch.Filter;
import maritimeCircuit.MaritimeCircuit;

/**
 * Clase que representa un Operador Logico Binario.
 * 
 * Un operador binario requiere 2 partes: rightFilter y leftFilter.
 * 
 * @author Gabriela Fascetta
 */
public abstract class BinaryOperator implements Filter{
	
	protected Filter rightFilter;
	protected Filter leftFilter;

	public BinaryOperator(Filter leftFilter, Filter rightFilter) {
		this.leftFilter = leftFilter;
		this.rightFilter = rightFilter;
	}

	@Override
	public abstract List<MaritimeCircuit> filterCircuits(List<MaritimeCircuit> maritimeCircuits);

	public Filter getRightFilter() {
		return rightFilter;
	}

	public Filter getLeftFilter() {
		return leftFilter;
	}
}
