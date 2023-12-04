package service;

import load.Load;

public abstract class Service {

	private Double price;
	private String name;

	public Service(Double price, String name) {
		this.price = price;
		this.name = name;
	}

	public abstract Double getPriceFor(Load load);

	public Double getPrice() {
		return price;
	}

	public final String getName() {
		return this.name;
	}

}
