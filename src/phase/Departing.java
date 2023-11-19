package phase;

public final class Departing extends Phase {

	@Override
	public Outbound nextPhase() {
		return new Outbound();
	}
}

