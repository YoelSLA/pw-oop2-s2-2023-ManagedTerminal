package service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import order.Order;

public class ExcessStorage extends Service{
	
	public ExcessStorage(Double price) {
		super(price);
		this.name = "ExcessStorage";
	}
	
	@Override
	public Double getPriceFor(Order order) {
		int extraDays = (int) Math.abs((int) ChronoUnit.DAYS.between(LocalDate.now(), order.getDateTruck()));
		return price * extraDays;
	}
}
