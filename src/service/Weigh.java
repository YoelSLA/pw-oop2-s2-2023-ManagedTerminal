package service;

import load.Load;

public class Weigh extends Service {

	public Weigh(Double price) {
		super(price, "Weigh");
	}

	@Override
	public Double getPriceFor(Load load) {
		return getPrice();
	}
}
