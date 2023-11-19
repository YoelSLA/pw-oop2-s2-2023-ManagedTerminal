package phase;

public final class Working extends Phase {

	@Override
	public Departing nextPhase() {
		return new Departing();
	}
}
