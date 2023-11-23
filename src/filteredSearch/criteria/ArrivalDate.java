package filteredSearch.criteria;

import java.time.LocalDate;
import java.util.List;

import maritimeCircuit.MaritimeCircuit;

/**
 * Clase que representa el criterio de filtro por fecha de llegada, implementa interfaz Criteria.
 * Permite filtrar circuitos maritimos.
 * @author Gabriela Fascetta.
 * */
public class ArrivalDate implements Criteria {
	
	private LocalDate searchedDate;
	
	public ArrivalDate(LocalDate date) {
		this.searchedDate = date;
	}

	@Override
	public List<MaritimeCircuit> filterCircuits(List<MaritimeCircuit> maritimeCircuits) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public LocalDate getSearchedDate() {return searchedDate;}
}
