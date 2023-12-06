package phase;

public class Working extends Phase {

	@Override
	public Phase nextPhase() {
		return new Departing();
	}
	
	
}

