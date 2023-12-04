package service;

import load.Load;
import order.Order;

public class Weigh extends Service {
	
	public Weigh(Double price) {
		super(price, "Weigh");
	}

	@Override
	public Double getPriceFor(Load load) {
		return price;
	}
}
