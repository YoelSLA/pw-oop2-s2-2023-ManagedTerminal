package phase;

public final class Departing implements Phase {

	@Override
	public Outbound nextPhase() {
		return new Outbound();
	}
}

