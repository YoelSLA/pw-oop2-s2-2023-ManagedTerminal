package filteredSearch;

import java.util.List;

import maritimeCircuit.MaritimeCircuit;

/**
 * Interfaz que representa un filtro.
 * Requiere implementar el algoritmo de filtro de circuitos a partir de una lista de los mismos.
 * 
 * @author Gabriela Fascetta
 */
public interface Filter {
	public List<MaritimeCircuit> filterCircuits(List<MaritimeCircuit> maritimeCircuits);
}
