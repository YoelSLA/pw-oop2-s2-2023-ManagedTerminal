package load;


/**
 *Clase que representa una carga que debe mantenerse refrigerada.
 *
 * @author Gabriela Fascetta
 **/
public class Reefer extends Load {
	
	/**
	 * Constructor de la clase Reefer.
	 * 
	 * @param width  El ancho de la carga en metros.
	 * @param height  La altura de la carga en metros.
	 * @param length La longitud de la carga en metros.
	 * @param weight El peso de la carga en toneladas.
	 * @param energyConsumption El consumo energetico en kw/hora.
	 */
	public Reefer(Double width, Double height, Double length, Double weight, int energyConsumption) {
		super(width, height, length, weight);
		this.energyConsumption = energyConsumption;
	}
}
