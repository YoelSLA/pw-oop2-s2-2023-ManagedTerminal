package phase;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InboundTest {

	private Inbound currentPhase; //SUT
	private Arrived nextPhase; //DOC
	
	
	@BeforeEach
	void setUp() {
		currentPhase = new Inbound();
		nextPhase = new Arrived();
	}
	
	@Test
	void getNextPhase() {
		assertEquals(nextPhase.getClass(), currentPhase.nextPhase().getClass());  
	}
}
