package load;

public class Reefer extends Load {
	
	private int energyConsumption;
	
	public Reefer(Double width, Double height, Double length, Double weight, int energyConsumption) {
		super(width, height, length, weight);
		this.energyConsumption = energyConsumption;
	}
	
	@Override
	public int getEnergyConsumption() {return energyConsumption;}
}
