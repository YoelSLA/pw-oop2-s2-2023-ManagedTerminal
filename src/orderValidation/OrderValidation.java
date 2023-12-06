package orderValidation;

import driver.Driver;
import order.Order;
import terminal.ManagedTerminal;
import truck.Truck;

public abstract class OrderValidation {

	public static void runFullOrderValidations(ManagedTerminal managedTerminal, Order order, Driver driver,
			Truck truck) {
		validateOrderInTerminal(managedTerminal, order);
		validateDriverAndTruckWithClientInfo(order, driver, truck);

	}

	public static void validateOrderInTerminal(ManagedTerminal managedTerminal, Order order) {
		validateDriverInTerminal(managedTerminal, order.getDriver());
		validateTruckInTerminal(managedTerminal, order.getTruck());

	}

	private static void validateDriverInTerminal(ManagedTerminal managedTerminal, Driver driver) {
		if (!managedTerminal.isDriverRegistered(driver)) {
			throw new RuntimeException("Driver not registered in the Managed Teminal.");
		}
	}

	private static void validateTruckInTerminal(ManagedTerminal managedTerminal, Truck truck) {
		if (!managedTerminal.isTruckRegistered(truck)) {
			throw new RuntimeException("Truck not registered in the Managed Teminal.");
		}
	}

	private static void validateDriverAndTruckWithClientInfo(Order order, Driver driver, Truck truck) {
		validateDriverInOrder(order, driver);
		validateTruckInOrder(order, truck);
	}

	private static void validateDriverInOrder(Order order, Driver driver) {
		if (!order.getDriver().equals(driver)) {
			throw new RuntimeException("Driver does not match the order");
		}
	}

	private static void validateTruckInOrder(Order order, Truck truck) {
		if (!order.getTruck().equals(truck)) {
			throw new RuntimeException("Truck does not match the order");
		}
	}

}
