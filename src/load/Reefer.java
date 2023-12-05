package load;

public class Reefer extends Load {

	private Double energyConsumption;

	public Reefer(Double height, Double length, Double width, Double weight, Double energyConsumption) {
		super(height, length, width, weight, "Reefer");
		this.energyConsumption = energyConsumption;
	}

	public Double getConsumptionkWh() {
		return energyConsumption;
	}

	public void setConsumption(Double consumptionkWh) {
		this.energyConsumption = consumptionkWh;
	}

	@Override
	public Boolean consumesElectricity() {
		return false;
	}
}
