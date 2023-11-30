package service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import order.Order;

public class Electricity extends Service{
		
	private LocalDateTime startConnection;
	private LocalDateTime endConnection;	
	
	public Electricity(Double price, LocalDateTime startConnection, LocalDateTime endConnection) {
		super(price);
		this.startConnection = startConnection;
		this.endConnection = endConnection;
		this.name = "Electricity";
	}
	
	@Override
	public Double getPriceFor(Order order) {
		Double hoursConnected = (double) ChronoUnit.HOURS.between(startConnection, endConnection);
		
		return order.getLoad().getEnergyConsumption() * price * hoursConnected;		
	}
	
	public LocalDateTime getStartConnection() {
		return startConnection;
	}

	public LocalDateTime getEndConnection() {
		return endConnection;
	}
}
