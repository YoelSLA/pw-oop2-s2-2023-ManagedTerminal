package load;

public class Reefer extends Load {
	
	private Double energyConsumption;
	
	public Reefer(Double length, Double height, Double width, Double weight, Double energyConsumption) {
		super(length, height, width, weight, "Reefer");
		this.energyConsumption = energyConsumption;
	}
	
	public Double getConsumptionkWh() {
		return energyConsumption;
	}
}
