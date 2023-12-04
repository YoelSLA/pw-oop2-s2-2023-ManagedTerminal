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

	/**
	 * Obtiene la siguiente terminal en el circuito después de una terminal dada.
	 *
	 * @param terminal Terminal de referencia.
	 * @return Siguiente terminal en el circuito.
	 */
	public Terminal getNextTerminalInCircuit(Terminal terminal) {
		Integer indexTerminal = originTerminals().indexOf(terminal);
		return originTerminals().get(indexTerminal + 1);
	}

	/**
	 * Devuelve la posición de la terminal en la lista de tramos.
	 *
	 * @param terminal La terminal cuya posición se busca.
	 * @return La posición de la terminal en la lista de tramos, o -1 si no se
	 *         encuentra.
	 * @throws Exception Si no se encuentra la terminal, se lanza una excepción.
	 */
	public Integer getPositionOf(Terminal terminal) throws Exception {
		return stretches.indexOf(stretches.stream().filter(s -> s.hasTerminal(terminal)).findFirst()
				.orElseThrow(() -> new Exception("Terminal not found")));
	}

	/**
	 * Obtiene el precio total del circuito sumando los precios de cada tramo.
	 *
	 * @return Precio total del circuito.
	 */
	public Double getPrice() {
		return stretches.stream().mapToDouble(Stretch::getPrice).sum();
	}

	public List<Stretch> getStretches() {
		return stretches;
	}

	/**
	 * Obtiene la duración total del circuito sumando las duraciones de cada tramo.
	 *
	 * @return Duración total del circuito en horas.
	 */
	public Integer getTime() {
		return stretches.stream().mapToInt(s -> calculateHoursRounded(s.getTime())).sum();
	}

	/**
	 * Calcula la duración total en horas entre dos terminales específicas en el
	 * circuito.
	 *
	 * @param origin  Terminal de origen.
	 * @param destiny Terminal de destino.
	 * @return Duración total en horas entre las terminales dadas.
	 * @throws Exception
	 */
	public Integer calculateTotalHoursBetweenTerminals(Terminal origin, Terminal destiny) throws Exception {
		long totalNanos = stretches.stream().skip(getPositionOf(origin))
				.limit(getPositionOf(destiny) - getPositionOf(origin)).mapToLong(s -> s.getTime().toNanos()).sum();
		return calculateHoursRounded(Duration.ofNanos(totalNanos));
	}

	/**
	 * Verifica si el circuito tiene una terminal específica.
	 *
	 * @param destiny Terminal que se desea verificar si está en el circuito.
	 * @return true si la terminal está en el circuito, false de lo contrario.
	 */
	public Boolean hasATerminal(Terminal destiny) {
		return originTerminals().stream().anyMatch(terminal -> terminal.equals(destiny));
	}

	public Terminal originTerminal() {
		return stretches.get(0).getOrigin();
	}

	/**
	 * Calcula las horas redondeadas a partir de una duración dada.
	 *
	 * @param time Duración a convertir a horas.
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
