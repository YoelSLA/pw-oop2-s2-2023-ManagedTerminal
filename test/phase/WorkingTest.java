package phase;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WorkingTest {

	private Working currentPhase; //SUT
	private Departing nextPhase; //DOC
	
	
	@BeforeEach
	void setUp() {
		currentPhase = new Working();
		nextPhase = new Departing();
	}
	
	@Test
	void getNextPhase() {
		assertEquals(nextPhase.getClass(), currentPhase.nextPhase().getClass());  
	}
}
