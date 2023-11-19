package phase;

public final class Arrived extends Phase {
	
	@Override
	public Working nextPhase() {
		return new Working();
	}
}
