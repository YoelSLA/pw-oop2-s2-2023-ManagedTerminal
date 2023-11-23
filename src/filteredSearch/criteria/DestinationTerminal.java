package filteredSearch.criteria;

import java.util.List;
import java.util.stream.Collectors;

import maritimeCircuit.MaritimeCircuit;
import terminal.Terminal;

/**
 * Clase que representa el criterio de filtro por Terminal de Destino, implementa interfaz Criteria.
 * Permite filtrar circuitos maritimos.
 * @author Gabriela Fascetta.
 * */
public class DestinationTerminal implements Criteria {
	
	private Terminal destinationTerminal;

	public DestinationTerminal(Terminal destinationTerminal) {
		this.destinationTerminal= destinationTerminal;
	}

	@Override
	public List<MaritimeCircuit> filterCircuits(List<MaritimeCircuit> maritimeCircuits) {
		/* precondicion: la lista de circuitos maritimos que recibe por parametro
		 * incluye a la terminal gestionada desde donde se realiza la consulta.
		 * */
		return	maritimeCircuits.stream().filter(m -> m.itHasASectionWhereItIs(destinationTerminal)).collect(Collectors.toList());
	}
	
	public Terminal getDestinationTerminal() {
		return destinationTerminal;
	}
}
