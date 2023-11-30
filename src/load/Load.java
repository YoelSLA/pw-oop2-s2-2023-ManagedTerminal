package load;

public abstract class Load {

	private final Double width;
	private final Double height;
	private final Double length;
	private final Double weight;
	
	
	/**
	 * @param width  El ancho de la carga en metros.
	 * @param height  La altura de la carga en metros.
	 * @param length La longitud de la carga en metros.
	 * @param weight El peso de la carga en toneladas.
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
	
	public final Double getVolume() {
		return width * height * length;
	}
	
	public int getEnergyConsumption() {return 0;}
}
