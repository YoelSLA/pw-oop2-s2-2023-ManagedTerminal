package phase;

public final class Inbound implements Phase {
	
	@Override
	public Arrived nextPhase() {
		return new Arrived();
	}
}

