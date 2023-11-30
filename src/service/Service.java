package service;

import order.Order;


public abstract class Service {

	protected Double price;
	protected Double optionalPrice;
	protected String name;
	
	public Service(Double price, Double optionalPrice) {
		this.price = price;
		this.optionalPrice = optionalPrice;
	}
	
	public Service(Double price) {
		this.price = price;
	}

	public Double getPrice() {return price;}
	
	public abstract Double getPriceFor(Order order);
	
	public final String getName() {return this.name;}

}
