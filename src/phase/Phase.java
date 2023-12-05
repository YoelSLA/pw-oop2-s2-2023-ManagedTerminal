package phase;

import ship.Ship;
import terminal.ManagedTerminal;

/** 
 * @author alejandrabesel
 * Clase abstracta que representa una fase del buque. 
 * Consta de un metodo abstracto que devolvera la proxima fase del buque representando los estados que éste puede tener desde
 * que da el preaviso de que esta cerca de la terminal, hasta que vuelve a salir luego de descargar.
 * Elegimos implementar las clases hijas como 'final' ya que en este dominio no nos interesa que los estados tengan herencia
 * */

public interface Phase {

	Phase updatePhase(Ship ship, ManagedTerminal managedTerminal);
	Phase updatePhaseFor(Ship ship);
	Phase nextPhase();


}
