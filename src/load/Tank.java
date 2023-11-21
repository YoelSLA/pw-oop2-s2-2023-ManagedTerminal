package load;


/**
 *Clase que representa una carga que transporta liquidos y gases.
 *
 * @author Gabriela Fascetta
 **/
public class Tank extends Load{

	/**
	 * Constructor de la clase Tank.
	 * 
	 * @param width  El ancho de la carga en metros.
	 * @param height  La altura de la carga en metros.
	 * @param length La longitud de la carga en metros.
	 * @param weight El peso de la carga en toneladas.
	 * 
	 */
	public Tank(Double width, Double height, Double length, Double weight) {
		super(width, height, length, weight);
	}
}
