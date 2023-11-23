package filteredSearch.criteria;

import java.util.List;

import filteredSearch.Filter;
import maritimeCircuit.MaritimeCircuit;

/**
 * Interfaz que provee el protocolo para un Criterio de filtro.
 * @author Gabriela Fascetta.
 * */
public interface Criteria extends Filter{
	@Override
	public List<MaritimeCircuit> filterCircuits(List<MaritimeCircuit> maritimeCircuits);
}
