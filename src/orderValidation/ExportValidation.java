package orderValidation;

import java.time.LocalDateTime;

import driver.Driver;
import order.ExportOrder;
import terminal.ManagedTerminal;
import truck.Truck;

/**
 * Validador de órdenes de exportación. Implementa el algoritmo del método
 * validation de su superclase.
 * 
 * @author Gabriela Fascetta | Yoel Ventoso
 */
public final class ExportValidation extends OrderValidation {

	public static void validateOrderFor(ExportOrder exportOrder, ManagedTerminal terminal) {
		validateDriverOf(exportOrder, terminal);
		validateTruckOf(exportOrder, terminal);

	}

	private static void validateDriverOf(ExportOrder exportOrder, ManagedTerminal terminal) {
		if (!terminal.isItRegistered(exportOrder.getDriver())) {
			throw new RuntimeException("El chofer no esta registrado en la terminal gestionada.");
		}

	}

	private static void validateTruckOf(ExportOrder exportOrder, ManagedTerminal terminal) {
		if (!terminal.isItRegistered(exportOrder.getTruck())) {
			throw new RuntimeException("El camión no esta registrado en la terminal gestionada.");
		}
	}

	public static void validateTruckEntry(Driver driver, ExportOrder exportOrder, Truck truck,
			LocalDateTime arrivalDate) {
		validateDriverReportedByShipper(exportOrder, driver);
		validateTruckArrivalTime(exportOrder, arrivalDate);
		validateTruckReportedByShipper(exportOrder, truck);

	}

	private static void validateTruckArrivalTime(ExportOrder exportOrder, LocalDateTime arrivalDate) {
		if (!(Math.abs(arrivalDate.getHour() - exportOrder.getDateTruck().getHour()) > 3)) {
			throw new RuntimeException("El horario difiere de más de 3 horas.");
		}

	}

	private static void validateDriverReportedByShipper(ExportOrder exportOrder, Driver driver) {
		if (!driver.equals(exportOrder.getDriver())) {
			throw new RuntimeException("El chofer no es el informado por el shipper.");
		}
	}

	private static void validateTruckReportedByShipper(ExportOrder exportOrder, Truck truck) {
		if (!truck.equals(exportOrder.getTruck())) {
			throw new RuntimeException("El camión no es el informado por el shipper.");
		}
	}

}
