package maritimeCircuit;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import stretch.Stretch;
import terminal.Terminal;

public class MaritimeCircuit {

	private List<Stretch> stretches;

	public MaritimeCircuit(List<Stretch> stretches) {
		this.stretches = stretches;
	}

	public List<Stretch> getStretches() {
		return stretches;
	}

	/**
	 * Calcula la duraci�n total en horas entre dos terminales espec�ficas en el
	 * circuito.
	 *
	 * @param origin  Terminal de origen.
	 * @param destiny Terminal de destino.
	 * @return Duraci�n total en horas entre las terminales dadas.
	 * @throws Exception
	 */
	public Integer calculateTotalHoursBetweenTerminals(Terminal origin, Terminal destiny) {
		long totalNanos = stretches.stream().skip(getPositionOf(origin))
				.limit(getPositionOf(destiny) - getPositionOf(origin))
				.mapToLong(s -> s.getTime().toNanos()).sum();
		return calculateHoursRounded(Duration.ofNanos(totalNanos));
	}

	/**
	 * Obtiene la siguiente terminal en el circuito despu�s de una terminal dada.
	 *
	 * @param terminal Terminal de referencia.
	 * @return Siguiente terminal en el circuito.
	 */
	public Terminal getNextTerminalInCircuit(Terminal terminal) {
		Integer indexTerminal = originTerminals().indexOf(terminal);
		return originTerminals().get(indexTerminal + 1);
	}

	/**
	 * Devuelve la posici�n de la terminal en la lista de tramos.
	 *
	 * @param terminal La terminal cuya posici�n se busca.
	 * @return La posici�n de la terminal en la lista de tramos, o -1 si no se
	 *         encuentra.
	 * @throws Exception Si no se encuentra la terminal, se lanza una excepci�n.
	 */
	public Integer getPositionOf(Terminal terminal) throws RuntimeException {
		List<Stretch> stretchesFiltered = stretches.stream().filter(s -> s.hasTerminal(terminal)).toList();
		if (stretchesFiltered.isEmpty()) {
			throw new RuntimeException("Terminal not found");
		}
		return stretches.indexOf(stretchesFiltered.get(0));
	}

	/**
	 * Obtiene el precio total del circuito sumando los precios de cada tramo.
	 *
	 * @return Precio total del circuito.
	 */
	public Double getPrice() {
		return stretches.stream().mapToDouble(Stretch::getPrice).sum();
	}

	public Double getPriceBetween(Terminal origin, Terminal destiny) {
		return stretches.stream().skip(getPositionOf(origin)).limit(getPositionOf(destiny) - getPositionOf(origin))
				.mapToDouble(s -> s.getPrice()).sum();
	}

	/**
	 * Obtiene la duraci�n total del circuito sumando las duraciones de cada tramo.
	 *
	 * @return Duraci�n total del circuito en horas.
	 */
	public Integer getTime() {
		return stretches.stream().mapToInt(s -> calculateHoursRounded(s.getTime())).sum();
	}

	/**
	 * Verifica si el circuito tiene una terminal espec�fica.
	 *
	 * @param destiny Terminal que se desea verificar si est� en el circuito.
	 * @return true si la terminal est� en el circuito, false de lo contrario.
	 */
	public Boolean hasATerminal(Terminal destiny) {
		return originTerminals().stream().anyMatch(terminal -> terminal.equals(destiny));
	}

	public Terminal originTerminal() {
		return stretches.get(0).getOrigin();
	}

	/**
	 * Calcula las horas redondeadas a partir de una duraci�n dada.
	 *
	 * @param time Duraci�n a convertir a horas.
	 * @return Horas redondeadas.
	 */
	private Integer calculateHoursRounded(Duration time) {
		return (int) Math.round(time.toNanos() / (double) Duration.ofHours(1).toNanos());
	}

	/**
	 * Obtiene la lista de terminales de origen del circuito, incluyendo la terminal
	 * inicial al final de la lista.
	 *
	 * @return Lista de terminales de origen.
	 */
	private List<Terminal> originTerminals() {
		List<Terminal> origins = new ArrayList<>(stretches.stream().map(Stretch::getOrigin).toList());
		origins.add(stretches.get(0).getOrigin());
		return origins;
	}

}
