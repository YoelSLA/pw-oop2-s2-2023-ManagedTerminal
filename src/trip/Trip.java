package trip;
import java.time.Duration;
import java.time.LocalDate;
import java.util.NoSuchElementException;

import ship.Ship;
import maritimeCircuit.MaritimeCircuit;
import terminal.Terminal;
import terminal.ManagedTerminal;
import section.Section;
/**
 * @author alejandrabesel
 * Clase que representa a un viaje.
 * 
 * Esta clase gestiona la información del viaje.
 * 
 */

public class Trip {

	private LocalDate startDate;
	private Ship ship;
	private MaritimeCircuit maritimeCircuit;
	private Terminal managedTerminal;

	public Trip(MaritimeCircuit maritimeCircuit, Ship ship, LocalDate startDate, Terminal managedTerminal) {
		this.maritimeCircuit = maritimeCircuit;
		this.ship = ship;
		this.startDate = startDate;
		this.managedTerminal = managedTerminal;
	}
	
	/** @author alejandrabesel
	 * return Devuelve el circuito marítimo que realiza el viaje.
	 * */
	public MaritimeCircuit getMaritimeCircuit() {
		return maritimeCircuit;
	}
	
	/** @author alejandrabesel
	 * return Devuelve la instancia de buque que hara ese viaje
	 * */
	public Ship getShip() {
		return ship;
	}
	
	/** @author alejandrabesel
	 * return Devuelve el dia de inicio del viaje
	 * */
	public LocalDate getStartDate() {
		return startDate;
	}
		
	/** @author alejandrabesel
	 * return Devuelve un cronograma con las fechas de arribo y partida a cada terminal
	 * 
	 * */
	public void schedule() {
		Section startSection = maritimeCircuit.getSections().stream().filter(section -> section.getOrigin().equals(this.managedTerminal)).findFirst().get();
		LocalDate currentDate = startDate;
		System.out.print("Section Start Date:" + this.startDate + "Origin Terminal:" + this.managedTerminal);
		this.maritimeCircuit.getSections().forEach(section -> {
			System.out.print("Section End Date:" + currentDate + "Destination Terminal:" + this.nextTerminalFrom(managedTerminal)); // no se como sumarle a un objeto de tipo Date un objeto de tipo Duration
		});
	};
		
	
	public Terminal nextTerminalFrom(Terminal terminal) {
		Section nextTerminal = this.getMaritimeCircuit().getSections().stream()
				.filter(section -> section.getOrigin().equals(terminal))
	            .findFirst().orElseThrow(() -> new NoSuchElementException("No se encontró una sección con el terminal de origen proporcionado."));
		return nextTerminal.getDestiny();
	}
	
	//.filter(section -> section.getOrigin().equals(terminal)) //parametrizo el metodo para poder usarla en el schedule() 
	//	.findFirst().get().getDestiny();
}
