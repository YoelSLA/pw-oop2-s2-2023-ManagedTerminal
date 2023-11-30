package service;

import order.Order;

public class Washed extends Service {
	
	//en metros cubicos
	private final int maxVolumePerMinimumFee = 70;
	

	public Washed(Double price, Double optionalPrice) {
		super(price, optionalPrice);
		this.name = "Washed";
	}
	
	@Override
	public Double getPriceFor(Order order) {
		if (order.getLoadVolume() <= maxVolumePerMinimumFee) {
			return price;
		} else return optionalPrice;
	}

	public Double getBigVolumePrice() {
		return optionalPrice;
	}

}
