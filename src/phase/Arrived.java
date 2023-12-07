package phase;

public class Arrived extends Phase {

	@Override
	public Phase nextPhase() {
		return new Working();
	}

}
