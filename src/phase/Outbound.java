package phase;

public final class Outbound implements Phase {
	
	@Override
	public Inbound nextPhase() {
		return new Inbound();
	}
}

