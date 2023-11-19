package phase;

public final class Inbound extends Phase {
	
	@Override
	public Arrived nextPhase() {
		return new Arrived();
	}
}

