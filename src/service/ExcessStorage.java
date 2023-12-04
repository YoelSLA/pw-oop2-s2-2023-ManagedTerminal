package service;

import load.Load;

public class ExcessStorage extends Service {

	Integer hoursOfStay;

	public ExcessStorage(Double price, Integer hoursOfStay) {
		super(price, "ExcessStorage");
		this.hoursOfStay = hoursOfStay;
	}

	@Override
	public Double getPriceFor(Load load) {
		return getPrice() * hoursOfStay;
	}

	public Integer getHoursOfStay() {
		return hoursOfStay;
	}
}
