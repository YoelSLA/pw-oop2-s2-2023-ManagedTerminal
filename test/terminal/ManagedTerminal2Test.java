package terminal;

import org.junit.jupiter.api.BeforeEach;

import routing.ShorterTime;

class ManagedTerminal2Test {

	protected ShorterTime shorterTime;
	// ------------------------------------------------------------
	protected ManagedTerminal buenosAires;

	@BeforeEach
	void setUp() throws Exception {
		// SHORTER TIME
		shorterTime = new ShorterTime();
		// ------------------------------------------------------------
		// MANAGED TERMINAL
		buenosAires = new ManagedTerminal(shorterTime);
	}

}
