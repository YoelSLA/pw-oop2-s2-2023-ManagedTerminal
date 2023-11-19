package load;


/**
 *Clase que representa una carga que debe mantenerse refrigerada.
 *
 * @author Gabriela Fascetta
 **/
public class Reefer extends Load {
	
	private Double energyConsumption;
	
	/**
	 * Constructor de la clase Reefer.
	 * 
	 * @param width  El ancho de la carga en metros.
	 * @param height  La altura de la carga en metros.
	 * @param length La longitud de la carga en metros.
	 * @param weight El peso de la carga en toneladas.
	 * @param energyConsumption El consumo energetico en kw/hora.
	 * 
	 * @author Gabriela Fascetta
	 */
	public Reefer(Double width, Double height, Double length, Double weight, Double energyConsumption) {
		super(width, height, length, weight);
		this.energyConsumption = energyConsumption;
	}

	public Double getEnergyConsumption() {
		return energyConsumption;
	}
}