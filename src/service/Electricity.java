package service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import load.Load;
import load.Reefer;
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
	public Double getPriceFor(Load load) {
		
		Reefer reefer = (Reefer) load;
		
		Integer hoursConnected = (int) ChronoUnit.HOURS.between(startConnection, endConnection);
		
		return reefer.getConsumptionkWh() * price * hoursConnected;		
	}
	
	public LocalDateTime getStartConnection() {
		return startConnection;
	}

	public LocalDateTime getEndConnection() {
		return endConnection;
	}
}
