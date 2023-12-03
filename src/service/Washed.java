package service;

import load.Load;

public class Washed extends Service {
	
	//en metros cubicos
	private final int maxVolumePerMinimumFee = 70;
	private Double costPerBigLoad;
	

	public Washed(Double price, Double costPerBigLoad) {
		super(price);
		this.name = "Washed";
		this.costPerBigLoad = costPerBigLoad;
	}
	
	@Override
	public Double getPriceFor(Load load) {
		Boolean hasRegularSize = load.getVolume() <= maxVolumePerMinimumFee;
		return hasRegularSize ? this.getPrice() : getCostPerBigLoad();
	}

	public Double getCostPerBigLoad() {
		return costPerBigLoad;
	}

}
