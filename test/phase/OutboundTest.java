package phase;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OutboundTest {

	private Outbound currentPhase; //SUT
	private Inbound nextPhase; //DOC
	
	
	@BeforeEach
	void setUp() {
		currentPhase = new Outbound();
		nextPhase = new Inbound();
	}
	
	@Test
	void getNextPhase() {
		assertEquals(nextPhase.getClass(), currentPhase.nextPhase().getClass());  
	}

}
