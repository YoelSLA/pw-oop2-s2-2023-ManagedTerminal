package stretch;

import java.time.Duration;

import terminal.Terminal;

public class Stretch {

	private Terminal destiny;
	private Terminal origin;
	private Double price;
	private Duration time;

	public Stretch(Terminal origin, Terminal destiny, Double price, Duration time) throws Exception {
		this.origin = origin;
		this.destiny = destiny;
		setPrice(price);
		this.time = time;
	}

	public Terminal getDestiny() {
		return destiny;
	}

	public Terminal getOrigin() {
		return origin;
	}

	public Double getPrice() {
		return price;
	}

	public Duration getTime() {
		return time;
	}

	public void setTime(Duration time) {
		this.time = time;
	}

	public void setPrice(Double price) throws Exception {
		if (price < 0) {
			throw new RuntimeException("The price must be positive.");
		}
		this.price = price;
	}

	/**
	 * Verifica si una terminal está presente en el tramo.
	 *
	 * @param terminal La terminal a verificar.
	 * @return true si la terminal está en el tramo, false de lo contrario.
	 */
	public Boolean hasTerminal(Terminal terminal) {
		return origin.equals(terminal) || destiny.equals(terminal);
	}
}