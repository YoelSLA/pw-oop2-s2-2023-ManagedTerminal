package filteredSearch.criteria;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import maritimeCircuit.MaritimeCircuit;
import shippingCompany.ShippingCompany;
import terminal.ManagedTerminal;

/**
 * Clase que representa el criterio de filtro por Fecha de salida, implementa interfaz Criteria.
 * Permite filtrar circuitos maritimos.
 * @author Gabriela Fascetta.
 * */
public class DepartureDate implements Criteria {
	
	private LocalDate searchedDate;
	private ManagedTerminal terminal;

	public DepartureDate(LocalDate date, ManagedTerminal terminal) {
		this.searchedDate = date;
		this.terminal = terminal;
	}

	@Override
	public List<MaritimeCircuit> filterCircuits(List<MaritimeCircuit> maritimeCircuits) {
		/* precondicion: la lista de circuitos maritimos que recibe por parametro
		 * incluye a la terminal gestionada desde donde se realiza la consulta.
		 * */
		List<MaritimeCircuit> searchedCircuits = new ArrayList<>();
				
		for(ShippingCompany sc: terminal.getShippingCompanies()) {
			searchedCircuits.addAll(sc.getCircuitsWithTripsThatStartOn(searchedDate));
		}
		
		return searchedCircuits;
	}
	
	public LocalDate getSearchedDate() {return searchedDate;}
	public ManagedTerminal getManagedTerminal() {return terminal;}
}
