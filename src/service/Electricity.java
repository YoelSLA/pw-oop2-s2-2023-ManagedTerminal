package service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import load.Load;
import load.Reefer;

public class Electricity extends Service {

	private LocalDateTime startConnection;
	private LocalDateTime endConnection;

	public Electricity(Double price, LocalDateTime startConnection) {
		super(price, "Electricity");
		this.startConnection = startConnection;
	}

	@Override
	public Double getPriceFor(Load load) {
		// Se hace un downcasting para obtener el reefer.
		Reefer reefer = (Reefer) load;
		// Se calcula las horas las cuales estuvo el reefer conectado.
		Integer hoursConnected = (int) ChronoUnit.HOURS.between(startConnection, endConnection);

		return reefer.getConsumptionkWh() * getPrice() * hoursConnected;
	}

	public LocalDateTime getStartConnection() {
		return startConnection;
	}

	public LocalDateTime getEndConnection() {
		return endConnection;
	}

	public void setEndConnection(LocalDateTime endConnection) {
		this.endConnection = endConnection;
	}

	public Boolean isElectricyService() {
		return true;
	}
}
