package turn;

import java.time.LocalDateTime;

import driver.Driver;
import truck.Truck;

public class Turn {

	private Driver driver;
	private Truck truck;
	private LocalDateTime date;

	public Turn(Driver driver, Truck truck) {
		this.driver = driver;
		this.truck = truck;
	}

	public Driver getDriver() {
		return driver;
	}

	public Truck getTruck() {
		return truck;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;

	}

}
