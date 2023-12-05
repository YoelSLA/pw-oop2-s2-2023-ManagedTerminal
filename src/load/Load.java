package load;

public abstract class Load {

	private final Double length;
	private final Double weight;
	private final Double width;
	private final Double height;
	private final String name;

	/**
	 * @param length La longitud de la carga en metros.
	 * @param height La altura de la carga en metros.
	 * @param width  El ancho de la carga en metros.
	 * @param weight El peso de la carga en toneladas.
	 */
	public Load(Double height, Double length, Double width, Double weight, String name) {
		this.height = height;
		this.length = length;
		this.width = width;
		this.weight = weight;
		this.name = name;
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

	public String getName() {
		return name;
	}

	public Double getVolume() {
		return width * height * length;
	}

	public Boolean consumesElectricity() {
		return false;
	}
}
