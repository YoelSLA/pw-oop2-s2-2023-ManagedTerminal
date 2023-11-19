package load;


/**
 * Clase que representa una carga / container.
 * 
 * Esta clase gestiona la información de la carga, detallando
 * sus dimensiones y calculo de su volumen.
 * 
 * @author Gabriela Fascetta
 */
public class Load {

	private Double width;
	private Double height;
	private Double length;
	private Double weight;
	
	/**
	 * Constructor de la clase Load.
	 * 
	 * @param width  El ancho de la carga en metros.
	 * @param height  La altura de la carga en metros.
	 * @param length La longitud de la carga en metros.
	 * @param weight El peso de la carga en toneladas.
	 * 
	 * @author Gabriela Fascetta
	 */
	public Load(Double width, Double height, Double length, Double weight) {
		this.width = width;
		this.height = height;
		this.length = length;
		this.weight = weight;
	}
	
	
	public Double getWidth() {
		return width;
	}
	public Double getHeight() {
		return height;
	}
	public Double getLength() {
		return length;
	}
	public Double getWeight() {
		return weight;
	}
	
	/**
	 * Metodo que calcula el volumen de la carga.
	 * Retorna una cantidad en metros cubicos.
	 * 
	 * @author Gabriela Fascetta
	 */
	public Double getVolume() {
		return width * height * length;
	}
	
}
