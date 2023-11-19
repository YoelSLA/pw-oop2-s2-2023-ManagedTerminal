package phase;

public final class Arrived implements Phase {

	@Override
	public Working nextPhase() {
		return new Working();
	}
}
