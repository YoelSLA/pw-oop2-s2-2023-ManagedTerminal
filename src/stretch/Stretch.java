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

	public Boolean hasATerminal(Terminal terminal) {
		return origin.equals(terminal) || destiny.equals(terminal);
	}

	public void setTime(Duration time) {
		this.time = time;
	}

	public void setPrice(Double price) throws Exception {
		if (price < 0) {
			throw new RuntimeException("Te price muste be positive.");
		}
		this.price = price;
	}

}
