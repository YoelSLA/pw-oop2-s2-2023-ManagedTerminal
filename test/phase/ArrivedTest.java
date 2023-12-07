package phase;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


class ArrivedTest {
	
	private Arrived currentPhase; //SUT
	private Working nextPhase; //DOC
	
	
	@BeforeEach
	void setUp() {
		currentPhase = new Arrived();
		nextPhase = new Working();
	}
	
	@Test
	void getNextPhase() {
		assertEquals(nextPhase.getClass(), currentPhase.nextPhase().getClass());  
	}

}
