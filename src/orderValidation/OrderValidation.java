package orderValidation;

import java.util.List;
import java.util.stream.Collectors;
import driver.Driver;
import truck.Truck;
import truckTransportCompany.TruckTransportCompany;

/**
 * Validador de órdenes.
 * 
 * Esta clase abstracta provee un molde para creación de subclases (validadores
 * de orden de importacion y exportacion). Requiere implementar el algoritmo del
 * método validation.
 * 
 * @author Gabriela Fascetta
 */
public abstract class OrderValidation {

	public static void validate(Truck truck, Driver driver, List<TruckTransportCompany> truckTransportCompanies) {

		validateDriverInTerminal(driver, truckTransportCompanies);
		validateTruckInTerminal(truck, truckTransportCompanies);
	}
	
	private static void validateDriverInTerminal(Driver driver, List<TruckTransportCompany> truckTransportCompanies) {
		if (!isRegisteredIn(driver, truckTransportCompanies)) 
			throw new RuntimeException("El chofer con la DNI " + driver.getDni() + "no está registrado en la terminal");
	}

	private static void validateTruckInTerminal(Truck truck, List<TruckTransportCompany> truckTransportCompanies) {
		if (!isRegisteredIn(truck, truckTransportCompanies))
			throw new RuntimeException(
					"El camión con la patente " + truck.getPatent() + "no está registrado en la terminal");
	}

	private static boolean isRegisteredIn(Driver driver, List<TruckTransportCompany> truckTransportCompanies) {
		return truckTransportCompanies.stream().map(TruckTransportCompany::dnisOfDrivers).flatMap(List::stream)
				.collect(Collectors.toList()).contains(driver.getDni());
	}

	private static boolean isRegisteredIn(Truck truck, List<TruckTransportCompany> truckTransportCompanies) {
		return truckTransportCompanies.stream().map(TruckTransportCompany::patentsOfTrucks).flatMap(List::stream)
				.collect(Collectors.toList()).contains(truck.getPatent());
	}
}
