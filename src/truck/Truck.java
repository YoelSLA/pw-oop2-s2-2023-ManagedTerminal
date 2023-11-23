package truck;

public class Truck {
	private String brand;
	private String model;
	private String patent;
	
	public Truck(String brand, String model, String patent) {
		this.brand = brand;
		this.model = model;
		this.patent = patent;
	}

	public String getBrand() {
		return brand;
	}

	public String getModel() {
		return model;
	}

	public String getPatent() {
		return patent;
	}
}
