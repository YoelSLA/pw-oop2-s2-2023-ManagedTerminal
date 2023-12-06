package phase;

import ship.Ship;

/** 
 * @author alejandrabesel
 * Clase abstracta que representa una fase del buque. 
 * Consta de un metodo abstracto que devolvera la proxima fase del buque representando los estados que éste puede tener desde
 * que da el preaviso de que esta cerca de la terminal, hasta que vuelve a salir luego de descargar.
 * Elegimos implementar las clases hijas como 'final' ya que en este dominio no nos interesa que los estados tengan herencia
 * */

public abstract class Phase {
	
	private Boolean status;
	
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	public abstract Phase nextPhase();
	
	/** 
	 * Valida que el buque este en condiciones de pasar a la siguiente fase
	 * Si lo esta, le da la responsabilidad al buque de que se comunique con la terminal y notifique de su estado actual
	 * Ademas, actualiza la fase del buque
	 * @param ship
	 * Observaciones: primero comunicamos con la terminal y luego actualizamos la fase para que cada fase
	 * notifique a la terminal lo que le corresponde. 
	 * Ademas, consideramos que este metodo puede pensarse como un Template method ya que se encarga de crear 
	 * la estructura del algoritmo, y luego delega en cada fase la implementacion de los demas metodos.
	 */
	public void proceedToNextPhase(Ship ship) { 
		if(this.canItGoToTheNextPhase(ship)) {
			this.communicateWithTerminal(ship);
			this.changePhase(ship);
		}
	}
	
	protected void communicateWithTerminal(Ship ship) {};

	protected boolean canItGoToTheNextPhase(Ship ship) {
		return status;
	};
	
	public void changePhase(Ship ship) {
		ship.setPhase(ship.getPhase().nextPhase());
	}
}
