package load;

/**
 *Clase que representa una carga que equivale a una caja metalica cerrada. 
 *
 * @author Gabriela Fascetta
 **/
public class Dry extends Load {
	
	/**
	 * Constructor de la clase Dry.
	 * 
	 * @param width  El ancho de la carga en metros.
	 * @param height  La altura de la carga en metros.
	 * @param length La longitud de la carga en metros.
	 * @param weight El peso de la carga en toneladas.
	 * 
	 * @author Gabriela Fascetta
	 */
	public Dry(Double width, Double height, Double length, Double weight) {
		super(width, height, length, weight);
	}

}
