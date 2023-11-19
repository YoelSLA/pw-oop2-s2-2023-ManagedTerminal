package phase;

public final class Working implements Phase {

	@Override
	public Departing nextPhase() {
		return new Departing();
	}
}