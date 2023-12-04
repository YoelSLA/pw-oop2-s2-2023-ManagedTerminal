package load;

public class Dry extends Load {
	
	/**
	 * Constructor de la clase Dry.
	 * 
	 * @param width  El ancho de la carga en metros.
	 * @param height  La altura de la carga en metros.
	 * @param length La longitud de la carga en metros.
	 * @param weight El peso de la carga en toneladas.
	 */
	public Dry(Double length, Double height, Double width, Double weight) {
		super(length, height, width, weight, "Dry");
	}

}
