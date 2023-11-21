package section;

import java.time.Duration;
import java.util.Objects;

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

	public boolean isItHasATerminal(Terminal terminal) {
		return destiny.equals(terminal) || getOrigin().equals(terminal);
	}

	@Override
	public int hashCode() {
		return Objects.hash(destiny, origin, price, time);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Section other = (Section) obj;
		return Objects.equals(destiny, other.destiny) && Objects.equals(origin, other.origin)
				&& Objects.equals(price, other.price) && Objects.equals(time, other.time);
	}
}
