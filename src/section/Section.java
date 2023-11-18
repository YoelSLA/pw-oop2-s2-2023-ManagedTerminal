package section;

import java.time.Duration;

import terminal.Terminal;

public class Section {

	private Terminal destiny;
	private Terminal origin;
	private Double price;
	private Duration time;

	public Section(Terminal destiny, Terminal origin, Double price, Duration time) {
		this.destiny = destiny;
		this.origin = origin;
		this.price = price;
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

}
