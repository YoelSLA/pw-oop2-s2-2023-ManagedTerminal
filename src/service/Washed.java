package service;

import load.Load;

public class Washed extends Service {
	
	private Double otherPrice;
	

	public Washed(Double price, Double otherPrice) {
		super(price, "Washed");
		this.otherPrice = otherPrice;
	}
	
	@Override
	public Double getPriceFor(Load load) {
		return load.getVolume() > 70 ? this.getPrice() : otherPrice;
	}

	public Double getOtherPrice() {
		return otherPrice;
	}
}
