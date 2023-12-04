package orderValidation;

import java.time.Duration;
import java.time.LocalDateTime;

import driver.Driver;
import order.ExportOrder;
import terminal.ManagedTerminal;
import truck.Truck;

public final class ExportValidation extends OrderValidation {

	public static void runFullOrderValidations(ManagedTerminal managedTerminal, ExportOrder exportOrder, Driver driver,
			Truck truck, LocalDateTime dateToArrival) {
		runFullOrderValidations(managedTerminal, exportOrder, driver, truck);
		validateShiftTiming(exportOrder, dateToArrival);
	}

	private static void validateShiftTiming(ExportOrder exportOrder, LocalDateTime arrivalDate) {
		Long hours = Duration.between(arrivalDate, exportOrder.getTurn().getDate()).toHours();

		if (hours.intValue() > 3) {
			throw new RuntimeException("Shift differs by more than 3 hours.");
		}
	}
}