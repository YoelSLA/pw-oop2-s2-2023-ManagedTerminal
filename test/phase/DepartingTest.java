package phase;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


class DepartingTest {
	
	private Departing currentPhase; //SUT
	private Outbound nextPhase; //DOC
	
	
	@BeforeEach
	void setUp() {
		currentPhase = new Departing();
		nextPhase = new Outbound();
	}
	
	@Test
	void getNextPhase() {
		assertEquals(nextPhase.getClass(), currentPhase.nextPhase().getClass());  
	}

}
