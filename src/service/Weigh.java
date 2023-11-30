package service;

import load.Load;
import order.Order;

public class Weigh extends Service {
	
	public Weigh(Double price) {
		super(price);
		this.name = "Weigh";
	}
		
	public Double weighOn(Load load) {
		return load.getWeight();
	}


	@Override
	public Double getPriceFor(Order order) {
		return price;
	}
}
