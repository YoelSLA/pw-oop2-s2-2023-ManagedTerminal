package trip;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import ship.Ship;
import stretch.Stretch;
import maritimeCircuit.MaritimeCircuit;
import terminal.Terminal;
import terminal.ManagedTerminal;
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
	private Terminal firstTerminal;
	private Terminal lastTerminal;

	public Trip(MaritimeCircuit maritimeCircuit, Ship ship, LocalDate startDate, Terminal firstTerminal, Terminal lastTerminal) {
		this.maritimeCircuit = maritimeCircuit;
		this.ship = ship;
		this.startDate = startDate;
		this.firstTerminal = firstTerminal;
		this.lastTerminal = lastTerminal;
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
		Stretch startSection = maritimeCircuit.getSections().stream().filter(section -> section.getOrigin().equals(this.firstTerminal)).findFirst().get();
		LocalDate currentDate = startDate;
		System.out.print("Section Start Date:" + this.startDate + "Origin Terminal:" + this.firstTerminal);
		this.maritimeCircuit.getSections().forEach(section -> {
			System.out.print("Section End Date:" + currentDate + "Destination Terminal:" + this.nextTerminalFrom(firstTerminal)); // no se como sumarle a un objeto de tipo Date un objeto de tipo Duration
		});
	};
		
	
	public Terminal nextTerminalFrom(Terminal terminal) {
		Stretch nextTerminal = this.getMaritimeCircuit().getSections().stream()
				.filter(section -> section.getOrigin().equals(terminal))
	            .findFirst().orElseThrow(() -> new NoSuchElementException("No se encontró una sección con el terminal de origen proporcionado."));
		return nextTerminal.getDestiny();
	}
	
	//.filter(section -> section.getOrigin().equals(terminal)) //parametrizo el metodo para poder usarla en el schedule() 
	//	.findFirst().get().getDestiny();
	
	public Double getCost() {
		Double price = 0.0;
		for (Stretch s: sectionsInThisTrip()) {
			if(! s.getOrigin().equals(lastTerminal)) {
				price += s.getPrice();
			}
		}
		return price;
	}
	
	public List<Stretch> sectionsInThisTrip(){
		
		List<Stretch> allSections = maritimeCircuit.getSections();
		List<Stretch> sectionsAhead = new ArrayList<>();
		List<Terminal> terminalsInCircuit = allSections.stream().map(s-> s.getOrigin()).collect(Collectors.toList());
		int indexFirstTerminal = terminalsInCircuit.indexOf(firstTerminal);
		int indexLastTerminal = terminalsInCircuit.indexOf(lastTerminal);
		for (int i = indexFirstTerminal; i < indexLastTerminal; i++) {
		    sectionsAhead.add(allSections.get(i));
		}
		
		return sectionsAhead;
	}
}
