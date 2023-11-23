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
	/* no funciona ya que me devuelve dos instancias distintas de la clase Working.
	 * Una solucion seria pasarle en el constructor a cada fase la proxima clase, de forma que podamos setearle la instancia desde el test,
	 * pero asi rompemos con el ppio de encapsulamiento y open-closed porque estamos dejando que cualquiera pueda setearle una clase*/
	@Test
	void getNextPhase() {
		assertEquals(nextPhase, currentPhase.nextPhase());  
	}

}
