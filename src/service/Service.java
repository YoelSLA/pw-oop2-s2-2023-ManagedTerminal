package service;

import load.Load;


public abstract class Service {

	protected Double price;
	protected String name;
	
	public Service(Double price) {
		this.price = price;
	}

	public Double getPrice() {return price;}
	
	public abstract Double getPriceFor(Load load);
	
	public final String getName() {return this.name;}

}
