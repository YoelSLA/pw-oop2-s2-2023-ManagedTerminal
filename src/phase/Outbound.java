package phase;

public final class Outbound extends Phase {
	
	@Override
	public Inbound nextPhase() {
		return new Inbound();
	}
}

